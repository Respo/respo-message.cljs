
(ns respo-message.main
  (:require [respo.core :refer [render! clear-cache! realize-ssr!]]
            [respo.cursor :refer [mutate]]
            [respo.util.format :refer [mute-element]]
            [respo-message.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [respo-message.schema :as schema]
            ["lorem-ipsum" :as lorem-ipsum]))

(defonce *id (atom 0))

(defonce *states (atom {}))

(defonce *store (atom schema/store))

(defn id! [] (swap! *id inc) @*id)

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [op-id (id!)
        op-time (.now js/Date)
        store @*store
        new-store (case op
                    :states (update store :states (mutate op-data))
                    :message/add
                      (assoc-in
                       store
                       [:messages op-id]
                       (assoc op-data :id op-id :time op-time))
                    :message/remove
                      (update store :messages (fn [messages] (dissoc messages (:id op-data))))
                    :message/clear (assoc store :messages {})
                    store)]
    (reset! *store new-store)))

(def mount-target (.querySelector js/document ".app"))

(defn render-app! [renderer] (renderer mount-target (comp-container @*store) dispatch!))

(def ssr? (some? (.querySelector js/document "meta.respo-app")))

(defn main! []
  (if ssr? (render-app! realize-ssr!))
  (render-app! render!)
  (add-watch *store :changes (fn [] (render-app! render!)))
  (js/setTimeout (fn [] (dispatch! :message/add {:text (lorem-ipsum)})))
  (println "app started!"))

(defn reload! []
  (clear-cache!)
  (render-app! render!)
  (println "Code update.")
  (dispatch! :message/add {:text (lorem-ipsum)}))

(set! (.-onload js/window) main!)

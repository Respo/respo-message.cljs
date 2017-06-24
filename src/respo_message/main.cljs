
(ns respo-message.main
  (:require [respo.core :refer [render! clear-cache! realize-ssr!]]
            [respo.cursor :refer [mutate]]
            [respo.util.format :refer [mute-element]]
            [respo-message.comp.container :refer [comp-container]]
            [respo-message.updater :refer [add-one remove-one]]
            [cljs.reader :refer [read-string]]
            [respo-message.schema :as schema]))

(def ssr? (some? (.querySelector js/document "meta.respo-app")))

(defonce *id (atom 0))

(defn id! [] (swap! *id inc) @*id)

(defonce *store (atom schema/store))

(def words
  ["just demo"
   "Oh, this is strange"
   "why do I have to do that? it's huge!"
   "OK"
   "wrong"
   "find"])

(def kinds [:attractive :irreversible :attentive :warn :verdant])

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [op-id (id!)
        new-store (case op
                    :states (update @*store :states (mutate op-data))
                    :message/add
                      (add-one
                       @*store
                       op
                       {:id op-id, :text (rand-nth words), :kind (rand-nth kinds)})
                    :notification/remove (remove-one @*store op op-data)
                    @*store)]
    (reset! *store new-store)))

(def mount-target (.querySelector js/document ".app"))

(defn render-app! [renderer] (renderer mount-target (comp-container @*store) dispatch!))

(defn main! []
  (enable-console-print!)
  (if ssr? (render-app! realize-ssr!))
  (render-app! render!)
  (add-watch *store :changes (fn [] (render-app! render!)))
  (js/setTimeout (fn [] (dispatch! :message/add nil)))
  (println "app started!"))

(defonce *states (atom {}))

(defn reload! []
  (clear-cache!)
  (render-app! render!)
  (println "Code update.")
  (dispatch! :message/add nil))

(set! (.-onload js/window) main!)

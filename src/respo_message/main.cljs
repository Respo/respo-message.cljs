
(ns respo-message.main
  (:require [respo.core :refer [render! clear-cache! realize-ssr!]]
            [respo.cursor :refer [mutate]]
            [respo.util.format :refer [mute-element]]
            [respo-message.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]
            [respo-message.schema :as schema]
            ["lorem-ipsum" :as lorem-ipsum]
            ["shortid" :as shortid]
            [respo-message.updater :refer [update-messages]]
            [respo-message.action :as action]
            [respo-message.config :as config]))

(defonce *id (atom 0))

(defonce *states (atom {}))

(defonce *store (atom schema/store))

(defn dispatch! [op op-data]
  (when config/dev? (println "Dispatch:" op))
  (let [op-id (.generate shortid), op-time (.now js/Date), store @*store]
    (reset!
     *store
     (cond
       (= op :states) (update store :states (mutate op-data))
       (action/message-action? op)
         (update store :messages #(update-messages % op op-data op-id op-time))
       :else (do (println "Unhandled operation:" op) store)))))

(defn id! [] (swap! *id inc) @*id)

(def mount-target (.querySelector js/document ".app"))

(defn render-app! [renderer]
  (renderer mount-target (comp-container @*store) #(dispatch! %1 %2)))

(def ssr? (some? (.querySelector js/document "meta.respo-app")))

(defn main! []
  (println "Running mode:" (if config/dev? "dev" "release"))
  (if ssr? (render-app! realize-ssr!))
  (render-app! render!)
  (add-watch *store :changes (fn [] (render-app! render!)))
  (js/setTimeout (fn [] (dispatch! action/create {:text (lorem-ipsum/loremIpsum)})))
  (println "app started!"))

(defn reload! []
  (clear-cache!)
  (render-app! render!)
  (println "Code update.")
  (dispatch! action/create {:text (lorem-ipsum/loremIpsum)}))

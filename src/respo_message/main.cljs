
(ns respo-message.main
  (:require [respo.core :refer [render! clear-cache!]]
            [respo-message.comp.container :refer [comp-container]]
            [cljs.reader :refer [read-string]]))

(def id-ref (atom 0))

(defn id! [] (swap! id-ref inc) @id-ref)

(defonce store-ref (atom {:messages []}))

(defn dispatch! [op op-data]
  (let [id (id!)
        new-store (case op
                    :message/add
                      (update
                       @store-ref
                       :messages
                       (fn [messages]
                         (conj messages {:id id, :kind :attractive, :text op-data})))
                    @store-ref)]
    (reset! store-ref new-store)))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(defn -main! []
  (enable-console-print!)
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (println "app started!"))

(defn on-jsload! [] (clear-cache!) (render-app!) (println "code update."))

(set! (.-onload js/window) -main!)

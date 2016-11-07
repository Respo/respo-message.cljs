
(ns respo-message.main
  (:require [respo.core :refer [render! clear-cache! falsify-stage! render-element]]
            [respo.util.format :refer [mute-element]]
            [respo-message.comp.container :refer [comp-container]]
            [respo-message.updater :refer [add-one remove-one]]
            [cljs.reader :refer [read-string]]))

(defonce id-ref (atom 0))

(defn id! [] (swap! id-ref inc) @id-ref)

(def words
  ["just demo"
   "Oh, this is strange"
   "why do I have to do that? it's huge!"
   "OK"
   "wrong"
   "find"])

(def kinds [:attractive :irreversible :attentive :warn :verdant])

(defonce store-ref (atom {:messages []}))

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [op-id (id!)
        new-store (case op
                    :message/add
                      (add-one
                       @store-ref
                       op
                       {:id op-id, :kind (rand-nth kinds), :text (rand-nth words)})
                    :message/remove (remove-one @store-ref op op-data)
                    @store-ref)]
    (reset! store-ref new-store)))

(defonce states-ref (atom {}))

(defn render-app! []
  (let [target (.querySelector js/document "#app")]
    (render! (comp-container @store-ref) target dispatch! states-ref)))

(def ssr-stages
  (let [ssr-element (.querySelector js/document "#ssr-stages")
        ssr-markup (.getAttribute ssr-element "content")]
    (read-string ssr-markup)))

(defn -main! []
  (enable-console-print!)
  (if (not (empty? ssr-stages))
    (let [target (.querySelector js/document "#app")]
      (falsify-stage!
       target
       (mute-element (render-element (comp-container @store-ref ssr-stages) states-ref))
       dispatch!)))
  (render-app!)
  (add-watch store-ref :changes render-app!)
  (add-watch states-ref :changes render-app!)
  (js/setTimeout (fn [] (dispatch! :message/add nil)))
  (println "app started!"))

(defn on-jsload! []
  (clear-cache!)
  (render-app!)
  (println "code update.")
  (dispatch! :message/add nil))

(set! (.-onload js/window) -main!)

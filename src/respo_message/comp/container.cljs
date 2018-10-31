
(ns respo-message.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo.core :refer [defcomp div span button <>]]
            [respo-ui.core :as ui]
            [respo.core :refer [create-comp]]
            [respo.comp.space :refer [=<]]
            [respo-message.comp.messages :refer [comp-messages]]
            [respo-message.schema :as schema]
            ["lorem-ipsum" :as lorem-ipsum]
            [respo-message.action :as action]
            [respo.comp.inspect :refer [comp-inspect]]
            [respo-message.config :as config]
            ["shortid" :as shortid]))

(defcomp
 comp-container
 (store)
 (div
  {:style (merge ui/global {:padding 16})}
  (div
   {:style ui/row}
   (button
    {:style ui/button,
     :on-click (fn [e d! m!]
       (let [new-token (.generate shortid)]
         (d! action/create (merge schema/message {:token new-token, :text (lorem-ipsum)}))
         (js/setTimeout (fn [] (d! action/remove-one {:token new-token})) 2000)))}
    (<> "Try"))
   (=< 16 nil)
   (button {:style ui/button, :on-click (fn [e d! m!] (d! action/clear nil))} (<> "Clear")))
  (comp-messages
   (:messages store)
   {:bottom? true}
   (fn [info d! m!] (d! action/remove-one info)))
  (when config/dev? (comp-inspect "messages" (:messages store) nil))))

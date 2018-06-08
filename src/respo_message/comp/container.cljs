
(ns respo-message.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo.macros :refer [defcomp div span button <>]]
            [respo-ui.core :as ui]
            [respo.core :refer [create-comp]]
            [respo.comp.space :refer [=<]]
            [respo-message.comp.messages :refer [comp-messages]]
            [respo-message.schema :as schema]
            ["lorem-ipsum" :as lorem-ipsum]))

(defcomp
 comp-container
 (store)
 (div
  {:style (merge ui/global {:padding 16})}
  (div
   {:style ui/row}
   (button
    {:style ui/button,
     :on-click (fn [e d! m!] (d! :message/add (merge schema/message {:text (lorem-ipsum)})))}
    (<> "Try"))
   (=< 16 nil)
   (button
    {:style ui/button, :on-click (fn [e d! m!] (d! :message/clear nil))}
    (<> "Clear")))
  (comp-messages (:messages store) :message/remove)))

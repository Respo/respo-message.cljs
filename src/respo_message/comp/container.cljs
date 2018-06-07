
(ns respo-message.comp.container
  (:require-macros [respo.macros :refer [defcomp div span button <>]])
  (:require [hsl.core :refer [hsl]]
            [respo-ui.core :as ui]
            [respo.core :refer [create-comp]]
            [respo.comp.space :refer [=<]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]))

(defn on-add [e dispatch!] (dispatch! :message/add nil))

(defcomp
 comp-container
 (store)
 (div
  {:style (merge ui/global {:padding 16})}
  (div
   {:style ui/row}
   (button {:style ui/button, :on-click on-add} (<> "Try"))
   (=< 16 nil)
   (button
    {:style ui/button, :on-click (fn [e d! m!] (d! :message/clear nil))}
    (<> "Clear")))
  (comp-msg-list (:messages store) :message/remove)))

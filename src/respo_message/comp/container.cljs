
(ns respo-message.comp.container
  (:require-macros [respo.macros :refer [defcomp div span <>]])
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
  (div {:style ui/button, :on-click on-add} (<> span "add message" nil))
  (comp-msg-list (:messages store) :notification/remove)))

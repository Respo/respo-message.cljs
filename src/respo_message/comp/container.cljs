
(ns respo-message.comp.container
  (:require [hsl.core :refer [hsl]]
            [respo-ui.style :as ui]
            [respo.alias :refer [create-comp div span]]
            [respo.comp.space :refer [comp-space]]
            [respo.comp.text :refer [comp-text]]
            [respo-message.comp.msg-list :refer [comp-msg-list]]))

(defn on-add [e dispatch!] (dispatch! :message/add nil))

(defn render [store]
  (fn [state mutate!]
    (div
     {:style (merge ui/global {:padding 16})}
     (div {:style ui/button, :event {:click on-add}} (comp-text "add message" nil))
     (comp-msg-list (:messages store)))))

(def comp-container (create-comp :container render))

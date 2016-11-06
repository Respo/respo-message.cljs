
(ns respo-message.comp.msg-list
  (:require [respo.alias :refer [create-comp div]]
            [respo-message.comp.message :refer [comp-message]]))

(defn render [messages]
  (fn [state mutate!]
    (div
     {}
     (let [view-messages (conj messages nil)]
       (->> view-messages (map-indexed (fn [idx message] [idx (comp-message idx message)])))))))

(def comp-msg-list (create-comp :msg-list render))

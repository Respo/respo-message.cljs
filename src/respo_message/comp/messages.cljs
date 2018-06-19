
(ns respo-message.comp.messages
  (:require [respo.macros :refer [defcomp list-> div span <>]]
            [respo.core :refer [create-comp]]
            [respo-message.comp.message :refer [comp-message]]))

(defcomp
 comp-messages
 (messages options on-remove!)
 (list->
  {:style {:overflow :hidden}}
  (->> messages
       vals
       (sort-by (fn [message] (unchecked-negate (:time message))))
       (map-indexed
        (fn [idx message] [(:id message) (comp-message idx message options on-remove!)])))))

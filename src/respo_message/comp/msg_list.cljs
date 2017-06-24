
(ns respo-message.comp.msg-list
  (:require-macros [respo.macros :refer [defcomp div span <>]])
  (:require [respo.core :refer [create-comp]]
            [respo-message.comp.message :refer [comp-message]]))

(defcomp
 comp-msg-list
 (messages op-remove)
 (div
  {}
  (let [view-messages (conj messages nil nil nil)]
    (->> view-messages
         (map-indexed (fn [idx message] [idx (comp-message idx message op-remove)]))))))

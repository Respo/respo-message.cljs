
(ns respo-message.action )

(def clear (gensym "message/clear"))

(def create (gensym "message/create"))

(def remove-one (gensym "message/remove-one"))

(defn message-action? [op] (contains? #{clear create remove-one} op))


(ns respo-message.action )

(def clear (gensym "message/clear"))

(def create (gensym "message/create"))

(def remove-one (gensym "message/remove-one"))

(def dict {:create create, :remove-one remove-one, :clear clear})

(defn message-action? [op] (contains? #{clear create remove-one} op))

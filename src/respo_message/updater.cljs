
(ns respo-message.updater )

(defn remove-one [store op op-data]
  (update store :messages (fn [messages] (subvec messages 0 op-data))))

(defn add-one [store op op-data]
  (update store :messages (fn [messages] (conj messages op-data))))

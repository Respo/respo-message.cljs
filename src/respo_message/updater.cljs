
(ns respo-message.updater (:require [respo-message.schema :as schema]))

(defn update-messages [messages dict op op-data op-id op-time]
  (cond
    (= op (:clear dict)) {}
    (= op (:create dict))
      (assoc messages op-id (merge schema/message op-data {:id op-id, :time op-time}))
    (= op (:remove-one dict))
      (if (some? (:token op-data))
        (->> messages
             (filter (fn [[k message]] (not= (:token op-data) (:token message))))
             (into {}))
        (dissoc messages (:id op-data)))
    :else messages))

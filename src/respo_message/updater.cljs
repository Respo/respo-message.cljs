
(ns respo-message.updater
  (:require [respo-message.schema :as schema] [respo-message.action :as action]))

(defn update-messages [messages op op-data op-id op-time]
  (cond
    (= op action/clear) {}
    (= op action/create)
      (assoc messages op-id (merge schema/message op-data {:id op-id, :time op-time}))
    (= op action/remove-one) (dissoc messages (:id op-data))
    :else messages))


(ns respo-message.util (:require [respo-message.action :as action]))

(defn auto-close-message! [dispatch! op op-data op-id op-time]
  (if (= op action/create)
    (js/setTimeout
     (fn [] (dispatch! action/remove-one {:id op-id, :time op-time, :index nil}))
     (* 1000 (or (:duration op-data) 4)))))

(defn get-env! [property] (aget (.-env js/process) property))

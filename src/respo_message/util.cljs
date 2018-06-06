
(ns respo-message.util )

(defn get-env! [property] (aget (.-env js/process) property))

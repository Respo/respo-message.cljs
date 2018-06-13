
(ns respo-message.util (:require [respo-message.action :as action]))

(defn get-env! [property] (aget (.-env js/process) property))

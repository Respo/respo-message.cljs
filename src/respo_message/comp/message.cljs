
(ns respo-message.comp.message
  (:require [respo.alias :refer [create-comp div]] [respo.comp.text :refer [comp-text]]))

(defn render [idx message] (fn [state mutate!] (div {} (comp-text (pr-str message) nil))))

(def comp-message (create-comp :message render))

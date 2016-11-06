
(ns respo-message.comp.message
  (:require [respo.alias :refer [create-comp div]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.comp.text :refer [comp-text]]))

(def style-zero
  {:min-width 0,
   :background-color "white",
   :width 0,
   :max-width 0,
   :opacity 0,
   :right 160,
   :pointer-events "none"})

(defn on-remove [idx] (fn [e dispatch!] (dispatch! :message/remove idx)))

(def style-message
  {:line-height "32px",
   :min-width 80,
   :text-overflow "ellipsis",
   :color "white",
   :text-align "right",
   :font-size "14",
   :top 8,
   :overflow "hidden",
   :background-color colors/attractive,
   :cursor "pointer",
   :max-width 320,
   :padding "0 16px",
   :right 8,
   :position "absolute",
   :transition-duration "400ms",
   :height 32})

(defn render [idx message]
  (fn [state mutate!]
    (if (some? message)
      (div
       {:style (merge
                style-message
                {:top (+ 8 (* idx 40)),
                 :background-color (case (:kind message)
                   :attactive colors/attractive
                   :irreversible colors/irreversible
                   :attentive colors/attentive
                   :verdant colors/verdant
                   :warm colors/warm
                   colors/attractive)}),
        :event {:click (on-remove idx)}}
       (comp-text (:text message) nil))
      (div {:style (merge style-message style-zero {:top (+ 8 (* idx 40))})}))))

(def comp-message (create-comp :message render))

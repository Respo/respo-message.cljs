
(ns respo-message.comp.message
  (:require [respo.alias :refer [create-comp div]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]
            [respo.comp.text :refer [comp-text]]))

(def style-zero {:pointer-events :none})

(defn on-remove [idx] (fn [e dispatch!] (dispatch! :message/remove idx)))

(def style-message
  {:line-height "32px",
   :min-width 64,
   :text-overflow :ellipsis,
   :color :white,
   :text-align :left,
   :font-size "14",
   :top 8,
   :overflow :hidden,
   :background-color :transparent,
   :cursor :pointer,
   :max-width 320,
   :padding "0 16px",
   :right 8,
   :position :absolute,
   :transition-duration "400ms",
   :height 32})

(defn render [idx message]
  (fn [state mutate!]
    (if (some? message)
      (div
       {:style (merge
                style-message
                {:transform (str "translate(0," (* idx 40) "px)"),
                 :background-color (case (:kind message)
                   :attactive colors/attractive
                   :irreversible colors/irreversible
                   :attentive colors/attentive
                   :verdant colors/verdant
                   :warm colors/warm
                   colors/attractive)}),
        :event {:click (on-remove idx)}}
       (comp-text (:text message) nil))
      (div
       {:style (merge
                style-message
                style-zero
                {:transform (str "translate(-120px," (* idx 40) "px)")})}))))

(def comp-message (create-comp :message render))

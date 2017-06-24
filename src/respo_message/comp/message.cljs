
(ns respo-message.comp.message
  (:require-macros [respo.macros :refer [defcomp div <> span]])
  (:require [respo.core :refer [create-comp]]
            [respo-ui.style :as ui]
            [respo-ui.style.colors :as colors]))

(defn on-remove [idx op-remove]
  (fn [e dispatch!] (dispatch! (if (some? op-remove) op-remove :message/remove) idx)))

(def style-message
  {:position :absolute,
   :top 8,
   :right 8,
   :height 32,
   :line-height "32px",
   :font-size "14",
   :background-color :transparent,
   :color :white,
   :padding "0 16px",
   :min-width 64,
   :text-align :left,
   :overflow :hidden,
   :text-overflow :ellipsis,
   :max-width 320,
   :cursor :pointer,
   :transition-duration "400ms"})

(def style-zero {:pointer-events :none})

(defcomp
 comp-message
 (idx message op-remove)
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
     :event {:click (on-remove idx op-remove)}}
    (<> span (:text message) nil))
   (div
    {:style (merge
             style-message
             style-zero
             {:transform (str "translate(-120px," (* idx 40) "px)")})})))

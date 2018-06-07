
(ns respo-message.comp.message
  (:require [respo.core :refer [create-comp]]
            [respo.macros :refer [defcomp div <> span]]
            [respo-ui.core :as ui]
            [hsl.core :refer [hsl]]))

(def style-message
  {:position :absolute,
   :top 8,
   :right 8,
   :height 32,
   :line-height "32px",
   :font-size "14",
   :background-color (hsl 0 0 100),
   :border (str "1px solid " (hsl 200 50 80)),
   :border-radius "8px",
   :color (hsl 0 0 60),
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
             (:style message)
             {:transform (str "translate(0," (* idx 40) "px)")}),
     :on-click (fn [e d! m!]
       (d! (if (some? op-remove) op-remove :message/remove) (assoc message :index idx)))}
    (<> span (:text message) nil))
   (div
    {:style (merge
             style-message
             style-zero
             {:transform (str "translate(-120px," (* idx 40) "px)")})})))

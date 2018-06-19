
(ns respo-message.comp.message
  (:require [respo.core :refer [create-comp]]
            [respo.macros :refer [defcomp div <> span]]
            [respo-ui.core :as ui]
            [hsl.core :refer [hsl]]
            [respo-message.schema :as schema]))

(def style-message
  {:position :absolute,
   :right 8,
   :height 32,
   :line-height "32px",
   :font-size "14",
   :background-color (hsl 0 0 100),
   :border-style :solid,
   :border-width "1px",
   :border-radius "8px",
   :border-color (hsl 200 50 80),
   :color (hsl 0 0 60),
   :padding "0 16px",
   :min-width 64,
   :text-align :left,
   :overflow :hidden,
   :text-overflow :ellipsis,
   :max-width 320,
   :cursor :pointer,
   :transition-duration "400ms"})

(defcomp
 comp-message
 (idx message options on-remove!)
 (div
  {:style (merge
           style-message
           (:style message)
           (if (:bottom? options)
             {:bottom 8, :transform (str "translate(0," (- (* idx 40)) "px)")}
             {:top 8, :transform (str "translate(0," (* idx 40) "px)")})),
   :on-click (fn [e d! m!]
     (on-remove!
      {:id (:id message), :token (:token message), :index idx, :time (:time message)}
      d!
      m!))}
  (<> span (:text message) nil)))

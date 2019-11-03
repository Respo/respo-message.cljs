
(ns respo-message.comp.message
  (:require [respo.core :refer [defcomp div <> span defeffect]]
            [respo-ui.core :as ui]
            [hsl.core :refer [hsl]]
            [respo-message.schema :as schema]
            [cumulo-util.core :refer [delay!]]))

(defeffect
 effect-fade
 (message)
 (action el *local)
 (case action
   :mount
     (let [style (.-style el)]
       (set! (.-right style) "60px")
       (set! (.-opacity style) "0")
       (delay! 0.01 (fn [] (set! (.-right style) "8px") (set! (.-opacity style) "1"))))
   :unmount
     (let [cloned (.cloneNode el true), style (.-style cloned)]
       (.appendChild (.-parentElement el) cloned)
       (delay! 0.01 (fn [] (set! (.-right style) "60px") (set! (.-opacity style) "0")))
       (delay! 0.3 (fn [] (.remove cloned))))
   (do)))

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
   :transition-duration "300ms"})

(defcomp
 comp-message
 (idx message options on-remove!)
 [(effect-fade message)
  (div
   {:style (merge
            style-message
            (:style message)
            (if (:bottom? options)
              {:bottom 8, :transform (str "translate(0," (- (* idx 44)) "px)")}
              {:top 8, :transform (str "translate(0," (* idx 40) "px)")})),
    :on-click (fn [e d! m!]
      (on-remove!
       {:id (:id message), :token (:token message), :index idx, :time (:time message)}
       d!
       m!))}
   (<> span (:text message) nil))])

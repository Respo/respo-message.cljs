
(ns respo-message.render
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [respo-message.comp.container :refer [comp-container]]
            [respo-message.schema :as schema]))

(def base-info
  {:title "Message", :icon "http://logo.mvc-works.org/mvc.png", :ssr nil, :inner-html nil})

(defn dev-page []
  (make-page "" (merge base-info {:styles [], :scripts ["/main.js" "/browser/main.js"]})))

(defn prod-page []
  (let [html-content (make-string (comp-container schema/store))
        manifest (.parse js/JSON (slurp "dist/manifest.json"))]
    (make-page
     html-content
     (merge
      base-info
      {:styles [(aget manifest "main.css")],
       :scripts [(aget manifest "vendor.js") (aget manifest "main.js")]}))))

(defn main! []
  (if (= js/process.env.env "dev")
    (spit "target/index.html" (dev-page))
    (spit "dist/index.html" (prod-page))))

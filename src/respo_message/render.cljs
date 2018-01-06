
(ns respo-message.render
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [respo-message.comp.container :refer [comp-container]]
            [respo-message.schema :as schema]
            [cljs.reader :refer [read-string]]))

(def base-info
  {:title "Message",
   :icon "http://logo.mvc-works.org/mvc.png",
   :ssr nil,
   :inner-html nil,
   :inline-style [(slurp "entry/main.css")]})

(defn dev-page []
  (make-page "" (merge base-info {:styles [], :scripts ["/lib.js" "/main.js"]})))

(def preview? (= "preview" js/process.env.prod))

(defn prod-page []
  (let [html-content (make-string (comp-container schema/store))
        assets (read-string (slurp "dist/assets.edn"))
        cdn (if preview? "" "http://cdn.tiye.me/respo-message/")
        prefix-cdn (fn [x] (str cdn x))]
    (make-page
     html-content
     (merge
      base-info
      {:styles ["http://cdn.tiye.me/favored-fonts/main-2.css"],
       :scripts (map #(-> % :output-name prefix-cdn) assets),
       :ssr "respo-ssr"}))))

(defn main! []
  (if (= js/process.env.env "dev")
    (spit "target/index.html" (dev-page))
    (spit "dist/index.html" (prod-page))))

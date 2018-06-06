
(ns respo-message.page
  (:require [respo.render.html :refer [make-string]]
            [shell-page.core :refer [make-page spit slurp]]
            [respo-message.comp.container :refer [comp-container]]
            [respo-message.schema :as schema]
            [cljs.reader :refer [read-string]]
            [respo-message.config :as config]
            [respo-message.util :refer [get-env!]]))

(def base-info
  {:title "Message",
   :icon "http://logo.mvc-works.org/mvc.png",
   :ssr nil,
   :inner-html nil,
   :inline-style [(slurp "entry/main.css")]})

(defn dev-page []
  (make-page
   ""
   (merge
    base-info
    {:styles ["/entry/main.css" (:dev-ui config/site)],
     :scripts ["/client.js"],
     :inline-styles []})))

(def local-bundle? (= "local-bundle" (get-env! "mode")))

(defn prod-page []
  (let [html-content (make-string (comp-container schema/store))
        assets (read-string (slurp "dist/assets.edn"))
        cdn (if local-bundle? "" (:cdn-url config/site))
        prefix-cdn (fn [x] (str cdn x))]
    (make-page
     html-content
     (merge
      base-info
      {:styles [(:release-ui config/site)],
       :scripts (map #(-> % :output-name prefix-cdn) assets),
       :ssr "respo-ssr",
       :inline-styles [(slurp "./entry/main.css")]}))))

(defn main! []
  (if (contains? config/bundle-builds (get-env! "mode"))
    (spit "dist/index.html" (prod-page))
    (spit "target/index.html" (dev-page))))

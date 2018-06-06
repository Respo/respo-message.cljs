
(ns respo-message.config (:require [respo-message.util :refer [get-env!]]))

(def bundle-builds #{"release" "local-bundle"})

(def dev?
  (if (exists? js/window)
    (do ^boolean js/goog.DEBUG)
    (not (contains? bundle-builds (get-env! "mode")))))

(def site
  {:storage "respo-message",
   :dev-ui "http://localhost:8100/main.css",
   :release-ui "http://cdn.tiye.me/favored-fonts/main.css",
   :cdn-url "http://cdn.tiye.me/respo-message/",
   :cdn-folder "tiye.me:cdn/respo-message/",
   :title "Message",
   :icon "http://cdn.tiye.me/logo/respo.png",
   :upload-folder "tiye.me:repo/mvc-works/respo-message/"})

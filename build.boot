
(set-env!
  :resource-paths #{"src"}
  :dependencies '[[respo                     "0.5.7"       :scope "provided"]
                  [respo/ui                  "0.1.9"       :scope "provided"]
                  [mvc-works/hsl             "0.1.2"]])

(def +version+ "0.2.0")

(task-options!
  pom {})

(deftask build []
  (comp
    (pom :project     'respo/message
         :version     +version+
         :description "Message component on the right top"
         :url         "https://github.com/Respo/respo-message"
         :scm         {:url "https://github.com/Respo/respo-message"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (install)
    (target)))

(deftask deploy []
  (set-env!
    :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"}]))
  (comp
    (build)
    (push :repo "clojars" :gpg-sign (not (.endsWith +version+ "-SNAPSHOT")))))

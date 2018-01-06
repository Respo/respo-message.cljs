

(defn read-password [guide]
  (String/valueOf (.readPassword (System/console) guide nil)))

(set-env!
  :resource-paths #{"src"}
  :dependencies '[]
  :repositories #(conj % ["clojars" {:url "https://clojars.org/repo/"
                                     :username "jiyinyiyong"
                                     :password (read-password "Clojars password: ")}]))

(def +version+ "0.2.2")

(deftask deploy []
  (comp
    (pom :project     'respo/message
         :version     +version+
         :description "Message component on the right top"
         :url         "https://github.com/Respo/respo-message"
         :scm         {:url "https://github.com/Respo/respo-message"}
         :license     {"MIT" "http://opensource.org/licenses/mit-license.php"})
    (jar)
    (push :repo "clojars" :gpg-sign false)))

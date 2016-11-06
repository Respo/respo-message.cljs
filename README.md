
Respo Message
----

> Message component for Respo apps.

Demo http://repo.respo.site/message/

### Usage

[![Clojars Project](https://img.shields.io/clojars/v/respo/message.svg)](https://clojars.org/respo/message)

```clojure
[respo/message "0.1.0"]
```

You will need these functions:

```clojure
respo-message.updater/add-one
respo-message.updater/remove-one
respo-message.comp.msg-list/comp-msg-list
```

For the store part, it's supposed to have the field `:messages` and two actions:

```clojure
(defonce store-ref (atom {:messages []}))

(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [op-id (id!)
        new-store (case op
                    :message/add
                      (add-one @store-ref op
                       {:id op-id, :kind (rand-nth kinds), :text (rand-nth words)})
                    :message/remove (remove-one @store-ref op op-data)
                    @store-ref)]
    (reset! store-ref new-store)))
```

Mounting the component into tree is simpler:

```clojure
(comp-msg-list (:messages store)))))
```

### Develop

Workflow https://github.com/mvc-works/stack-workflow

### License

MIT

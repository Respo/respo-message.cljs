
Respo Message
----

> Message component for Respo apps.

Demo http://repo.respo.site/message/

### Usage

[![Clojars Project](https://img.shields.io/clojars/v/respo/message.svg)](https://clojars.org/respo/message)

```clojure
[respo/message "0.3.1"]
```

You will need:

```clojure
respo-message.action/message-action?
respo-message.action/create
respo-message.action/clear
respo-message.action/remove-one
respo-message.updater/update-messages
respo-message.comp.messages/comp-messages
```

To mount component and show a message, by default it shows for 4 seconds:

```clojure
(comp-messages (:messages store) {:bottom? true})
```

```clojure
(dispatch! action/create {:text (lorem-ipsum), :token "xxx"}))

(dispatch! action/remove-one {:token "xxx"})
```

Messages can be removed with `:id` or `:token`, where `:token` is what you can generate.

Sorry but the component gets even harder to setup:

```clojure
(defn dispatch! [op op-data]
  (println "dispatch!" op op-data)
  (let [op-id (.generate shortid), op-time (.now js/Date), store @*store]
    (reset!
     *store
     (cond
       (= op :states) (update store :states (mutate op-data))
       (action/message-action? op)
         (update store :messages #(update-messages % op op-data op-id op-time))
       :else (do (println "Unhandled operation:" op) store)))))
```

### Develop

Workflow https://github.com/mvc-works/calcit-workflow

### License

MIT

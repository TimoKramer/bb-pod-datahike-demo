(ns pod
  (:require [babashka.pods :as pods]))

(pods/load-pod "./dhi")

(require '[datahike.pod :as d])

(def config {:store  {:backend :file
                      :path "/tmp/dh-shared-db"}
             :keep-history? true
             :schema-flexibility :read})

(d/delete-database config)

(d/create-database config)

(def conn (d/connect config))

(d/transact conn [{:name  "Alice", :age   20}
                  {:name  "Bob", :age   30}
                  {:name  "Charlie", :age   40}
                  {:age 15}])

(d/q '[:find ?e ?n ?a
       :where
       [?e :name ?n]
       [?e :age ?a]]
     (d/db conn))

(d/transact conn {:tx-data [{:db/id 3 :age 25}]})

(d/q {:query '{:find [?e ?n ?a]
               :where
               [[?e :name ?n]
                [?e :age ?a]]}
      :args [(d/db conn)]})

(d/pull (d/db conn) '[*] 3)

(d/pull-many (d/db conn) '[*] [1 2 3])

(def as-of (d/as-of (d/db conn) {:tx-id 536870913}))

(d/q {:query '{:find [?e ?n ?a]
               :where
               [[?e :name ?n]
                [?e :age ?a]]}
      :args [as-of]})

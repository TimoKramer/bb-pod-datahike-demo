#!/usr/bin/env bb

(require '[babashka.pods :as pods]
         '[clojure.java.io :as io]
         '[clojure.string :as s])

(pods/load-pod "./dhi")

(require '[datahike.pod :as d])

(def config {:store  {:backend :file
                      :path "/tmp/dh-shared-db"}
             :keep-history? true
             :schema-flexibility :read})

(defn -main [& _args]
  (d/delete-database config)
  (d/create-database config)
  (let [conn (d/connect config)
        raw (->> (io/reader *in*)
                 line-seq)]
    (->> raw
         (map s/trim)
         (map #(s/split % #" "))
         (map (fn [[count method path]]
                {:count count :method (subs method 1) :path path}))
         (d/transact conn))))

(when (= *file* (System/getProperty "babashka.file"))
  (apply -main *command-line-args*))

(comment
  (d/q '[:find (count ?e) .
         :where
         [?e :count _]]
       (d/db (d/connect config)))
  (d/q '[:find ?e ?v
         :where
         [?e _ ?v]]
       (d/db (d/connect config))))

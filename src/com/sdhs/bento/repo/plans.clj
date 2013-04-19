(ns com.sdhs.bento.repo.plans
  (:use com.sdhs.bento.repo.db
        clj-time.local
        clj-time.coerce)
  (:require [clojureql.core :as cql]))


(defn get-by-now []
  @(-> (cql/select (cql/table db :plans)
     (cql/where (and (<= :startTime (to-date (local-now))) (>= :endTime (to-date (local-now))))))
  ))
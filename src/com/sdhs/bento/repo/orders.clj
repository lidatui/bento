(ns com.sdhs.bento.repo.orders
  (:use com.sdhs.bento.repo.db)
  (:require [clojureql.core :as cql]))


(defn find-all [page limit]
  @(-> (cql/table db :orders)
     (cql/sort [:id#desc])
     (cql/take (+ limit (* page limit)))
     (cql/drop (* page limit))
   ))
(defn count-all []
  (:cnt (first @(-> (cql/table db :orders) (cql/aggregate [:count/id :as :cnt])))))

(defn save [order]
  @(cql/update-in! (cql/table db :orders) (cql/where (= :id (:id order))) order))

(defn delete [id]
  @(cql/disj! (cql/table db :orders) (cql/where (= :id id))))
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
  (:cnt (first @(-> (cql/table db :orders) (cql/aggregate [:count/id :as :cnt]))))
)

(defn save [order]
  (println order)
  @(cql/update-in! (cql/table db :orders) (cql/where (= :id (:id order))) order)
)

(defn save-list [planId createUserId userIds]
  (println userIds)
  (dorun (map (fn [userId]
                (save {:planId planId
                       :userId userId
                       :createUserId createUserId
                       :createTime (java.util.Date.)}))
              userIds))
)

(defn delete [id]
  @(cql/disj! (cql/table db :orders) (cql/where (= :id id)))
)
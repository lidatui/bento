(ns com.sdhs.bento.repo.orders
  (:use com.sdhs.bento.repo.db
        [clj-time.core :only (date-time year month)]
        clj-time.local
        clj-time.coerce)
  (:require [clojureql.core :as cql]))

(defn startMonth [date]
  (to-date (date-time (year date) (month date) 1)))

(defn endMonth [date]
  (to-date (date-time (year date) (+ (month date) 1) 1)))

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


(defn get-count-by-id [planId]
  (:cnt (first  @(-> (cql/select (cql/table db :orders)
                                 (cql/where (= :planId planId)))
                     (cql/aggregate [:count/id :as :cnt])
                 )))
)

(defn get-month-count []
  (:cnt (first  @(-> (cql/select (cql/table db :orders)
                       (cql/where (and (>= :createTime (startMonth (local-now))) (< :createTime (endMonth (local-now))))))

                     (cql/aggregate [:count/id :as :cnt])
                ))
  )
)


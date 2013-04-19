(ns com.sdhs.bento.repo.users
  (:use com.sdhs.bento.repo.db)
  (:require [clojureql.core :as cql]))


(defn find-all
  ([]
    @(-> (cql/table db :users)))

  ([page limit]
    @(-> (cql/table db :users)
     (cql/sort [:id#desc])
     (cql/take (+ limit (* (- page 1) limit)))
     (cql/drop (* (- page 1) limit))
   )))
(defn count-all []
  (:cnt (first @(-> (cql/table db :users) (cql/aggregate [:count/id :as :cnt])))))

(defn save [user]
  @(cql/update-in! (cql/table db :users) (cql/where (= :id (:id user))) user))

(defn delete [id]
  @(cql/disj! (cql/table db :users) (cql/where (= :id id))))
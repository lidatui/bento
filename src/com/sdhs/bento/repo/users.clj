(ns com.sdhs.bento.repo.users
  (:require [clojureql.core :as cql]))


(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :user "root"
         :password ""
         :subname "//127.0.0.1:3306/bento?useUnicode=true&characterEncoding=UTF-8"})

(alter-var-root #'clojureql.core/*debug* (constantly true))

(defn find-all [page limit]
  @(-> (cql/table db :users)
     (cql/sort [:id#desc])
     (cql/take (+ limit (* page limit)))
     (cql/drop (* page limit))
   ))
(defn count-all []
  (:cnt (first @(-> (cql/table db :users) (cql/aggregate [:count/id :as :cnt])))))

(defn save [user]
  @(cql/update-in! (cql/table db :users) (cql/where (= :id (:id user))) user))

(defn delete [id]
  @(cql/disj! (cql/table db :users) (cql/where (= :id id))))
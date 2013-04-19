(ns com.sdhs.bento.web.routes
  (:use compojure.core
        com.sdhs.bento.web.view.index
        com.sdhs.bento.web.view.main
        com.sdhs.bento.web.view.users
        [hiccup.middleware :only (wrap-base-url)])
  (:require [clojureql.core :as cql]
            [compojure.route :as route]
            [compojure.handler :as handler]
            [compojure.response :as response]
            [clj-json.core :as json]
            [com.sdhs.bento.repo.users :as users]
            [com.sdhs.bento.repo.plans :as plans]
            [com.sdhs.bento.repo.orders :as orders]))


(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json;charset=utf-8"}
   :body (json/generate-string data)})


(defroutes main-routes
  (GET "/" [] (index-page) )

  (GET "/main.html" [] (main-page (users/find-all)))


  ;user
  (GET "/users.html" [] (users-page))
  (GET "/users" {params :params}
    (json-response {:items (users/find-all (. Integer parseInt (:page params)) (. Integer parseInt (:limit params)))
                    :totalCount (users/count-all)}))
  (POST "/user" {params :params}
    (users/save params)
    (json-response {:msg "success"}))
  (DELETE "/user" {params :params}
    (users/delete (:id params))
    (json-response {:msg "success"}))

  ;plan
  (GET "/plan/now" [] (json-response {:id (:id (first (plans/get-by-now)))}))

  ;order

  (POST "/order" {params :params}
    (orders/save-list
      (:planId params)
      (:createUserId params)
      (:userIds params))
    (json-response {:msg "success"}))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
    (wrap-base-url)))






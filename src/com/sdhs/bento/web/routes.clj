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
            [com.sdhs.bento.repo.users :as users]))


(defn json-response [data & [status]]
  {:status (or status 200)
   :headers {"Content-Type" "application/json;charset=utf-8"}
   :body (json/generate-string data)})


(defroutes main-routes
  (GET "/" [] (index-page) )

  (GET "/main.html" [] (main-page))

  (GET "/users.html" [] (users-page))
  (GET "/users" {params :params} (json-response {:items (users/find-all (. Integer parseInt (:page params)) (. Integer parseInt (:limit params)))
                                                 :totalCount (users/count-all)}))
  (POST "/user" {params :params} (users/save params) (json-response {:msg "success"}))
  (DELETE "/user" {params :params} (println (:id params)) (users/delete (:id params)) (json-response {:msg "success"}))

  (route/resources "/")
  (route/not-found "Page not found"))

(def app
  (-> (handler/site main-routes)
    (wrap-base-url)))






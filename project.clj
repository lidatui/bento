(defproject bento "0.1.0"
  :description ""
  :dependencies [[org.clojure/clojure "1.4.0"]
                 [compojure "1.1.5"]
                 [hiccup "1.0.3"]
                 [clj-json "0.5.3"]
                 [clj-time "0.5.0"]
                 [clojureql "1.0.4"]
                 [mysql/mysql-connector-java "5.1.24"]]
  :plugins [[lein-ring "0.8.3"]]
  :ring {:handler com.sdhs.bento.web.routes/app
         :auto-reload? true
         :auto-refresh true})
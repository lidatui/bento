(ns com.sdhs.bento.repo.db)

(alter-var-root #'clojureql.core/*debug* (constantly true))

(def db {:classname "com.mysql.jdbc.Driver"
         :subprotocol "mysql"
         :user "root"
         :password ""
         :subname "//127.0.0.1:3306/bento?useUnicode=true&characterEncoding=UTF-8"})

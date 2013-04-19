(ns com.sdhs.bento.web.view.index
  (:use [hiccup core page]))

(defn index-page []
  (html5
    [:head
      [:title "饭盒"]
      (include-css "css/normalize.css")
      (include-css "bootstrap/css/bootstrap.min.css")
      (include-css "css/global.css")
    ]
    [:body
      [:header
        [:hgroup
          [:h1 "饭盒"]
          [:h2 "Style Guide - November 1, 2012"]
        ]
        [:nav
          [:h3 "配置"]
          [:ul
            [:li [:a {:href "users.html"} "人员管理"]]
          ]
        ]
      ]
      [:div#main
        [:iframe {:src "main.html"}]
      ]

      [:script {:src "js/vendor/jquery-1.9.1.min.js"}]
      [:script {:src "js/plugins.js"}]
      [:script {:src "bootstrap/js/bootstrap.min.js"}]
      [:script {:src "js/index.js"}]
    ]

  )
)
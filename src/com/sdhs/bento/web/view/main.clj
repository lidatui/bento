(ns com.sdhs.bento.web.view.main
  (:use [hiccup core page]))

(defn main-page []
  (html5
    [:head
      [:title "饭盒"]
      (include-css "css/normalize.css")
      (include-css "bootstrap/css/bootstrap.min.css")
      (include-css "css/global.css")
      (include-css "css/main.css")
    ]
    [:body
      [:main
        [:section
          [:h1
            [:span "首页"]
          ]
          [:div.row-fluid.stat
            [:div.span4
              [:div.title "本月晚饭预定人次累计："]
              [:div.value "246"]
            ]
            [:div.span4
              [:div.title "今天晚饭已预定人数："]
              [:div.value "15"]
            ]
            [:div.span4
              [:div.title "您今晚在公司吃饭吗？"]
              [:div {:style "text-align: center;padding:10px;"}
                [:button.btn.btn-primary.btn-large "我要预订！"]
              ]
            ]
          ]
          [:div#chart-line]

        ]
      ]

      [:script {:src "js/vendor/jquery-1.9.1.min.js"}]
      [:script {:src "js/plugins.js"}]
      [:script {:src "bootstrap/js/bootstrap.min.js"}]
      [:script {:src "js/vendor/highcharts.js"}]
      [:script {:src "js/main.js"}]
    ]

  )
)
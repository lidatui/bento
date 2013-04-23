(ns com.sdhs.bento.web.view.main
  (:use [hiccup core page]))

(defn main-page [users]
  (html5
    [:head
      [:title "饭盒"]
      (include-css "css/normalize.css")
      (include-css "bootstrap/css/bootstrap.min.css")
      (include-css "js/vendor/select2/select2.css")
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
              [:div#orderMonthCount.value "0"]
            ]
            [:div.span4
              [:div.title "今天晚饭已预定人数："]
              [:div#orderCount.value "0"]
            ]
            [:div.span4
              [:div.title "您今晚在公司吃饭吗？"]
              [:div {:style "text-align: center;padding:10px;"}
                [:button#order-add.btn.btn-primary.btn-large {:disable "disable"} "别着急，预订还没开始"]
              ]
            ]
          ]
          [:div#chart-line]

          ;预定窗口
          [:div#order-modal.modal.hide.fade
            {:tabindex "-1"
              :role "dialog"
              :aria-labelledby "orderLabel"
              :aria-hidden "true"}

            [:div.modal-header
              [:button.close {:type "button" :data-dismiss "modal" :aria-hidden "true"} "×"]
              [:h3#orderLabel "预定晚饭"]
            ]
            [:div.modal-body
              [:div.alert.alert-error.hide "一切正常"]
              [:form.form-horizontal
                [:div.control-group
                  [:label.control-label "预定人员"]
                  [:div.controls
                    [:select#orderUser {:multiple "multiple"}
                     (map (fn [user] [:option {:value (:id user)} (:name user)]) users)
                    ]
                    [:span.help-inline.hide "预定人员不能为空."]
                  ]
                ]
              ]
            ]
            [:div.modal-footer
              [:button.btn {:type "button" :data-dismiss "modal" :aria-hidden "true"} "关闭"]
              [:button#order-save.btn.btn-primary "保存"]
            ]

          ]
        ]
      ]

      [:script {:src "js/vendor/jquery-1.9.1.min.js"}]
      [:script {:src "js/plugins.js"}]
      [:script {:src "bootstrap/js/bootstrap.min.js"}]
      [:script {:src "js/vendor/highcharts.js"}]
      [:script {:src "js/vendor/select2/select2.min.js"}]
      [:script {:src "js/vendor/select2/select2_locale_zh-CN.js"}]
      [:script {:src "js/main.js"}]
    ]

  )
)
(ns com.sdhs.bento.web.view.users
  (:use [hiccup core page]))

(defn users-page []
  (html5
    [:head
      [:meta {:charset "utf-8"}]
      [:title "人员管理"]
      (include-css "css/normalize.css")
      (include-css "bootstrap/css/bootstrap.min.css")
      (include-css "css/global.css")
      (include-css "css/users.css")
    ]
    [:body
      [:main
        [:section
          [:h1
            [:span "人员管理"]
          ]
          [:p "维护站点访问的用户列表"]
          [:div.row-fluid
            [:div.span10
              [:table.table.table-bordered.table-striped
                [:thead
                  [:tr [:th "#"] [:th "姓名"] [:th "RTX"] [:th "IP地址"]]
                ]
                [:tbody#user-body]
              ]
              [:div.pagination.pagination-right
                [:ul#userPaginator]
              ]
            ]
            [:div.span2
              [:button#user-add.btn.btn-block.btn-success "添加"]
              [:button#user-edit.btn.btn-block.btn-primary "编辑"]
              [:button#user-remove.btn.btn-block.btn-danger "删除"]
              [:div#user-modal.modal.hide.fade
                {:tabindex "-1"
                 :role "dialog"
                 :aria-labelledby "userLabel"
                 :aria-hidden "true"}

                [:div.modal-header
                  [:button.close {:type "button" :data-dismiss "modal" :aria-hidden "true"} "×"]
                  [:h3#userLabel "人员-属性"]
                ]
                [:div.modal-body
                  [:div.alert.alert-error.hide "一切正常"]
                  [:form.form-horizontal
                    [:div.control-group
                      [:label.control-label "人员姓名"]
                      [:div.controls
                        [:input#txtName {:type "text"}]
                        [:span.help-inline.hide "人员姓名不能为空."]
                      ]
                    ]
                    [:div.control-group
                      [:label.control-label "RTX账号"]
                      [:div.controls
                        [:input#txtRtx {:type "text"}]
                      ]
                    ]
                    [:div.control-group
                      [:label.control-label "IP地址"]
                      [:div.controls
                        [:input#txtIp {:type "text"}]
                        [:span.help-inline.hide "IP地址不能为空."]
                      ]
                    ]
                  ]
                ]
                [:div.modal-footer
                  [:button.btn {:type "button" :data-dismiss "modal" :aria-hidden "true"} "关闭"]
                  [:button#user-save.btn.btn-primary "保存"]
                ]

              ]
            ]
          ]


        ]
      ]

      [:script {:src "js/vendor/jquery-1.9.1.min.js"}]
      [:script {:src "js/plugins.js"}]
      [:script {:src "bootstrap/js/bootstrap.min.js"}]
      [:script {:src "js/vendor/highcharts.js"}]
      [:script {:src "js/users.js"}]
    ]

  )
)
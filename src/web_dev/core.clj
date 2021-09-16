(ns web-dev.core
  (:require [ring.adapter.jetty :as jetty]
            [compojure.core :as comp]
            [compojure.route :as route]
            [ring.middleware.params :refer [wrap-params]]
            [ring.middleware.keyword-params :refer [wrap-keyword-params]]
            [clojure.pprint :as pprint]))

(comp/defroutes
 routes
 (comp/GET "/" []
           {:status 200
            :body "<h1>My site</h1>
                           <ul>
                               <li><a href=/echo>Echo request</a></li>
                               <li><a href=/greeting>Greeting</a></li>
                           </ul>"
            :headers {"Content-Type" "text/html; charset=UTF-8"}})
 (comp/ANY "/echo" req
           {:status 200
            :body (with-out-str (pprint/pprint req))
            :headers {"Content-Type" "text/plain"}})
 (comp/GET "/greeting" req
           (let [qs (:params req)]
             {:status 200
              :body (str (:g qs) ", world")
              :headers {"Content-Type" "text/plain"}}))
 (comp/GET "/test" [] "Foo")
 (comp/GET
  "/user/:id/posts/:post-id"
  [id post-id comments-count]
  (str "Get " comments-count " comments for user with id " id " and post with id " post-id))
 (route/not-found {:status 404
                   :body "Not found."
                   ::headers {"Content-Type" "text/plain"}}))

(def app
  (-> #'routes
      wrap-keyword-params
      wrap-params))

(defonce server (atom nil))

(defn start-server
  []
  (reset! server
          (jetty/run-jetty #'app {:port 3001
                                  :join? false})))

(defn stop-server
  []
  (when-some [s @server]
    (.stop s)
    (reset! server nil)))

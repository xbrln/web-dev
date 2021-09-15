(ns web-dev.core
  (:require [ring.adapter.jetty :as jetty]
            [clojure.pprint :as pprint]))

(defn app
  [req]
  (case (:uri req)
    "/" {:status 200
         :body "<h1>Homepage</h1>
                <ul>
                    <li><a href=/echo>Echo request</a></li>
                    <li><a href=/greeting>Greeting</a></li>
                </ul>"
         :headers {"Content-Type" "text/html; charset=UTF-8"}}
    "/echo" {:status 200
             :body (with-out-str (pprint/pprint req))
             :headers {"Content-Type" "text/plain"}}
    "/greeting" {:status 200
                 :body "Hello world"
                 :headers {"Content-Type" "text/plain"}}
    {:status 404
     :body "Not found."
     ::headers {"Content-Type" "text/plain"}}))

(defonce server (atom nil))

(defn start-server
  []
  (reset! server
          (jetty/run-jetty (fn [req] (app req))
                           {:port 3001
                            :join? false})))

(defn stop-server
  []
  (when-some [s @server]
    (.stop s)
    (reset! server nil)))

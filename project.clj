(defproject web-dev "0.1.0"
  :description "Learning Ring"
  :url "https://github.com/xbrln/web-dev"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.10.1"]
                 [ring/ring-core "1.9.1"]
                 [ring/ring-jetty-adapter "1.9.1"]
                 [compojure "1.6.2"]]
  :repl-options {:init-ns web-dev.core})

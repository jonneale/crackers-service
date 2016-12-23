(defproject crackers-service "0.1.0-SNAPSHOT"
  :description "Service for producing Christmas Cracker jokes on demand"
  :url "http://example.com/FIXME"
  :license {:name "Eclipse Public License"
            :url "http://www.eclipse.org/legal/epl-v10.html"}
  :dependencies [[org.clojure/clojure "1.8.0"]
                 [compojure "1.5.1"]
                 [ring "1.5.0"]
                 [org.clojure/data.json "0.2.3"]
                 [org.clojure/data.csv "0.1.2"]]
  :plugins [[lein-ring "0.8.5"]]
  :ring {:handler crackers-service.core/app}
  :main crackers-service.core)

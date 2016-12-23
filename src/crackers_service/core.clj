(ns crackers-service.core
  (:require [compojure.core     :as compojure :refer [GET]]
            [ring.adapter.jetty     :as jetty]
            [ring.middleware.params :as params]
            [clojure.data.csv       :as csv]
            [clojure.data.json      :as json]
            [clojure.java.io        :as io]))

(def path "joke-list.csv")


(def helpful-text
  "
<h1>REST interface</h1>
<pre>
GET   helpful tect                           <a href='/'>/</a>
GET   a single joke                          <a href='/jokes'>/jokes</a>
GET   some number of jokes                   <a href='/jokes/10'>/jokes/:number</a>
</pre>")

(def jokes
  (with-open [joke-file (io/reader (io/resource path))]
    (doall
     (for [[set-up punchline] (csv/read-csv joke-file)]
       {:set-up set-up
        :punchline punchline}))))

(compojure/defroutes app
  ;;http://localhost:8080
  (GET "/"
    []
    helpful-text)

  (GET "/jokes"
    []
    {:status  200
     :headers {"Content-Type"   "application/json"
               "Vary"           "Accept-Encoding"}
     :body    (json/json-str (take 1 (shuffle jokes)))})

  (GET "/jokes/:number"
    [number]
    (let [number-of-jokes (Integer/parseInt number)]
      {:status  200
       :headers {"Content-Type"   "application/json"
                 "Vary"           "Accept-Encoding"}
       :body    (json/json-str (take number-of-jokes (shuffle jokes)))})))

(defn -main [& args]
  (println (str "Starting the switch-api on port 8080...."))
  (jetty/run-jetty (params/wrap-params app) {:port 8080}))

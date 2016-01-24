(ns authkey-generator.run
  (:require [com.stuartsierra.component :as component]
            [environ.core :refer (env)]
            [authkey-generator.core :as ag])
  (:gen-class))

(defn parse-config
  [conf]
  (cond-> conf
    (:ag-port conf) (Long/parseLong (:ag-port conf))))

(defonce system nil)

(defn -main
  [& args]
  (println "Starting authkey generator web API.")
  (alter-var-root #'system (constantly (ag/map->WebAPI {:config (parse-config env)})))
  (alter-var-root #'system component/start))

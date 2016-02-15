(ns authkey-generator.core
  (:require [clojure.set :as set]
            [com.stuartsierra.component :as component]
            [sauerworld.cube2.crypto :as crypto]
            [hiccup.core :refer (html)]
            [immutant.web :as web]))

(defn render-key
  "Generates HTML view of a key."
  [{:keys [private public]}]
  (html [:dl
         [:dt "Private Key"]
         [:dd (format "/authkey \"player\" \"%s\" \"SL\"; saveauthkeys"
                      private)]
         [:dt "Public Key"]
         [:dd (str "player " public)]]))

(defn generate-key
  "Takes any number of args, returns map of
   {:private priv-key-str
    :public  pub-key-str}"
  [& _]
  (let [priv (crypto/make-privkey)]
    {:private (.toString priv 16)
     :public (crypto/get-pubkey priv)}))

(defn handler
  [req]
  {:status 200
   :headers {"Content-Type" "text/html"}
   :body (render-key (generate-key))})

(defrecord WebAPI
    [config server-handle]

  component/Lifecycle
  (start [this]
    (if server-handle ;; already started
      this
      (let [options (-> config
                        (select-keys [:ag-port :ag-host])
                        (set/rename-keys {:ag-port :port :ag-host :host}))
            server-handle (web/run handler options)]
        (assoc this :server-handle server-handle))))

  (stop [this]
    (if server-handle ;;started
      (do (web/stop server-handle)
          (assoc this :server-handle nil))
      this)))

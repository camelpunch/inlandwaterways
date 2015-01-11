(ns inlandwaterways.core
  (:require [clojure.browser.repl :as repl]
            [goog.events :as events]))

;; (repl/connect "http://localhost:9000/repl")

(enable-console-print!)

(println "Hello world!")

(def map-opts {:center { :lat -34.397 :lng 150.644 }
               :zoom 8})

(defn map-load []
  (js/google.maps.Map. (.getElementById js/document "map-canvas")
                       (clj->js map-opts)))

(events/listen js/window "load" map-load)

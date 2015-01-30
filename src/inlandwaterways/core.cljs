(ns inlandwaterways.core
  (:require [clojure.browser.repl :as repl]
            [ajax.core :refer [GET]]
            [goog.events :as events]))

(enable-console-print!)

(def map-opts {:center {:lat 53.0672367 :lng -2.52393}
               :zoom 9})

(defn create-polyline [coords]
  (js/google.maps.Polyline. (clj->js {:path coords
                                      :geodesic true
                                      :strokeColor "#000000"
                                      :strokeOpacity 1.0,
                                      :strokeWeight 4})))

(defn init []
  (let [gmap (js/google.maps.Map. (.getElementById js/document "map-canvas")
                                  (clj->js map-opts))]
    (GET "http://localhost:3000/centrelines"
         {:params {"q" "Lee Navigation"}
          :handler (fn [sections]
                     (.setCenter gmap (clj->js (ffirst sections)))
                     (let [polylines (map create-polyline sections)]
                       (doseq [polyline polylines]
                         (.setMap polyline gmap))))})))

(events/listen js/window "load" init)

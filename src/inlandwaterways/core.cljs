(ns inlandwaterways.core
  (:require [clojure.browser.repl :as repl]
            [goog.events :as events]))

(enable-console-print!)

(def map-opts {:center { :lat 53.0672367 :lng -2.52393 }
               :zoom 8})

(def coords [{:lat 53.0672367 :lng -2.5239}
             {:lat 50 :lng -2.52}])

(def polyline
  (js/google.maps.Polyline. (clj->js {:path coords
                                      :geodesic true
                                      :strokeColor "#FF0000"
                                      :strokeOpacity 1.0,
                                      :strokeWeight 2})))

(defn map-load []
  (let [map (js/google.maps.Map. (.getElementById js/document "map-canvas")
                                 (clj->js map-opts))]
    (.setMap polyline map)))

(events/listen js/window "load" map-load)

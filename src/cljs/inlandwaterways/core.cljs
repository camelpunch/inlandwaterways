(ns inlandwaterways.core
  (:require [clojure.browser.repl :as repl]
            [clojure.string :refer [split]]
            [goog.events :as events]))

(enable-console-print!)

(def northings-eastings '[(["364129.937596856" "348351.139023636"] ["364131.249196855" "348347.218423635"])])

(def map-opts {:center { :lat 53.0672367 :lng -2.52393 }
               :zoom 8})

(defn os2latlng [x y]
  (let [osref (js/OSRef. x y)
        latlng (.toLatLng osref)]
    (.OSGB36ToWGS84 latlng)
    {:lat (.-lat latlng)
     :lng (.-lng latlng)}))

(def sections (map (fn [input-section] (map #(apply os2latlng %) input-section))
                   northings-eastings))

(defn create-polyline [coords]
  (js/google.maps.Polyline. (clj->js {:path coords
                                      :geodesic true
                                      :strokeColor "#000000"
                                      :strokeOpacity 1.0,
                                      :strokeWeight 4})))

(def polylines (map create-polyline sections))

(defn map-load []
  (let [map (js/google.maps.Map. (.getElementById js/document "map-canvas")
                                 (clj->js map-opts))]
    (doseq [polyline polylines]
      (.setMap polyline map))))

(events/listen js/window "load" map-load)

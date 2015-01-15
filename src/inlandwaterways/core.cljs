(ns inlandwaterways.core
  (:require [clojure.browser.repl :as repl]
            [goog.events :as events]))

(enable-console-print!)

(def map-opts {:center { :lat 53.0672367 :lng -2.52393 }
               :zoom 8})

(def shroppie-coord [364129.937596856,348351.139023636])

(defn os2latlng [x y]
  (let [osref (js/OSRef. x y)
        latlng (.toLatLng osref)]
    (.OSGB36ToWGS84 latlng)
    {:lat (.-lat latlng)
     :lng (.-lng latlng)}))

(def coords [{:lat 53.0672367 :lng -2.5239}
             (apply os2latlng shroppie-coord)])

(def polyline
  (js/google.maps.Polyline. (clj->js {:path coords
                                      :geodesic true
                                      :strokeColor "#000000"
                                      :strokeOpacity 1.0,
                                      :strokeWeight 4})))

(defn map-load []
  (let [map (js/google.maps.Map. (.getElementById js/document "map-canvas")
                                 (clj->js map-opts))]
    (.setMap polyline map)))

(events/listen js/window "load" map-load)

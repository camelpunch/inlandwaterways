(ns inlandwaterways.convert
  (:require [clojure.zip :as zip]
            [clojure.java.io :as io]
            [clojure.data.zip.xml :refer :all]
            [clojure.xml :as xml]))

(def gml (-> "Canal_Centreline.gml"
             io/file
             xml/parse
             zip/xml-zip))

(defn raw-lines [waterway]
  (xml-> gml
         :gml:featureMember
         :gml2:Canal_Centreline
         [:gml2:SAP_NAME (text= waterway)]
         :gml:lineStringProperty
         text))

(defn split-comma [str] clojure.string/split str #",")
(defn split-space [str] clojure.string/split str #" ")

(defn comma-sep-coords [waterway]
  (map split-space (raw-lines waterway)))

(defn coords [waterway]
  (let [comma-sep (comma-sep-coords waterway)]
    (map #(map split-comma %) comma-sep)))

(first (first (comma-sep-coords "Shropshire Union Canal")))

(def all-coords (xml-> gml
                       :gml:featureMember
                       :gml2:Canal_Centreline
                       :gml:lineStringProperty
                       text))

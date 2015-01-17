(ns inlandwaterways.convert
  (:require [clojure.zip :as zip]
            [clojure.java.io :as io]
            [clojure.data.zip.xml :refer :all]
            [clojure.xml :as xml]))

(defn frm-save
  "Save a clojure form to file. Stolen from https://groups.google.com/forum/#!topic/clojure/y1JG0HFCo9w"
  [#^java.io.File file form]
  (with-open [w (java.io.FileWriter. file)]
    (binding [*out* w *print-dup* true] (prn form))))

(def gml (-> "Canal_Centreline.gml"
             io/file
             xml/parse
             zip/xml-zip))

(def sap-names
  (sort (set (xml-> gml
                    :gml:featureMember
                    :gml2:Canal_Centreline
                    :gml2:SAP_NAME
                    text))))

(comment
  (count sap-names)
  (frm-save (io/file "sapnames.txt") sap-names))

(defn raw-lines [waterway]
  (xml-> gml
         :gml:featureMember
         :gml2:Canal_Centreline
         [:gml2:SAP_NAME (text= waterway)]
         :gml:lineStringProperty
         text))

(defn split-comma [str] (clojure.string/split str #","))
(defn split-space [str] (clojure.string/split str #" "))

(defn comma-sep-coords [waterway]
  (map split-space (raw-lines waterway)))

(defn sections [waterway]
  (let [comma-sep (comma-sep-coords waterway)]
    (map (fn [pairs] (map split-comma pairs)) comma-sep)))

(frm-save (io/file "shropshireunion.edn")
          (sections "Shropshire Union Canal"))

(def all-coords (xml-> gml
                       :gml:featureMember
                       :gml2:Canal_Centreline
                       :gml:lineStringProperty
                       text))

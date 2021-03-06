(defproject inlandwaterways "0.1.0-SNAPSHOT"
  :description "FIXME: write this!"
  :url "http://example.com/FIXME"

  :dependencies [[org.clojure/clojure "1.6.0"]
                 [cljs-ajax "0.3.9"]
                 [org.clojure/clojurescript "0.0-2665"]]

  :node-dependencies [[source-map-support "0.2.8"]]

  :plugins [[lein-cljsbuild "1.0.4"]
            [lein-npm "0.4.0"]]

  :source-paths ["src" "target/classes"]

  :clean-targets ["out/inlandwaterways" "inlandwaterways.js" "inlandwaterways.min.js"]

  :profiles {:dev {:dependencies [[org.clojure/tools.nrepl "0.2.7"]]
                   :plugins [[com.cemerick/austin "0.1.6"]
                             [cider/cider-nrepl "0.8.1"]]}}

  :cljsbuild {:builds [{:id "dev"
                        :source-paths ["src"]
                        :compiler {:output-to "inlandwaterways.js"
                                   :output-dir "out"
                                   :optimizations :none
                                   :cache-analysis true
                                   :source-map true}}
                       {:id "release"
                        :source-paths ["src"]
                        :compiler {:output-to "inlandwaterways.min.js"
                                   :pretty-print false
                                   :optimizations :advanced}}]})

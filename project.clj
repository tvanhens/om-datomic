(defproject om-datomic "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url         "http://example.com/FIXME"
  
  :license
  {:name "Eclipse Public License"
   :url "http://www.eclipse.org/legal/epl-v10.html"}
  
  :dependencies
  [[org.clojure/clojure       "1.7.0"]
   [org.clojure/clojurescript "0.0-3308"
    :classifier "aot"
    :scope "provided"]
   [org.clojure/core.async    "0.1.346.0-17112a-alpha"]
   [org.omcljs/om             "0.9.0"]]
  
  :plugins
  [[lein-cljsbuild "1.0.6"]
   [lein-figwheel  "0.3.7"]]

  :profiles
  {:dev
   {:dependencies
    [[com.cemerick/piggieback "0.2.1"]]
    :repl-options
    {:nrepl-middleware [cemerick.piggieback/wrap-cljs-repl]}}}
  
  :cljsbuild
  {:builds
   [{:id           "dev"
     :source-paths ["src/"]
     :figwheel     true
     :compiler
     {:main       "om-datomic.core"
      :asset-path "js/out"
      :output-to  "resources/public/js/app.js"
      :output-dir "resources/public/js/out"}}]})

(defproject aoc-2024 "0.1.0-SNAPSHOT"
  :description "FIXME: write description"
  :url "http://example.com/FIXME"
  :license {:name "EPL-2.0 OR GPL-2.0-or-later WITH Classpath-exception-2.0"
            :url "https://www.eclipse.org/legal/epl-2.0/"}
  :dependencies [[org.clojure/clojure "1.11.0"]]
  :main ^:skip-aot aoc-2024.core
  :target-path "target/%s"
  :plugins [[dev.weavejester/lein-cljfmt "0.13.0"]]
  :profiles {:uberjar {:aot :all}})

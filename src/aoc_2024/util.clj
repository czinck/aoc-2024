(ns aoc-2024.util
  (:require [clojure.java.io]
            [clojure.edn :as edn]))

(defn slurp-lines
  [filename]
  (with-open [rdr (clojure.java.io/reader filename)]
    (vec (line-seq rdr))))

(defn parse-int
  [is]
  (edn/read-string is))

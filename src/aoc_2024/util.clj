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

(defn gcd
  [x y]
  (first (filter some? 
                 (for [i (range (max (abs x) (abs y)) 0 -1)]
                   (if (and (= (mod x i) 0) (= (mod y i) 0)) i nil)))))

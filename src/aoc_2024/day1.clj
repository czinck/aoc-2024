(ns aoc-2024.day1
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]))

(defn read-part1
  []
  (let [l (map #(str/split % #" +") (util/slurp-lines "resources/day1-1.txt"))]
    (vector (map util/parse-int (map first l)) (map util/parse-int (map second l)))))

(defn part1
  []
  (let [[l1 l2] (read-part1)]
    (reduce + (map #(abs (- %1 %2)) (sort l1) (sort l2)))))

(defn part2
  []
  (let [[l1 l2] (read-part1)
        f1 (frequencies l1)
        f2 (frequencies l2)]
    (reduce-kv (fn [init k v] (+ init (* k v (get f2 k 0)))) 0 f1)))



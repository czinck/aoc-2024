(ns aoc-2024.day2
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]))

(defn read-part1
  []
  (map (fn [l] (map util/parse-int l)) 
       (map #(str/split % #" +") (util/slurp-lines "resources/day2-1.txt"))))

(defn -pair-is-safe
  [l r oper]
  (and (oper l r)
       (< (abs (- l r)) 4)))

(defn -is-safe
  [l oper]
  (if (= (count l) 2) 
    (-pair-is-safe (first l) (second l) oper)
    (if (not (-pair-is-safe (first l) (second l) oper))
      false
      (recur (rest l) oper))))

(defn is-safe-increasing
  [l]
  (-is-safe l <))

(defn is-safe-decreasing
  [l]
  (-is-safe l >))

(defn is-safe 
  [l]
  (or (-is-safe l <) (-is-safe l >)))

(defn all-remove-reading
  [l]
  (map-indexed (fn [i sl] (concat (take i l) (nthrest l (inc i)))) l))

(defn part1
  []
  (count (filter is-safe (read-part1))))

(defn part2
  []
  (let [unsafe (remove is-safe (read-part1))]
    (+ (count (filter (fn [l] (some is-safe (all-remove-reading l))) unsafe))
       (part1))))

(ns aoc-2024.day7
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))


(defrecord Equation [tot values])

(defn parse-row
  [row]
  (let [[tot values] (str/split row #":")]
    (Equation. (util/parse-int tot) (map util/parse-int (str/split (str/trim values) #" ")))))

(defn load-data
  []
  (map parse-row (util/slurp-lines "resources/day7-1.txt")))

(defn permutations
  [length]
  (if (= length 1) '([2] [1] [0])
    (let [subperms (permutations (dec length))]
      (concat (map #(conj % 2) subperms) (map #(conj % 1) subperms) (map #(conj % 0) subperms)))))

(defn solve-equation
  [cur values operations]
  (if (= (count operations) 0) cur
    (case (first operations)
      2 (solve-equation (util/parse-int (str cur (first values))) (rest values) (rest operations))
      1 (solve-equation (* cur (first values)) (rest values) (rest operations))
      0 (solve-equation (+ cur (first values)) (rest values) (rest operations)))))

(defn equation-solvable
  [equation]
  (let [operations (permutations (dec (count (:values equation))))]
    (some #(= (:tot equation) (solve-equation (first (:values equation)) (rest (:values equation)) %)) operations)))

(defn part1
  []
  (let [equations (load-data)]
    (apply + (map :tot (filter equation-solvable equations)))))

(defn part2
  []
  )

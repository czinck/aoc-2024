(ns aoc-2024.day13
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defrecord Problem [x1 y1 x2 y2 x3 y3])

(defn button-presses
  [problem]
  ; x3 = ax1 + bx2
  ; y3 = ay1 + by2

  ; x2/y2 = (x3 - ax1) / (y3 - ay1)
  ; y3(x2/y2) - ay1(x2/y2) = x3 - ax1
  ; ax1 - ay1(x2/y2) = x3 - y3(x2/y2)
  ; a = (x3 - y3(x2/y2)) / (x1 - y1(x2/y2))
  ; b = (x3 - ax1) / x2
  (let [a (/ (- (:x3 problem) (* (:y3 problem) (/ (:x2 problem) (:y2 problem))))
             (- (:x1 problem) (* (:y1 problem) (/ (:x2 problem) (:y2 problem)))))
        b (/ (- (:x3 problem) (* a (:x1 problem))) (:x2 problem))]
    (if (and (integer? a) (pos? a) (integer? b) (pos? b)) [a b] [0 0])))

(defn get-values-from-line
  [re line]
  (let [matches (re-matches re line)]
    (map util/parse-int (rest matches))))

(defn parse-problem-lines
  [lines]
  ;Button A: X+11, Y+73
  ;Button B: X+95, Y+99
  ;Prize: X=6258, Y=10706
  (let [[x1 y1] (get-values-from-line #"Button A: X\+(\d+), Y\+(\d+)" (first lines))
        [x2 y2] (get-values-from-line #"Button B: X\+(\d+), Y\+(\d+)" (nth lines 1))
        [x3 y3] (get-values-from-line #"Prize: X=(\d+), Y=(\d+)" (nth lines 2))]
    (->Problem x1 y1 x2 y2 x3 y3)))

(defn load-data
  []
  (let [lines (util/slurp-lines "resources/day13-1.txt")]
    (map parse-problem-lines (partition-all 4 lines))))

(defn score
  [[a b]]
  (+ (* a 3) b))

(defn part1
  []
  (let [problems (load-data)]
    (apply + (map score (map button-presses problems)))))

(defn change-problem
  [problem]
  (->Problem (:x1 problem) (:y1 problem) (:x2 problem) (:y2 problem) (+ 10000000000000 (:x3 problem)) (+ 10000000000000 (:y3 problem))))


(defn part2
  []
  (let [problems (map change-problem (load-data))]
    (apply + (map score (map button-presses problems)))))

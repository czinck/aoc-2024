(ns aoc-2024.day4
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]))

(defn- up
  [y i]
  (- y i))

(defn- down
  [y i]
  (+ y i))

(defn- left
  [x i]
  (- x i))

(defn- right
  [x i]
  (+ x i))

(defn same
  [xy i]
  xy)

(defn- direction-vector
  [x oper]
  (for [i [0 1 2 3]]
    (oper x i)))

(defn- traverse
  [x y grid xoper yoper]
  (let [xv (direction-vector x xoper)
        yv (direction-vector y yoper)
        res (map (fn [xp yp] (nth (nth grid yp nil) xp nil)) xv yv)]
    (if (and (= (nth res 0) \X)
             (= (nth res 1) \M)
             (= (nth res 2) \A)
             (= (nth res 3) \S))
      1
      0)))

(defn count-matches
  [x y grid]
  (if (not= (nth (nth grid y) x) \X) 0
    (+ (traverse x y grid right same)
       (traverse x y grid left same)
       (traverse x y grid same up)
       (traverse x y grid same down)
       (traverse x y grid right up)
       (traverse x y grid right down)
       (traverse x y grid left up)
       (traverse x y grid left down))))

(defn part1
  []
  (let [grid (util/slurp-lines "resources/day4-1.txt")]
    (apply + (for [x (range (count (first grid)))
                   y (range (count grid))]
               (count-matches x y grid)))))

(defn- sam-diagonals
  [x y grid ]
  (let [left-down (nth (nth grid (inc y) nil) (dec x) nil)
        left-up (nth (nth grid (dec y) nil) (dec x) nil)
        right-up (nth (nth grid (dec y) nil) (inc x) nil)
        right-down (nth (nth grid (inc y) nil) (inc x) nil)]
    (and (or (and (= left-down \S) (= right-up \M))
             (and (= left-down \M) (= right-up \S)))
         (or (and (= left-up \S) (= right-down \M))
             (and (= left-up \M) (= right-down \S)))
         (= (nth (nth grid y) x) \A))))



(defn cross?
  [x y grid]
  (and (= (nth (nth grid y) x) \A) (sam-diagonals x y grid)))


(defn part2
  []
  (let [grid (util/slurp-lines "resources/day4-1.txt")]
    (count (filter true? (for [x (range (count (first grid)))
                               y (range (count grid))]
                           (cross? x y grid))))))

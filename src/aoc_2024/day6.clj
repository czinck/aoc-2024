(ns aoc-2024.day6
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defrecord Guard [x y direction])
(defrecord Obstruction [x y])

(defn guard-position
  [initial-grid]
  (first (first (filter not-empty (keep-indexed (fn [y row] (keep-indexed (fn [x cell] 
                                            (case cell
                                              \^ (Guard. x y :up)
                                              \> (Guard. x y :right)
                                              \v (Guard. x y :down)
                                              \< (Guard. x y :left)
                                              nil))
                                          row))
                initial-grid)))))


(defn- next-x
  [guard]
  (case (:direction guard)
    :up (:x guard)
    :right (inc (:x guard))
    :down (:x guard)
    :left (dec (:x guard))))

(defn- next-y
  [guard]
  (case (:direction guard)
    :up (dec (:y guard))
    :right (:y guard)
    :down (inc (:y guard))
    :left (:y guard)))

(defn tick-guard
  ([grid guard]
  (let [x (:x guard)
        y (:y guard)
        xn (next-x guard)
        yn (next-y guard)]
    (if (= (nth (nth grid yn nil) xn) \#)
      (Guard. x y (case (:direction guard)
                    :up :right
                    :right :down
                    :down :left
                    :left :up))
      (Guard. xn yn (:direction guard)))))

  ([grid guard new-obstruction]
  (let [x (:x guard)
        y (:y guard)
        xn (next-x guard)
        yn (next-y guard)]
    (if (or (= (nth (nth grid yn nil) xn) \#) (and (= xn (:x new-obstruction)) (= yn (:y new-obstruction))))
      (Guard. x y (case (:direction guard)
                    :up :right
                    :right :down
                    :down :left
                    :left :up))
      (Guard. xn yn (:direction guard))))))

(defn guard-in-grid?
  [grid guard]
  (let [x (:x guard)
        y (:y guard)
        x-max (count (first grid))
        y-max (count grid)]
    (and (>= x 0) (>= y 0) (< x x-max) (< y y-max))))

(defn unique-positions
  [guard-positions]
  (reduce (fn [s g] (conj s (list (:x g) (:y g)))) #{} guard-positions))


(defn walk-repeats
  [grid guard new-obstruction]
  (reduce (fn [positions new-guard] 
            (cond (contains? positions new-guard) (reduced true)
                  (not (guard-in-grid? grid guard)) (reduced false)
                  :default (conj positions new-guard)))
            (iterate (fn [g] tick-guard grid g new-obstruction) guard)))
  
(defn part1
  []
  (let [initial (map seq (util/slurp-lines "resources/day6-1.txt"))
        guard (guard-position initial)
        x-max (count (first initial))
        y-max (count initial)]
    (count (unique-positions (take-while (fn [g] (guard-in-grid? initial g)) (iterate (fn [g] (tick-guard initial g)) guard))))))

(defn part2
  []
  (let [initial (map seq (util/slurp-lines "resources/day6-sample.txt"))
        guard (guard-position initial)]
    (count (filter true? (for [y (range (count initial))
          x (range (count (first initial)))
          :when (= (nth (nth initial y) x) \.)]
      (walk-repeats initial guard (Obstruction. x y)))))))

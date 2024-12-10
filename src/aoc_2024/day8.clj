(ns aoc-2024.day8
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defrecord Point [x y])

(defn append-update
  [v [x y]]
  (if (nil? v) [(->Point x y)] (conj v (->Point x y))))

(defn antinodes-for-antena-pair
  [a1 a2]
  (let [delta-x (- (:x a1) (:x a2))
        delta-y (- (:y a1) (:y a2))]
    [(->Point (+ (:x a1) delta-x) (+ (:y a1) delta-y)) (->Point (- (:x a2) delta-x) (- (:y a2) delta-y))]))

(defn antinodes-for-antenas
  [antenas]
  (apply clojure.set/union 
         (for [i (range (count antenas))
               j (range (inc i) (count antenas))]
           (antinodes-for-antena-pair (nth antenas i) (nth antenas j)))))

(defn- line-generator
  [point reduced-x reduced-y direction]
  (map (fn [i] (->Point (direction (:x point) (* i reduced-x)) (direction (:y point) (* i reduced-y)))) (range)))

(defn point-in-grid?
  [point max-x max-y]
  (and (>= (:x point) 0) (>= (:y point) 0) (< (:x point) max-x) (< (:y point) max-y)))
  
(defn antinodes
  [antenas-map]
  (reduce (fn [antinodes antenas] (clojure.set/union antinodes (antinodes-for-antenas antenas))) #{} (vals antenas-map)))

(defn load-antenas
  []
  (let [lines (util/slurp-lines "resources/day8-1.txt")
        max-y (count lines)
        max-x (count (first lines))
        antenas-raw (apply concat (map-indexed (fn [y row] (map-indexed (fn [x c] (if (= c \.) nil [c x y])) row)) lines))
        antenas-map (reduce (fn [m a] (if (nil? a) m (update m (first a) append-update (rest a)))) {} antenas-raw)
        antinodes-unfiltered (antinodes antenas-map)]
    (count (set (filter (fn [antinode] (point-in-grid? antinode max-x max-y)) antinodes-unfiltered)))))

(defn part1
  []
  (load-antenas))

(defn line-points-for-antenas
  [a1 a2 max-x max-y]
  (let [delta-x (- (:x a1) (:x a2))
        delta-y (- (:y a1) (:y a2))
        gcd (util/gcd delta-x delta-y)
        step-x (/ delta-x gcd)
        step-y (/ delta-y gcd)]
    (set (concat (take-while (fn [p] (point-in-grid? p max-x max-y)) (line-generator a1 step-x step-y +)) (take-while (fn [p] (point-in-grid? p max-x max-y)) (line-generator a1 step-x step-y -))))))


(defn antinodes-lines-for-antenas
  [antenas max-x max-y]
  (apply clojure.set/union 
         (for [i (range (count antenas))
               j (range (inc i) (count antenas))]
           (line-points-for-antenas (nth antenas i) (nth antenas j) max-x max-y))))

(defn antinodes-lines 
  [antenas-map max-x max-y]
  (reduce (fn [antinodes antenas] (clojure.set/union antinodes (antinodes-lines-for-antenas antenas max-x max-y))) #{} (vals antenas-map)))

(defn part2
  []
  (let [lines (util/slurp-lines "resources/day8-1.txt")
        max-y (count lines)
        max-x (count (first lines))
        antenas-raw (apply concat (map-indexed (fn [y row] (map-indexed (fn [x c] (if (= c \.) nil [c x y])) row)) lines))
        antenas-map (reduce (fn [m a] (if (nil? a) m (update m (first a) append-update (rest a)))) {} antenas-raw)]
    (count (antinodes-lines antenas-map max-x max-y))))

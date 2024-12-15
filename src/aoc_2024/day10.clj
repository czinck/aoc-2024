(ns aoc-2024.day10
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn load-topo 
  []
  (mapv #(mapv util/parse-int (map str %)) (util/slurp-lines "resources/day10-1.txt")))

(defn peaks-from-trailhead
  [x y topo]
  (let [curr (nth (nth topo y) x)
        max-x (dec (count (first topo)))
        max-y (dec (count topo))]
    (if (= curr 9) #{(list x y)}
      (clojure.set/union (if (and (> y 0) (= (nth (nth topo (dec y)) x) (inc curr))) (peaks-from-trailhead x (dec y) topo) #{})
        (if (and (> x 0) (= (nth (nth topo y) (dec x)) (inc curr))) (peaks-from-trailhead (dec x) y topo) #{})
        (if (and (< y max-y) (= (nth (nth topo (inc y)) x) (inc curr))) (peaks-from-trailhead x (inc y) topo) #{})
        (if (and (< x max-x) (= (nth (nth topo y) (inc x)) (inc curr))) (peaks-from-trailhead (inc x) y topo) #{})))))

(defn part1
  []
  (let [topo (load-topo)
        peaks-for-trailheads (map-indexed (fn [y row] (map-indexed (fn [x curr] (if (= curr 0) (peaks-from-trailhead x y topo) #{})) row)) topo)]
    (apply + (map (fn [row] (apply + (map count row))) peaks-for-trailheads))))

(defn paths-from-trailhead
  [x y topo]
  (let [curr (nth (nth topo y) x)
        max-x (dec (count (first topo)))
        max-y (dec (count topo))]
    (if (= curr 9) 1
      (+ (if (and (> y 0) (= (nth (nth topo (dec y)) x) (inc curr))) (paths-from-trailhead x (dec y) topo) 0)
        (if (and (> x 0) (= (nth (nth topo y) (dec x)) (inc curr))) (paths-from-trailhead (dec x) y topo) 0)
        (if (and (< y max-y) (= (nth (nth topo (inc y)) x) (inc curr))) (paths-from-trailhead x (inc y) topo) 0)
        (if (and (< x max-x) (= (nth (nth topo y) (inc x)) (inc curr))) (paths-from-trailhead (inc x) y topo) 0)))))

(defn part2
  []
  (let [topo (load-topo)]
    (apply + (map-indexed (fn [y row] (apply + (map-indexed (fn [x curr] (if (= curr 0) (paths-from-trailhead x y topo) 0)) row))) topo))))

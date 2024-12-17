(ns aoc-2024.day12
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn load-data
  []
  (mapv vec (util/slurp-lines "resources/day12-1.txt")))


(defn get-region
  [x y grid traversed]
  (let [curr (nth (nth grid y) x)
        updated (clojure.set/union traversed #{[x y]})
        max-x (dec (count (first grid)))
        max-y (dec (count grid))]
    (if (contains? traversed [x y]) updated
      (let [left-traversed (if (and (> x 0) (= (nth (nth grid y) (dec x)) curr)) (get-region (dec x) y grid updated) updated)
            up-traversed (if (and (> y 0) (= (nth (nth grid (dec y)) x) curr)) (get-region x (dec y) grid left-traversed) left-traversed)
            right-traversed (if (and (< x max-x) (= (nth (nth grid y) (inc x)) curr)) (get-region (inc x) y grid up-traversed) up-traversed)
            down-traversed (if (and (< y max-y) (= (nth (nth grid (inc y)) x) curr)) (get-region x (inc y) grid right-traversed) right-traversed)]
        down-traversed))))


(defn get-regions
  [grid]
  (reduce (fn [plots [x y]]
            (if (some (fn [plot] (contains? plot [x y])) plots) plots (conj plots (get-region x y grid #{})))) 
          (list)
          (for [x (range (count (first grid)))
                y (range (count grid))] [x y])))

(defn area
  [plot]
  (count plot))

(defn perimeter
  [plot]
  (apply + (map (fn [[x y]] (+ 
                              (if (contains? plot [(dec x) y]) 0 1)
                              (if (contains? plot [(inc x) y]) 0 1)
                              (if (contains? plot [x (dec y)]) 0 1)
                              (if (contains? plot [x (inc y)]) 0 1))) plot)))

(defn part1
  []
  (let [regions (get-regions (load-data))]
    (apply + (map (fn [plot] (* (perimeter plot) (area plot))) regions))))

(defn part2
  []
  )

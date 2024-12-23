(ns aoc-2024.day14
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defrecord RobotState [x y vx vy])

(defn parse-line
  [line]
  (let [match (re-matches #"p=(-?\d+),(-?\d+) v=(-?\d+),(-?\d+)" line)]
    (apply ->RobotState (map util/parse-int (rest match)))))

(defn load-data
  []
  (map parse-line (util/slurp-lines "resources/day14-1.txt")))

(defn n-ticks
  [n state max-x max-y]
  (let [new-x (mod (+ (* (:vx state) n) (:x state)) (inc max-x))
        new-y (mod (+ (* (:vy state) n) (:y state)) (inc max-y))]
    (->RobotState new-x new-y (:vx state) (:vy state))))

(defn states-for-region
  [states min-x max-x min-y max-y]
  (filter (fn [state] (and 
                        (>= (:x state) min-x)
                        (>= (:y state) min-y)
                        (<= (:x state) max-x)
                        (<= (:y state) max-y)))
          states))

(defn part1
  []
  (let [states (load-data)
        new-states (map (fn [state] (n-ticks 100 state 100 102)) states)]
    (* (count (states-for-region new-states 0 49 0 50))
       (count (states-for-region new-states 0 49 52 103))
       (count (states-for-region new-states 51 101 0 50))
       (count (states-for-region new-states 51 101 52 103)))))

(defn part2
  []
  )

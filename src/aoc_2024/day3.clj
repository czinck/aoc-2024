(ns aoc-2024.day3
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]))


(defn part1
  []
  (let [commands (re-seq #"mul\((\d{1,3}),(\d{1,3})\)" (slurp "resources/day3-1.txt"))]
    (reduce (fn [s command] (+ s 
                               (* (util/parse-int (nth command 1)) 
                                  (util/parse-int (nth command 2))))) 
            0
            commands)))


(defn enabled-reducer
  [sum commands enabled]
  (cond
    (empty? commands) sum
    (str/starts-with? (first (first commands)) "don't") (recur sum (rest commands) false)
    (str/starts-with? (first (first commands)) "do") (recur sum (rest commands) true)
    (true? enabled) (recur (+ sum (* 
                                    (util/parse-int (nth (first commands) 1))
                                    (util/parse-int (nth (first commands) 2))))
                           (rest commands) 
                           enabled)
    :else (recur sum (rest commands) enabled)))


(defn part2
  []
  (let [commands (re-seq #"do\(\)|don't\(\)|mul\((\d{1,3}),(\d{1,3})\)" (slurp "resources/day3-1.txt"))]
    (enabled-reducer 0 commands true)))

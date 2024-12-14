(ns aoc-2024.core
  (:require [aoc-2024.day1 :as day1]
            [aoc-2024.day2 :as day2]
            [aoc-2024.day3 :as day3]
            [aoc-2024.day4 :as day4]
            [aoc-2024.day5 :as day5]
            [aoc-2024.day6 :as day6]
            [aoc-2024.day7 :as day7]
            [aoc-2024.day8 :as day8]
            [aoc-2024.day9 :as day9])
  (:gen-class))

(defn -main
  [& args]
  (println (time (day9/part1)))
  (println (time (day9/part2))))

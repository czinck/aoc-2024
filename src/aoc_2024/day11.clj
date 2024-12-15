(ns aoc-2024.day11
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn load-data
  []
  (str/split (str/trim (slurp "resources/day11-1.txt")) #" "))

(defn change-stone
  [stone]
  (let [digits (count stone)]
    (cond (= stone "0") ["1"]
          (zero? (mod digits 2)) [(str (parse-long (subs stone 0 (/ digits 2)))) (str (parse-long (subs stone (/ digits 2))))]
          :default [(str (* 2024 (parse-long stone)))])))

(defn tick
  [stones]
  (mapcat change-stone stones))

(defn part1
  []
  (let [initial-stones (load-data)]
    (count (reduce (fn [stones _] (tick stones)) initial-stones (range 25)))))


(defn part2 
  []
  (let [initial-stones (load-data)]
    (count (reduce (fn [stones _] (tick stones)) initial-stones (range 75)))))

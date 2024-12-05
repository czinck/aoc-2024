(ns aoc-2024.day5
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn- split-rule-line
  [l]
  (map util/parse-int (str/split l #"\|")))

(defn rule-map-reducer
  [existing rule-line]
  (let [[before after] rule-line]
    (update existing after (fn [old] (if (nil? old) [before] (conj old before))))))


(defn build-rules
  []
  (let [rules-lines (take-while not-empty (util/slurp-lines "resources/day5-1.txt"))]
    (reduce rule-map-reducer {} (map split-rule-line rules-lines))))

(defn- valid-pages?
  [pages rules]
  (cond (empty? pages) true
        (not (contains? rules (first pages))) (recur (rest pages) rules)
        (empty? (clojure.set/intersection (set (rest pages)) (set (get rules (first pages))))) (recur (rest pages) rules)
        :else false))


(defn get-pages
  []
  (map (fn [l] (map util/parse-int (str/split l #","))) (take-while not-empty (reverse (util/slurp-lines "resources/day5-1.txt")))))
  

(defn- middle-page
  [pages]
  (nth pages (int (/ (count pages) 2))))

(defn part1
  []
  (let [rules (build-rules)
        pages (get-pages)
        valid-pages (filter (fn [p] (valid-pages? p rules)) pages)]

    (apply + (map middle-page valid-pages))))


(defn part2
  []
  )

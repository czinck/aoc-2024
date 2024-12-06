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
    (update existing after (fn [old] (if (nil? old) #{before} (conj old before))))))


(defn build-rules
  []
  (let [rules-lines (take-while not-empty (util/slurp-lines "resources/day5-1.txt"))]
    (reduce rule-map-reducer {} (map split-rule-line rules-lines))))

(defn find-conflicting-indexes
  [pages rules]
  (first (filter some? 
    (for [i (range (dec (count pages)))
          j (range (dec (count pages)) i -1)]
      (if (contains? (get rules (nth pages i)) (nth pages j)) [i j] nil)))))


(defn- valid-pages?
  [pages rules]
  (nil? (find-conflicting-indexes pages rules)))

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

(defn- move-after-fn
  [v to-move after]
  (fn [index item]
    (cond (< index to-move) (nth v index)
          (= index to-move) (nth v (inc index))
          (and (>= index to-move) (< index after)) (nth v (inc index))
          (= index after) (nth v to-move)
          :else (nth v index))))

(defn move-after
  [v to-move after]
  (keep-indexed (move-after-fn v to-move after) v))

(defn fix-pages
  [pages rules]
  (let [conflicting-indexes (find-conflicting-indexes pages rules)]
    (if (nil? conflicting-indexes) pages
      (recur (move-after pages (first conflicting-indexes) (second conflicting-indexes)) rules))))


(defn part2
  []
  (let [rules (build-rules)
        pages (get-pages)
        invalid-pages (remove (fn [p] (valid-pages? p rules)) pages)
        fixed-pages (map (fn [p] (fix-pages p rules)) invalid-pages)]
    (apply + (map middle-page fixed-pages))))

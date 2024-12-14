(ns aoc-2024.day9
  (:require [aoc-2024.util :as util]
            [clojure.string :as str]
            [clojure.set]))

(defn load-data
  []
  (map util/parse-int (map str (str/trim (slurp "resources/day9-1.txt")))))

(defn expand-disk-map
  [disk-map]
  (vec (flatten (map-indexed (fn [i c] (if (= (mod i 2) 0) (repeat c (/ i 2)) (repeat c nil))) disk-map))))

(defn shift-checksum
  [expanded-map]
  (loop [i 0
         j (dec (count expanded-map))
         sum 0]
    (cond (= i j) (+ sum (* i (nth expanded-map i)))
          (> i j) sum
          (and (nil? (nth expanded-map i)) (nil? (nth expanded-map j))) (recur i (dec j) sum)
          (nil? (nth expanded-map i)) (recur (inc i) (dec j) (+ sum (* i (nth expanded-map j))))
          :default (recur (inc i) j (+ sum (* i (nth expanded-map i)))))))

(defn part1
  []
  (shift-checksum (expand-disk-map (load-data))))


(defn block-length
  [expanded-map start-index]
  (+ 1 (if (and (< (inc start-index) (count expanded-map))
                (= (nth expanded-map start-index) (nth expanded-map (inc start-index))))
         (block-length expanded-map (inc start-index)) 0)))

(defn swap-locations
  [expanded-map swap-to swap-from length]
  (apply assoc (apply assoc expanded-map
                      (interleave (range swap-to (+ swap-to length))
                                  (repeat length (nth expanded-map swap-from))))
         (interleave (range swap-from (+ swap-from length))
                     (repeat length (nth expanded-map swap-to)))))

(defn trim-trailing-nils
  [expanded-map]
  (loop [i (dec (count expanded-map))]
    (if (nil? (nth expanded-map i)) (recur (dec i)) (subvec expanded-map 0 (inc i)))))

(defn move-file
  [expanded-map file-start-index]
  (let [file-block-length (block-length expanded-map file-start-index)]
    (loop [i 0]
      (cond (>= i file-start-index) expanded-map
        (and (nil? (nth expanded-map i)) (>= (block-length expanded-map i) file-block-length)) (trim-trailing-nils (swap-locations expanded-map i file-start-index file-block-length))
        :default (recur (inc i))))))


(defn move-files
  [expanded-map]
  (loop [i (dec (count expanded-map))
         curr-map expanded-map]
    (cond (zero? i) curr-map
          (>= i (count curr-map)) (recur (dec (count curr-map)) curr-map)
          (= (nth curr-map i) (nth curr-map (dec i))) (recur (dec i) curr-map)
          :default (recur (dec i) (move-file curr-map i)))))

(defn checksum
  [expanded-map]
  (reduce + (map-indexed (fn [i c] (* i (if (nil? c) 0 c))) expanded-map)))

(defn part2
  []
  (let [expanded-map (expand-disk-map (load-data))]
    (checksum (move-files expanded-map))))

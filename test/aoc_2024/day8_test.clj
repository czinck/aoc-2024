(ns aoc-2024.day8-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day8 :refer :all]
            [clojure.set]))

(deftest get-antinodes-test
  (testing "gets antinodes properly, no worry about bounds"
    (is (= #{(->Point 1 1) (->Point 4 4)} (set (antinodes-for-antena-pair (->Point 2 2) (->Point 3 3)))))
    (is (= #{(->Point 1 1) (->Point 4 4)} (set (antinodes-for-antena-pair (->Point 3 3) (->Point 2 2)))))
    (is (= #{(->Point 4 2) (->Point 10 11)} (set (antinodes-for-antena-pair (->Point 6 5) (->Point 8 8)))))))

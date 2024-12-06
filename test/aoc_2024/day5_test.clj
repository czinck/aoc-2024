(ns aoc-2024.day5-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day5 :refer :all]))

(deftest move-after-test
  (testing "move-after"
    (is (= (move-after [1 2 3 4 5 6 7 8] 0 5) '(2 3 4 5 6 1 7 8)))
    (is (= (move-after [1 2 3 4 5 6 7 8] 0 7) '(2 3 4 5 6 7 8 1)))
    (is (= (move-after [1 2 3 4 5 6 7 8] 1 5) '(1 3 4 5 6 2 7 8)))))

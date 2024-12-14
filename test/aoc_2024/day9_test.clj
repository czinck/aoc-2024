(ns aoc-2024.day9-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day9 :refer :all]))

(deftest expand-map-test
  (testing "expands disk map"
    (is (= (expand-disk-map '(1 2 3 4 5)) '(0 nil nil 1 1 1 nil nil nil nil 2 2 2 2 2)))
    (is (= (expand-disk-map '(2 3 3 3 1 3 3 1 2 1 4 1 4 1 3 1 4 0 2 )) '(0 0 nil nil nil 1 1 1 nil nil nil 2 nil nil nil 3 3 3 nil 4 4 nil 5 5 5 5 nil 6 6 6 6 nil 7 7 7 nil 8 8 8 8 9 9 )))))

(deftest shift-checksum-test
  (testing "generates checksum"
    (is (= 1928 (shift-checksum [0 0 nil nil nil 1 1 1 nil nil nil 2 nil nil nil 3 3 3 nil 4 4 nil 5 5 5 5 nil 6 6 6 6 nil 7 7 7 nil 8 8 8 8 9 9 ])))))

(deftest block-length-test
  (testing "returns correct block length"
    (is (= 3 (block-length [1 1 2 2 2 3 4 4] 2)))
    (is (= 2 (block-length [1 1 2 2 2 3 4 4] 6)))
    (is (= 1 (block-length [1 1 2 2 2 3 4 4] 5)))
    (is (= 2 (block-length [1 1 2 2 2 3 nil nil] 6)))))

(deftest swap-locations-test
  (testing "swaps locations"
    (is (= [1 1 3 3 3 2 2 2 4] (swap-locations [1 1 2 2 2 3 3 3 4] 2 5 3)))))

(deftest trim-nils-test
  (testing "trims trailing nils"
    (is (= [1 2 3 4 5] (trim-trailing-nils [1 2 3 4 5])))
    (is (= [nil 2 3 4 5] (trim-trailing-nils [nil 2 3 4 5])))
    (is (= [1 2 3 4] (trim-trailing-nils [1 2 3 4 nil])))
    (is (= [1 2 3 nil 5] (trim-trailing-nils [1 2 3 nil 5])))
    (is (= [1 2 3] (trim-trailing-nils [1 2 3 nil nil])))))


(deftest test-checksum
  (testing "basic checksum math"
    (is (= (checksum [0 0 9 9 8 1 1 1 8 8 8 2 7 7 7 3 3 3 6 4 4 6 5 5 5 5 6 6 nil nil nil nil nil nil nil nil nil nil nil nil nil nil]) 1928))))


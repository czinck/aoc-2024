(ns aoc-2024.day7-test
  (:require [clojure.test :refer :all]
            [aoc-2024.day7 :refer :all]
            [clojure.set]))

(deftest move-after-test
  (testing "parsing of rows"
    (is (= (parse-row "54753537: 2 35 2 5 5 9 1 17 367 73") (->Equation 54753537 '(2 35 2 5 5 9 1 17 367 73))))))

(deftest permutations-test
  (testing "permutation creation"
    (is (= (set (permutations 1)) #{'(2) '(1) '(0)}))
    (is (= (set (permutations 2)) #{'(2 2) '(2 1) '(2 0) '(1 2) '(0 2) '(1 1) '(1 0) '(0 1) '(0 0)}))
    (is (clojure.set/superset? (set (permutations 3)) #{'(1 1 1) '(1 1 0) '(1 0 1) '(1 0 0) '(0 1 1) '(0 1 0) '(0 0 1) '(0 0 0)}))))

(deftest test-equation-solver
  (testing "equation solver does right operations"
    (is (= 9 (solve-equation 3 '(3) '(1))))
    (is (= 6 (solve-equation 3 '(3) '(0))))
    (is (= 27 (solve-equation 3 '(3 3) '(1 1))))
    (is (= 12 (solve-equation 3 '(3 3) '(1 0))))))

(deftest test-equation-solvable
  (testing "if the equation solvable checker works"
    (is (true? (equation-solvable (parse-row "190: 10 19"))))
    (is (true? (equation-solvable (parse-row "3267: 81 40 27"))))
    (is (nil? (equation-solvable (parse-row "83: 17 5"))))
    (is (true? (equation-solvable (parse-row "156: 15 6"))))
    (is (true? (equation-solvable (parse-row "7290: 6 8 6 15"))))
    (is (nil? (equation-solvable (parse-row "161011: 16 10 13"))))
    (is (true? (equation-solvable (parse-row "192: 17 8 14"))))
    (is (nil? (equation-solvable (parse-row "21037: 9 7 18 13"))))
    (is (true? (equation-solvable (parse-row "292: 11 6 16 20"))))))

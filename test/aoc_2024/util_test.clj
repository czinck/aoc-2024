(ns aoc-2024.util-test
  (:require [clojure.test :refer :all]
            [aoc-2024.util :refer :all]))

(deftest gcd-test
  (testing "does gcd calculation properly"
    (is (= 1 (gcd 20 1)))
    (is (= 1 (gcd 20 3)))
    (is (= 3 (gcd 21 3)))
    (is (= 3 (gcd -21 3)))
    (is (= 3 (gcd -21 -3)))
    (is (= 7 (gcd 21 14)))))

(ns wordle.core-test
  (:require [clojure.test :refer :all]
            [wordle.core :refer :all]))

(deftest test-compare-target-to-guess
  (testing "test compare-target-to-guess"
    (testing "3 green 2 black"
      (is (=
           '([:g \a] [:g \b] [:g \a] [:b \s] [:b \e])
           (compare-target-to-guess "aback" "abase"))))
    (testing "correct guess"
      (is (=
           '([:g \a] [:g \b] [:g \a] [:g \c] [:g \k])
           (compare-target-to-guess "aback" "aback"))))
    (testing "some green, yellow, black"
      (is (=
           '([:y \b] [:b \l] [:g \a] [:g \c] [:g \k])
           (compare-target-to-guess "aback" "black"))))))

(deftest test-eligible-by-letter?
  (testing "test eligible-by-letter?"
    (testing "eligible"
      (is (=
           '(true true true true true)
           (eligible-by-letter?
            "aback"
            '([:g \a] [:g \b] [:g \a] [:b \s] [:b \e])))))
    (testing "ineligible"
      (is (=
           '(false false false true true)
           (eligible-by-letter?
            "witch"
            '([:g \a] [:g \b] [:g \a] [:b \s] [:b \e])))))))

(deftest test-eligible-single-feedback?
  (testing "test eligible-by-letter?"
    (testing "eligible"
      (is (=
           true
           (eligible-single-feedback?
            "aback"
            '([:g \a] [:g \b] [:g \a] [:b \s] [:b \e])))))
    (testing "ineligible"
      (is (=
           false
           (eligible-single-feedback?
            "witch"
            '([:g \a] [:g \b] [:g \a] [:b \s] [:b \e])))))))

(deftest test-eligible-words
  (testing "test eligible-words"
    (testing ""
      (is (=
           '("aback" "black" "block" "clack")
           (eligible-words
            '("aback" "black" "block" "bread" "clack" "forum")
            ['([:b \g] [:b \r] [:b \e] [:b \e] [:b \n])
             '([:b \w] [:b \i] [:b \t] [:g \c] [:b \h])]))))))

(deftest test-attempt-guess
  (testing "test attempt-guess"
    (testing "1 guess"
      (is (= 1 (attempt-guess "aback" "aback"))))
    (testing "2 guesses"
      (is (= 2 (attempt-guess "aback" "abase"))))))

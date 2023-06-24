(ns wordle.core
  (:require
     [wordle.information :as info]
     [clojure.string]))
;; (load-file "information.clj")

(def word-length 5)

(defn main [& args])


(defn generate-guess [feedbacks]
  ())

(defn assert-word-length [word]
  (assert (= (count word) word-length)
          (str "Word must have length of " word-length)))

(def possible-words
  (-> (slurp "data/possible_words.txt")
      (clojure.string/split-lines)))
      ;; ((fn [w]
      ;;   (zipmap w (repeat (count w) (/ 1 (count w))))))))

(defn compare-target-to-guess [target guess]
  "Feedback for guess"
  (assert-word-length guess)
  (map #(let [current-letter (nth guess %)]
          (cond 
            (= (nth target %) current-letter) [:g current-letter]
            (some (fn [x] (= current-letter x)) target) [:y current-letter]
            :else [:b current-letter]))
       (range (count target))))

(defn eligible-by-letter? [word feedback]
  "Returns eligibility for each letter in feedback"
  (map (fn [w p]
         (let [present (some (fn [x] (= (nth p 1) x)) word)]
           (case (first p)
             :g (= (nth p 1) w)
             :y (not (= (nth p 1) w))
             :b (not present))))
       word
       feedback))

(defn eligible? [word feedback]
  "Returns eligibility of word"
  (every? true? (eligible-by-letter? word feedback)))

(def answer "aback")
(first possible-words)
(def guess "abase")
(compare-target-to-guess answer "abase")

; search for eligible words
(defn eligible-words [words feedback]
  "Filter out ineligible words according to the feedback"
  (filter #(eligible? % feedback) words))

(eligible-words possible-words (compare-target-to-guess answer guess))
; read file
; generate guess by calculating max information
; guess
; get feedback (modularity for input)
; generate guess by calculating max information
; continue until solved


;; max information:
;; max probablility under entropy graph across all possible guesses
;; sigmoid is for incorporating letter frequency
;; least number of all greys?


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

(def allowed-words
  (-> (slurp "data/allowed_words.txt")
      (clojure.string/split-lines)))

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

(defn eligible-single-feedback? [word feedback]
  (every? true?
          (eligible-by-letter? word feedback)))

(defn eligible? [word feedbacks]
  "Returns eligibility of word"
  (every? true?
    (map (partial eligible-single-feedback? word) feedbacks)))

; search for eligible words
(defn eligible-words [words feedbacks]
  "Filter out ineligible words according to the feedback"
  (filter #(eligible? % feedbacks) words))

(defn choose-word-random [pw]
  (rand-nth pw))

(def answer "aback")
(first possible-words)
(def guess "green")
(compare-target-to-guess answer "abase")

(eligible-words possible-words [(compare-target-to-guess answer guess) (compare-target-to-guess answer "witch")])

(defn attempt-guess [answer initial-guess]
  (loop [n 1 guess initial-guess feedbacks '()]
    (let [feedback (compare-target-to-guess answer guess)
          feedbacks (conj feedbacks feedback)]
      (if (every? #(= :g (first %)) feedback)
        n
        (recur (+ n 1) (choose-word-random (eligible-words possible-words feedbacks)) feedbacks)))))

(attempt-guess "aback" "aback")
(attempt-guess "aback" "witch")
(attempt-guess "aback" "saber")

(defn average-no-of-guesses [answers possible-words]

  )


; read file
; generate guess by calculating max information
; guess
; get feedback (modularity for input)
; generate guess by calculating max information
; continue until solved




(ns wordle.core
  (:require
     [wordle.information :as info]
     [clojure.string]))

(def word-length 5)
(def initial-guess "salet")
(def n-trials 1000)


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

(defn compare-target-to-guess
  "Feedback for guess"
  [target guess]
  (assert-word-length guess)
  (map #(let [current-letter (nth guess %)]
          (cond
            (= (nth target %) current-letter) [:g current-letter]
            (some (fn [x] (= current-letter x)) target) [:y current-letter]
            :else [:b current-letter]))
       (range (count target))))

(defn eligible-by-letter?
  "Returns eligibility for each letter in feedback"
  [word feedback]
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

(defn eligible?
  "Returns eligibility of word"
  [word feedbacks]
  (every? true?
          (map (partial eligible-single-feedback? word) feedbacks)))

; search for eligible words
(defn eligible-words
  "Filter out ineligible words according to the feedback"
  [words feedbacks]
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

(compare-target-to-guess "aback" "black")
(attempt-guess "aback" "aback")

(defn take-rand [coll n]
  (take n (repeatedly #(rand-nth coll))))

(defn average [coll]
  (float (/ (reduce + coll) (count coll))))

(defn average-no-of-guesses [answers n]
  ; pmap about 5 times as fast as map
  (-> (pmap attempt-guess (take-rand answers n) (repeat initial-guess))
      (average)))

(defn -main [& args]
  (let [n (average-no-of-guesses possible-words 1000)]
    (println "Average no of guesses over" n-trials "runs:" n)))


(ns wordle.core
  (:require
     [wordle.information :as info]
     [clojure.string]))
;; (load-file "information.clj")

(defn main [& args])


(defn solve [guess-feedback-map]
  ())

(def possible-words
  (-> (slurp "data/possible_words.txt")
      (clojure.string/split-lines)
      ((fn [w]
        (zipmap w (repeat (count w) (/ 1 (count w))))))))

(defn compare-target-to-guess [target guess]
  "Feedback for guess"
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

(def answer "water")
(first possible-words)
(info/entropy possible-words)
(compare-target-to-guess answer "wretch")


; read file
; generate guess by calculating max information
; guess
; get feedback (modularity for input)
; generate guess by calculating max information
; continue until solved

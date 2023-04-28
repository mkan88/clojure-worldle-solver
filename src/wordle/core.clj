(ns wordle.core
  (:require [wordle.information :as info]))

(defn main [& args])


(defn solve [guess-feedback-map]
  ())

(def possible-words
  (-> (clojure.string/split-lines (slurp "data/possible_words.txt"))
      (fn [w]
         (zipmap w (repeat (count w) (/ 1 (count w)))))))

(info/entropy possible-words)


; read file
; generate guess by calculating max information
; guess
; get feedback (modularity for input)
; generate guess by calculating max information
; continue until solved
(ns wordle.information
  (:require [clojure.math :as math]))

(defn entropy [probs]
  "Calculates total entropy/information"
  (reduce + (map
             #(* % (Math/log (/ 1 %)))
             (vals probs))))

(defn sigmoid [x]
  "Sigmoid function"
  (/ 1 (+ 1 (math/exp (- x)))))

(defn update-all
  "Updates all values in hash-map"
  [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn sigmoid-weighted-prob [probs]
  "Apply sigmoid function to probs"
  (update-all probs sigmoid))


; search for eligible words
(defn eligible-words [feedback words]
  "Filter out ineligible words according to the feedback"
  (filter (partial eligible? feedback) words))


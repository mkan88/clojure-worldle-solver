(ns information
  (:require [clojure.math :as math]))

(defn entropy [probs]
  (reduce + (map
             #(* % (Math/log (/ 1 %)))
             (vals probs))))

(defn sigmoid [x]
  (/ 1 (+ 1 (math/exp (- x)))))

(defn update-all
  "Updates all values in hash-map"
  [m f & args]
  (reduce (fn [r [k v]] (assoc r k (apply f v args))) {} m))

(defn sigmoid-weighted-prob [probs]
  (update-all probs sigmoid))

(defn compare-target-to-guess [target guess]
  (map #(let [current-letter (nth guess %)]
          (cond
            (= (nth target %) current-letter) [:g current-letter]
            (some (fn [x] (= current-letter x)) target) [:y current-letter]
            :else [:b current-letter]))
        (range (count target))))

[{:g l1} :y l2 :b l3 :b l4 :b l5]
(defn eligible-by-feedback? [word feedback]
  (map (fn [w p]
          (let [present (some (fn [x] (= (nth p 1) x)) word)]
          (case (first p)
            :g (= (nth p 1) w)
            :y (not (= (nth p 1) w))
            :b (not present))))
        word
        feedback))

(defn eligible? [word feedback]
  (every? true? (eligible-by-feedback? word feedback)))

; search for eligible words
(defn eligible-words [word feedback words]
  (filter (partial eligible? word feedback) words))


(def possible-words
  (-> (slurp "data/possible_words.txt")
      (clojure.string/split-lines)
      ((fn [w]
         (zipmap w (repeat (count w) (/ 1 (count w))))))))


(entropy possible-words)

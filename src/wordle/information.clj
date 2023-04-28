(ns wordle.information)

(require
 '[clojure.math :as math])

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


; search for eligible words
(defn eligible-words [words feedback]
  (filter eligible? words))

(defn eligible? [word feedback]
  )
; if green match
; 
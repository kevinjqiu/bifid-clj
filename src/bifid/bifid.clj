
(ns org.bifid
  (:genclass)
  ;(:require )
  ;(:use )
  ;(:import )
  )


(defn polybius-square
  "Generate a map corresponding to the polybius square."
  []
  (map #(vector %1 %2 %3) "ABCDEFGHIKLMNOPQRSTUVWXYZ" 
    (reduce #(concat %1 %2) (repeat 5 [1 2 3 4 5]))
    (for [x (range 1 6) y (range 1 6)] x)))

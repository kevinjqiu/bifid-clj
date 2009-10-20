(comment
 This is my effort to implement Bifid cipher using Clojure.
)

(ns bifid
  (:gen-class))


(defn polybius-square
  "Polybius square by the given charset and size"
  [charset square-size]
  (map #(vector %1 [%2 %3]) charset
    (for [x (range 1 (+ 1 square-size)), y (range 1 (+ 1 square-size))] x)
    (take (count charset) (cycle (range 1 (+ 1 square-size))))))

(defn- letter-2-code
  "Produce a mapping between letters and their codes"
  [square]
  (letfn [(helper [the-square the-map]
          (if (empty? the-square)
            the-map
            (let [the-item (first the-square)
                  the-key (first the-item)
                  the-number (second the-item)]
              (recur (rest the-square) (assoc the-map the-key the-number)))))]
    (helper square (empty hash-map))))

(defn- transposed-2-letter
  "Produce a mapping between the transposed codes to letter"
  [square]
  (letfn [(helper [the-square the-map]
            (if (empty? the-square)
              the-map
              (let [the-item (first the-square)
                    the-key (second the-item)
                    the-letter (first the-item)]
                (recur (rest the-square) (assoc the-map the-key the-letter)))))]
    (helper square (empty hash-map))))

(defn encode
  "Return the message encoded by the specified polybius-square"
  [message polybius-square]
  (let [l2c (letter-2-code polybius-square)
        tr2l (transposed-2-letter polybius-square)]
    (apply str
      (map #(get tr2l % %) (partition 2 (apply interleave (map #(get l2c % %) message)))))))

(defn decode
  "Return the decoded message using the specified polybius-square"
  [encoded polybius-square]
  (let [l2c (letter-2-code polybius-square)
        c2l (transposed-2-letter polybius-square)
        codes (apply concat (map #(get l2c % %) encoded))
        len (count codes)
        parts (partition (/ len 2) codes)]
    (apply str (map #(get c2l %1 %1) (map #(vector %1 %2) (first parts) (second parts))))))

(defn -main
  "A simple test case entry point"
  []
  (let [square (polybius-square "ABCDEFGHIKLMNOPQRSTUVWXYZ" 5)]
    (do
        (println (encode "PROGRAMMINGPRAXIS" square))
        (println (decode "OMQNHHQWUIGBIMWCS" square)))))





(ns lexicographic-permutations-24)

;; Initially I got hung up on working out each digit one-by-one, fiddling with factorials...
;; But then I realised generating the permutations in order was sufficient.

;; credit to https://stackoverflow.com/a/26076145
(defn permutations [s]
  (lazy-seq
    (if (seq (rest s))
      (apply concat (for [x s]
                      (map #(cons x %)
                           (permutations (remove #{x} s)))))
      [s])))

(-> (permutations (range 10))
    (nth (dec (int 1e6)))
    (->> (apply str))
    parse-long)

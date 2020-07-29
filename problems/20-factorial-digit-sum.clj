;;; n! means n × (n − 1) × ... × 3 × 2 × 1

;;; For example, 10! = 10 × 9 × ... × 3 × 2 × 1 = 3628800,
;;; and the sum of the digits in the number 10! is 3 + 6 + 2 + 8 + 8 + 0 + 0 = 27.

;;; Find the sum of the digits in the number 100!

(defn naturals [n]
  (range 1N (inc n))) ; 'N' denotes use of BigInt

(naturals 10) ; => (1N 2N 3N 4N 5N 6N 7N 8N 9N 10N)

(defn lazy-factorial [n] ; Because `range`, above, is lazy!
  (reduce * (naturals n)))

(lazy-factorial 6) ; => 720N

(defn factorial-digit-sum [n]
  (->> (lazy-factorial n)
       (str)
       (seq)
       (map #(Character/digit % 10)) ; converts \ chars to ints
       (reduce #(+ %1 %2))
))

(factorial-digit-sum 100)

; And it works! :D
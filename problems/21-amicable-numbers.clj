;; Let d (n) be defined as the sum of proper divisors of n (numbers less than n which divide evenly into n) .
;; If d (a) = b and d (b) = a, where a ≠ b, then a and b are an amicable pair and each of a and b are called amicable numbers.

;; For example, the proper divisors of 220 are 1, 2, 4, 5, 10, 11, 20, 22, 44, 55 and 110; therefore d(220) = 284. The proper divisors of 284 are 1, 2, 4, 71 and 142; so d(284) = 220.

;; Evaluate the sum of all the amicable numbers under 10000.

(defn proper-divisor-sum [n]
  (->> (range 1 (+ 2 (quot n 2)))
       (filter #(zero? (mod n %)))
       (reduce +)))

(proper-divisor-sum 220) ; => 284
(proper-divisor-sum 284) ; => 220

(defn amicables-under-n [n]
  (->> (range 1 n)
       (filter #(let [a % b (proper-divisor-sum a)]
                  (if (not= a b)
                    (= % (proper-divisor-sum (proper-divisor-sum %)))
                    false)))))

(amicables-under-n 1000) ; => (220 284)
; Seems on the right track!

(reduce + (amicables-under-n 10000))

; Aaand, it works! :)
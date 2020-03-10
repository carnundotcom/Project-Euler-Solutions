;;; The problem: The sum of the squares of the first ten natural numbers is,
;;;     1^2 + 2^2 + ... + 10^2 = 385
;;; The square of the sum of the first ten natural numbers is,
;;;     (1 + 2 + ... + 10)^2 = 55^2 = 3025
;;; Hence the difference between the sum of the squares of the first ten natural numbers and the square of the sum is
;;;     3025 − 385 = 2640.
;;;
;;; Find the difference between the sum of the squares of the first one hundred natural numbers and the square of the sum.

(defn sum-squares
  ([n] (sum-squares n 0))
  ([n result]
   (if (zero? n)
     result
     (recur (dec n) (+ result (* n n))))))

(defn square-sum
  ([n] (square-sum n 0))
  ([n result]
   (if (zero? n)
     (* result result)
     (recur (dec n) (+ result n)))))

(defn sum-square-difference [n]
  (- (square-sum n) (sum-squares n)))

(sum-square-difference 100)

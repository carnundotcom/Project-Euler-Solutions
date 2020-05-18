;;; The sum of the primes below 10 is 2 + 3 + 5 + 7 = 17.
;;; Find the sum of all the primes below two million.

; A good place to start: an is-prime function!

; Inspired by https://stackoverflow.com/questions/35025351/check-a-given-is-prime-or-not-using-clojure
(defn is-prime [n]
  (nil? (first ; returns true if n has no divisors
    (filter #(and (zero? (rem n %)) (not= 2 n)) ; filters the below sequence into divisors of n
      (range 2 (inc (Math/sqrt n))))))) ; generates the sequence (2 3 4 ... sqrt(n))

; Now, we need only run through 2 -> 2mil
(defn sum-primes-below-n [n]
  (->> (range 2 n)
      (filter #(is-prime %) ,,,) ; (commas not strictly necessary!)
      (reduce + ,,,)
))

(sum-primes-below-n 10) ; => expect 17
(sum-primes-below-n 2000000)

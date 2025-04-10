(ns problem-26-reciprocal-cycles
  (:require
    [hyperfiddle.rcf :refer [tests]]))

(defn recurring-decimal? [ratio]
  ; - Terminating decimal <--> denominator divides some power of 10 evenly.
  ; - 10^k = 2^k x 5^k, for any k.
  ; - So, recurring decimal <--> *isn't* composed of just factors of 2 and 5.
  (let [d (denominator ratio)]
    (not= 1 (loop [n d]
              (cond
                (= n 1) 1
                (zero? (mod n 2)) (recur (/ n 2))
                (zero? (mod n 5)) (recur (/ n 5))
                :else n)))))

(tests
  (recurring-decimal? 1/2) := false
  (recurring-decimal? 1/3) := true
  (recurring-decimal? 1/4) := false
  (recurring-decimal? 1/5) := false
  (recurring-decimal? 1/6) := true
  (recurring-decimal? 1/7) := true
  (recurring-decimal? 1/8) := false
  (recurring-decimal? 1/9) := true
  (recurring-decimal? 1/10) := false
  )

(defn recurring-cycle
  "Returns the recurring decimal cycle of candidate-n as a string.

   For example:
   - 1/3 --> \"3\" (since 1/3 = 0.333333...)
   - 1/7 --> \"142857\" (since 1/7 = 0.142857142857...)"
  [candidate-n]
  ; Effectively: do short division, keeping track of the relevant parts...
  (let [divisor (denominator candidate-n)]
    (loop [carry        1
           carry-seen?  #{}
           decimal-part ""]
      (let [numerator (parse-long (str carry 0))
            quotient  (quot numerator divisor)
            remainder (mod numerator divisor)]
        (if (carry-seen? carry)
          ; --> a cycle is about to restart, so return the decimal part from the first
          ;     digit in the cycle
          (re-find (re-pattern (str quotient ".*")) decimal-part)
          ; --> we're mid-cycle, so continue division
          (recur remainder
                 (conj carry-seen? carry)
                 (str decimal-part quotient)))))))

(tests
  (recurring-cycle 1/3) := "3"
  (recurring-cycle 1/6) := "6"
  (recurring-cycle 1/7) := "142857"
  (recurring-cycle 1/9) := "1"
  (recurring-cycle 1/81) := "012345679"
  )

; "Find the value of d < 1000 for which 1/d contains the longest recurring cycle in its
; decimal fraction part."
(comment
  (->> ; Generate all recurring cycles
       (for [d (range 2 1000)
             :let [n (/ 1 d)]
             :when (recurring-decimal? n)]
         [n (recurring-cycle n)])
       ; Sort (descending) by cycle length
       (sort-by (comp count second) #(compare %2 %1))
       ; Return d
       ffirst
       denominator)
  )

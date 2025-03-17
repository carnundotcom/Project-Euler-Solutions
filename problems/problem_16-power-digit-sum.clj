;;; 2^15 = 32768 and the sum of its digits is 3 + 2 + 7 + 6 + 8 = 26.
;;; What is the sum of the digits of the number 2^1000?

; As in the last problem, Clojure's BigInt will be useful, here!

; First, we need to define a function which returns two-to-the-n:

(defn two-to-the-n [n]
  (loop [result 1 i 0]
    (if (= i n)
      result
      (recur (* 2N result) (inc i)))))

(two-to-the-n 15) ; => 32768N

; Looks good! And it's super fast for even 2^1000:

(do
  (def t1 (System/currentTimeMillis))
  (two-to-the-n 1000) ; => 10715086071862673209484250490600018105614048117055...N
  (- (System/currentTimeMillis) t1)) ; => 2, sometimes 1

; Now, all I need to do is reduce 2^1000's digits to their sum!

(defn sum-digits [n]
  (->> (str n)
       (seq)
       (map #(read-string (str %)))
       (reduce + 0)))

(sum-digits (two-to-the-n 15)) ; => 26

(sum-digits (two-to-the-n 1000))

; Looks good!
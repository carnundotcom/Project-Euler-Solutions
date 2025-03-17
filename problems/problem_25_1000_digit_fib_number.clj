(ns problem-25-1000-digit-fib-number)

(defn lazy-fibonacci-seq []
  (letfn [(fib-step [a b]
            (cons a (lazy-seq (fib-step b (+ a b)))))]
    ; fib nums get big FAST, so use BigInt
    (fib-step 1N 1N)))

(comment
  (->> (map-indexed vector (lazy-fibonacci-seq))
       (some #(when (= 1000 (count (str (second %))))
                (inc (first %))))))

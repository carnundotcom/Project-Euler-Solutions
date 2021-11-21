(ns non-abundant-sums-23)

(def abundant?
  "Returns truthy if n is abundant."
  (memoize
    (fn [n]
      (< n
         (transduce
           (filter #(zero? (mod n %)))
           +
           1
           (range 2 (inc (int (/ n 2)))))))))

(defn- abundant-pair?
  "Returns truthy if both of m and (n - m) are abundant."
  [n m]
  (and (abundant? m) (abundant? (- n m))))

(defn abundant-sum?
  "Returns truthy if n can be written as the sum of two abundant numbers,
  and falsy otherwise."
  [n]
  (let [r (range 1 (inc (int (/ n 2))))]
    (some (partial abundant-pair? n) r)))

(time
  (transduce
    (remove abundant-sum?)
    + 
    (reduce + (range 1 24))
    (range 25 28124)))

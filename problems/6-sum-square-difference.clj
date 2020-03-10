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

;;; The following iterative sequence is defined for the set of positive integers:

;;;   n → n/2 (n is even)
;;;   n → 3n + 1 (n is odd)

;;; Using the rule above and starting with 13, we generate the following sequence:

;;;   13 → 40 → 20 → 10 → 5 → 16 → 8 → 4 → 2 → 1

;;; It can be seen that this sequence (starting at 13 and finishing at 1) contains 10 terms. 
;;; Although it has not been proved yet (Collatz Problem), it is thought that all starting numbers finish at 1.

;;; Which starting number, under one million, produces the longest chain?

;;; NOTE: Once the chain starts the terms are allowed to go above one million.


; Hmm. I'm not sure how to go about this one other than by looping through all possible starting numbers.
; So I guess I'll do that!

; First, I need a way of computing the length of a given sequence:
(defn collatz-count [n & count]
  (let [new-count (if (nil? count) 1 (inc count))]
    (if (even? n)
      (recur (/ n 2) new-count)
      (if (= n 1)
        new-count
        (recur (+ (* 3 n) 1) new-count)))))

(collatz-count 13) ; => 10

; Seems to be working!

; Next, I need a way of looping through all possible starting numbers, updating a 'max' count as I go:
(defn find-max-count [starting-n]
  (loop [n starting-n
                max 0]
    (if (= n 1)
      max
      (recur
        (dec n)
        (let [cc (collatz-count n)]
          (if (> cc max)
            cc
            max))))))

(find-max-count 5) ; => 8

(collatz-count 5) ; => 6
(collatz-count 4) ; => 3
(collatz-count 3) ; => 8
(collatz-count 2) ; => 2 
(collatz-count 1) ; => 1

; Seems to be working. Although (collatz-count 3) => 8 is a little hard to believe! But:
; 3 -> 10 -> 5 -> 16 -> 8 -> 4 -> 2 -> 1

(find-max-count 999999) ; => 525

; Works fast enough! Except I've just realised the question calls for _the starting number_ which produces the longest chain.
; Not _the length of the chain itself_!

; No worries. The modification is trivial:
(defn find-max-count-starter [starting-n]
  (loop [n starting-n
                max 0
        max-starter 1]
    (if (= n 1)
      max-starter
      (let [cc (collatz-count n)]
        (recur
          (dec n)
          (if (> cc max)
            cc
            max)
          (if (> cc max)
            n
            max-starter))))))
            
(find-max-count-starter 5) ; => 3

; It works! So:

(find-max-count-starter 999999)
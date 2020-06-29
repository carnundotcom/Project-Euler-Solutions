;;; Starting in the top left corner of a 2×2 grid, and only being able to move to the right and down,
;;; there are exactly 6 routes to the bottom right corner.

;;; How many such routes are there through a 20×20 grid?

; Alrighty:
;   - First thing I noticed, right away, was that the 6 routes come in 3 diagonally symmetrical pairs.
;   - The second thing I noticed was that, because the grid is 2x2, a valid route must comprise exactly 2 downward and 2 rightward moves.
;       - So, we can rephrase the problem: 'How many ways are there to produce a sequence of 2 Ds and 2 Rs?'
;       - And this framing generalises! 'How many ways... of n Ds and n Rs?'

; Now, because of the symmetry, we can ignore half of the possibilities. Which half? Well, that doesn't matter.
; So why not the half starting with 'R'? :P

; Our problem is then one of building, and then pruning, a full binary tree, n^2 - 1 levels deep! And counting its leftover top-level leaves.

; There are (2^n) leaves in a full binary tree, n levels deep.
; In the 2x2 case, we can build a binary tree 3 levels deep, with 8 leaves, where each branch represents a choice of either 'D' or 'R'.
; Recall: the tree is 3 (2^2 - 1) levels deep because we're assuming that the first choice was 'D'.
; Now, how many leaves are pruned?

; Here's the tree:
;           D
;          / \
;         D   R
;        /\   /\
;       D  R D  R
;      /\ /\ /\ /\
;     D  RD RD RD R

; Every third D (and everything after it) has to go! Leaving:

;           D
;          / \
;         D   R
;        /\   /\
;       D  R D  R
;          \  \ /\
;           R  RD R

; Ditto for every third R. Leaving only *three* leaves:

;           D
;          / \
;         D   R
;        /\   /\
;       D  R D  R
;          \  \ /
;           R  RD

; Then, 2*3 gets us 6.

; The analogous process for the 20x20 grid goes like this:
; 1) Generate a binary tree, 399 levels deep (!)...

; That's a bit much, ha. 2^399 is pretty damn big!
; We're going to have to approach things a little differently.

; Hmm. How about treating it as a simple combinatorics problem?
; I.e., in the 2x2 grid case, our task is analogous to asking:
;   'How many unique, 4-long sequences of 2 Ds and 2 Rs exist?'

; Imagine 4 open 'slots' in the sequence. Each can be filled by either a D or an R.
; So in total, there are 2^4 = 16 possible sequences.

; But some of these—as before, with some of our binary trees—are sequences containing more than 2 Ds or 2 Rs.
; In particular, there are 2(4C4) sequences containing all Ds or all Rs, and 2(4C3) sequences containing
; either 3 Ds or 3Rs.

; So, subtracting these: total valid sequences = 2^4 - 2(4C4) - 2(4C3) = 16 - 2(1) - 2(4) = 16 - 2 - 8 = 6.
; Which is what we want!

; Generalising this idea to an nxn grid gives:
;   total valid sequences = 2^(n^2) - 2((n^2)C(n^2)) - 2((n^2)C((n^2)-1)) - ... - 2((n^2)C(n+1))

; So, we're still dealing with giant numbers! But thankfully we don't need to build giant binary trees!

; Also, Clojure supplies a handy construct for arbitrary-precision integers: BigInt!

(defn two-to-the-n [n]
  (loop [l 1 result 1]
    (if (> l n)
      result
    (recur (inc l) (* 2N result))))) ; The N after 2, here, indicates use of BigInt.

(two-to-the-n 2) ; => 4N
(two-to-the-n 20) ; => 1048576N
(two-to-the-n 200) ; => 1606938044258990275541962092341162602522202993782792835301376N
; etc.

; Now, we need a BigInt way of handling combinations: nCr = n! / r! * (n - r)!

(defn big-n-fact [n]
  (loop [i n result 1N]
    (if (zero? n)
      1N
      (if (= i 1)
        result
        (recur (dec i) (* result i))))))
    
(big-n-fact 0) ; => 1N
(big-n-fact 1) ; => 1N
(big-n-fact 5) ; => 120N
(big-n-fact 10) ; => 3628800N
; etc.

(defn n-choose-r [n r]
  (/ (big-n-fact n) (* (big-n-fact r) (big-n-fact (- n r)))))

(n-choose-r 4 4) ; => 1N
(n-choose-r 4 3) ; => 4N

; Looks good! Time to bring everything together:
(defn valid-grid-routes [n]
  (let [n-squared (* n n 1N)]
    (loop [i n-squared result (two-to-the-n n-squared)]
      (if (= n i)
        result
        (recur (dec i) (- result (* 2 (n-choose-r n-squared i))))))))

(valid-grid-routes 2) ; => 6N

; Seems to be working!

(valid-grid-routes 3) ; => -252N

; Ah. Something's wrong.

; Let's take a look at our formula again:
;   total valid sequences = 2^(n^2) - 2((n^2)C(n^2)) - 2((n^2)C((n^2)-1)) - ... - 2((n^2)C(n+1))

; Ah! The factor of 2^n is the issue! And only coincidentally right for the 2x2 case:
; What we really want is a factor of 2*n, because a valid path is 2*n 'choices' long!
; (I guess I still had 2^n on the brain from the binary trees, ha...)

; So, our new formula looks like:
;   total valid sequences = 2^(2*n) - 2((2*n)C(2*n)) - 2((2*n)C((2*n)-1)) - ... - 2((2*n)C(n+1))

; And our new valid-grid-routes function looks like:
(defn real-valid-grid-routes [n]
  (let [two-n (* n 2N)]
    (loop [i two-n result (two-to-the-n two-n)]
      (if (= n i)
        result
        (recur (dec i) (- result (* 2 (n-choose-r two-n i))))))))

(real-valid-grid-routes 2) ; => 6N
(real-valid-grid-routes 3) ; => 20N
(real-valid-grid-routes 4) ; => 70N

; Definitely looks less absurd, ha!

(real-valid-grid-routes 20)

; And it works! :D

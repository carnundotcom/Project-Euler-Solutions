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

; Our problem is then one of building, and then pruning, a full binary tree, n^2 - 1 levels deep! And counting its leftover branches.

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
; We're going to approach things a little differently.

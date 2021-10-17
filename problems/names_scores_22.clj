;;; Using names.txt, a 46K text file containing over five-thousand first names, begin by sorting it into alphabetical order.
;;; Then working out the alphabetical value for each name, multiply this value by its alphabetical position in the list to obtain a name score.

;;; For example, when the list is sorted into alphabetical order, COLIN, which is worth 3 + 15 + 12 + 9 + 14 = 53, is the 938th name in the list.
;;; So, COLIN would obtain a score of 938 × 53 = 49714.

;;; What is the total of all the name scores in the file?


; so... this one is pretty straightforward:
;   1) read the file into a list
;   2) sort it
;   3) map & reduce
;   4) sum

(ns names-scores-22
  (:require [clojure.data.csv :as csv]
            [clojure.java.io :as io]))

(def names
  (first
    (with-open [r (io/reader "files/p022_names.txt")]
      (doall (csv/read-csv r)))))

(def char->score
  (zipmap [\A \B \C \D \E \F \G \H \I \J \K \L \M \N \O \P \Q \R \S \T \U \V \W \X \Y \Z]
          (range 1 27)))

(time
  (->> names
       sort
       (map-indexed (fn [i n]
                      (* (inc i)
                         (reduce #(+ %1 (char->score %2)) 0 n))))
       (reduce +)))

; hmm. this works fine, but I suspect it isn't ideal efficiency-wise

; it would be nice if we could combine steps (1) and (2), and (3) and (4)
; so, build up a sorted list _as_ we read from the file, then do all the rest in one step

(time
  (let [xform (map-indexed (fn [i n]
                             (* (inc i)
                                (reduce #(+ %1 (char->score %2)) 0 n))))]
    (with-open [r (io/reader "files/p022_names.txt")]
      (->> (csv/read-csv r)
           first
           sort
           (transduce xform +)))))

; hmm. hard to say if this is any faster, actually! :P

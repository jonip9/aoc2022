(ns aoc2022.day6
  (:require [clojure.string :as s]))

(defn read-input
  "Read input."
  [input-file]
  (-> input-file
      slurp
      s/trim-newline))

(defn find-signal
  "Day 6, part 1 and 2. Find marker or message in string.
  Finds marker if only `input-file` is passed, or message if `i` is also passed."
  ([input-file] (find-signal input-file 0 4))
  ([input-file i] (find-signal input-file i 14))
  ([input-file i char-len]
   (let [data (read-input input-file)]
     (loop [pos i]
       (let [pos-end (+ pos char-len)
             cur (subs data pos pos-end)]
         (if (= (count (distinct cur)) char-len)
           (+ (s/index-of data cur) char-len)
           (recur (inc pos))))))))

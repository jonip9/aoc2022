(ns aoc2022.day8
  (:require [clojure.string :as s]
            [clojure.set :as set]
            [clojure.java.io :as io]))

(defn input->tree-vec
  "Read input file and create a matrix of integers representing trees."
  [input-file]
  (->> input-file
       io/resource
       slurp
       s/split-lines
       (mapv #(mapv (fn [n] (Integer/parseInt n))
                    (s/split % #"")))))

(defn tree-height
  "Day 8, part 1. Find the height of a tree at the given position."
  [trees [pos-i pos-j]]
  (if (vector? (first trees))
    (get-in trees [pos-j pos-i])
    (get trees pos-j)))

(defn find-visible
  "Day 8, part 1. Find visible trees from the beginning of a row or column."
  [trees pos-i]
  (let [max-pos (dec (count trees))]
    (loop [pos-j 0
           tallest (tree-height trees [pos-i pos-j])
           visible #{}]
      (let [cur (tree-height trees [pos-i pos-j])]
        (cond
          (= pos-j max-pos) visible
          (> cur tallest) (recur (inc pos-j)
                                 cur
                                 (conj visible (if (vector? (first trees))
                                                 (str pos-j " " pos-i)
                                                 (str pos-i " " pos-j))))
          :else (recur (inc pos-j)
                       tallest
                       visible))))))

(defn rfind-visible
  "Day 8, part 1. Find visible trees from the end of a row or column."
  [trees pos-i]
  (let [max-pos 0]
    (loop [pos-j (dec (count trees))
           tallest (tree-height trees [pos-i pos-j])
           visible #{}]
      (let [cur (tree-height trees [pos-i pos-j])]
        (cond
          (= pos-j max-pos) visible
          (> cur tallest) (recur (dec pos-j)
                                 cur
                                 (conj visible (if (vector? (first trees))
                                                 (str pos-j " " pos-i)
                                                 (str pos-i " " pos-j))))
          :else (recur (dec pos-j)
                       tallest
                       visible))))))

(defn visible-trees
  "Day 8, part 1. Count how many trees are visible from outside."
  [input]
  (let [tree-vec (input->tree-vec input)
        max-rows (-> tree-vec
                     count
                     dec)
        max-cols (-> tree-vec
                     first
                     count
                     dec)]
    (loop [row 1
           col 1
           visible #{}]
      (let [cur-row (get tree-vec row)]
        (cond
          (< row max-rows) (recur (inc row)
                                  col
                                  (set/union visible
                                             (find-visible cur-row row)
                                             (rfind-visible cur-row row)))
          (< col max-cols) (recur row
                                  (inc col)
                                  (set/union visible
                                             (find-visible tree-vec col)
                                             (rfind-visible tree-vec col)))
          :else (- (+ (count visible)
                      (* (count tree-vec) 2)
                      (* (count (first tree-vec)) 2))
                   4))))))

(defn count-side
  "Day 8, part 2. Count how many trees are visible in the given side."
  [side tree]
  (loop [the-rest side
         score 0]
    (cond
      (empty? the-rest) score
      (>= (first the-rest) tree) (inc score)
      :else (recur (rest the-rest)
                   (inc score)))))

(defn multiply-sides
  "Day 8, part 2. Multiply each of a tree together."
  [sides tree]
  (reduce #(* %1 (count-side %2 tree)) 1 sides))

(defn best-visibility
  "Day 8, part 2. Find the tree with the best visibility."
  [input]
  (let [tree-vec (input->tree-vec input)
        max-rows (-> tree-vec
                     count
                     dec)
        max-cols (-> tree-vec
                     first
                     count
                     dec)]
    (loop [[row col] [0 0]
           top-score 0]
      (let [cur-tree (get-in tree-vec [row col])
            cur-row (get tree-vec row)
            cur-col (mapv #(get % col) tree-vec)
            left (reverse (subvec cur-row 0 col))
            right (subvec cur-row (inc col))
            up (reverse (subvec cur-col 0 row))
            down (subvec cur-col (inc row))
            score (multiply-sides [left right up down] cur-tree)]
        (cond
          (< col max-cols) (recur [row (inc col)]
                                  (if (> score top-score)
                                    score
                                    top-score))
          (and (= col max-cols)
               (< row max-rows)) (recur [(inc row) 0]
                                        top-score)
          :else top-score)))))


(ns aoc2022.day3
  (:require [clojure.string :as str]
            [clojure.set :refer [intersection]]
            [clojure.java.io :as io]))

(def items "0abcdefghijklmnopqrstuvwxyzABCDEFGHIJKLMNOPQRSTUVWXYZ")

(defn sum-compartments
  "Day 3, part 1. Find and sum the items that appear in both compartments of each elf."
  [input-file]
  (let [rucksacks (map #(let [half (-> % count (/ 2) int)]
                          (vector (subs % 0 half)
                                  (subs % half)))
                       (-> input-file
                           io/resource
                           slurp
                           str/split-lines))]
    (reduce #(let [[left right] %2
                   dupe (first (intersection (set left)
                                             (set right)))]
               (+ %1 (str/index-of items dupe)))
            0
            rucksacks)))

(defn find-badges
  "Day 3, part 2. Find the badges belonging to each group of elves."
  [rucksacks]
  (reduce #(let [[one two three] %2
                 found-char (first (intersection (set one)
                                                 (set two)
                                                 (set three)))]
             (conj %1 found-char))
          []
          (partition 3 rucksacks)))

(defn sum-badges
  "Day 3, part 2. Sum total score of badges."
  [input-file]
  (let [rucksacks (-> input-file
                      io/resource
                      slurp
                      str/split-lines)
        badges (find-badges rucksacks)]
    (reduce #(+ %1 (str/index-of items %2)) 0 badges)))

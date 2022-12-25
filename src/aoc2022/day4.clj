(ns aoc2022.day4
  (:require [clojure.string :as str]
            [clojure.set :as set]
            [clojure.java.io :as io]))

(defn parse-input
  "Day 1. Parse input."
  ([input-file]
   (->> input-file
        io/resource
        slurp
        str/split-lines
        (map #(map (fn [n] (Integer/parseInt n))
                   (str/split % #"-|,"))))))

(defn count-overlaps
  "Day 1, part 1 and 2. Count instances where ranges overlap, both full and partial."
  [input-file]
  (reduce #(let [[a-start a-end b-start b-end] %2
                 first-r (set (range a-start (inc a-end)))
                 second-r (set (range b-start (inc b-end)))]
             (cond
               (or (set/subset? first-r second-r)
                   (set/subset? second-r first-r))
               (assoc %1
                      :full-overlaps (inc (:full-overlaps %1))
                      :overlaps (inc (:overlaps %1)))
               (-> (set/intersection first-r second-r)
                   count
                   zero?
                   not)
               (assoc %1 :overlaps (inc (:overlaps %1)))
               :else
               %1))
          {:full-overlaps 0
           :overlaps 0}
          (parse-input input-file)))

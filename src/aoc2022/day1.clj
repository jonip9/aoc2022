(ns aoc2022.day1
  (:require [clojure.string :as str]
            [clojure.java.io :as io]))

(defn count-calories
  "Day 1. Count total calories of each elf."
  [input-file]
  (let [split-cals (map #(str/split % #"\n")
                        (-> input-file
                            io/resource
                            slurp
                            str/trim
                            (str/split #"\n\n")))]
    (map #(reduce (fn [acc val]
                    (+ acc (Integer/parseInt val)))
                  0
                  %)
         split-cals)))

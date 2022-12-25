(ns aoc2022.core
  (:gen-class)
  (:require [clojure.string :as str]
            [aoc2022.day1 :refer [count-calories]]
            [aoc2022.day2 :refer [calculate-rps-score-p1
                                  calculate-rps-score-p2]]
            [aoc2022.day3 :refer [sum-compartments
                                  sum-badges]]
            [aoc2022.day4 :refer [count-overlaps]]
            [aoc2022.day5 :refer [move-crates-p1
                                  move-crates-p2]]
            [aoc2022.day6 :refer [find-signal]]
            [aoc2022.day7 :refer [input->dir-sizes
                                  filter-and-sum-dirs
                                  find-smallest-dir-to-del]]
            [aoc2022.day8 :refer [visible-trees
                                  best-visibility]]))

(defn -main
  "Advent of code 2022"
  [& args]
  (let [[file-name] args
        input-file (str "resources/" file-name)]
    (condp = file-name
      "day1.txt" (let [counted-cals (count-calories input-file)]
                   (println (apply max counted-cals))
                   (println (apply + (take-last 3 (sort counted-cals)))))
      "day2.txt" (let [p1-result (calculate-rps-score-p1 input-file)
                       p2-result (calculate-rps-score-p2 input-file)]
                   (println p1-result)
                   (println p2-result))
      "day3.txt" (do (println (sum-compartments input-file))
                     (println (sum-badges input-file)))
      "day4.txt" (println (count-overlaps input-file))
      "day5.txt" (do (println (move-crates-p1 input-file))
                     (println (move-crates-p2 input-file)))
      "day6.txt" (let [marker (find-signal input-file)]
                   (println marker)
                   (println (find-signal input-file marker)))
      "day7.txt" (let [dir-size-map (input->dir-sizes file-name)]
                   (do (println (filter-and-sum-dirs dir-size-map))
                       (println (find-smallest-dir-to-del dir-size-map))))
      "day8.txt" (do (println (visible-trees input-file))
                     (println (best-visibility input-file))))))

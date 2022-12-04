(ns aoc2022.core
  (:gen-class)
  (:require [clojure.string :as str]
            [aoc2022.day1 :refer [count-calories]]
            [aoc2022.day2 :refer [calculate-rps-score-p1
                                  calculate-rps-score-p2]]))

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
                   (println p2-result)))))

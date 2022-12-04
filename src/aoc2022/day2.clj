(ns aoc2022.day2
  (:require [clojure.string :as str]))

(def rps {:rock 1
          :paper 2
          :scissors 3})

(def opponent-hand {:A :rock
                    :B :paper
                    :C :scissors})

(def player-hand-p1 {:X :rock
                     :Y :paper
                     :Z :scissors})

(def player-hand-p2 {:X :lose
                     :Y :draw
                     :Z :win})

(def round-end-scores {:win 6
                       :lose 0
                       :draw 3})

(defn calculate-rps-score-p2
  "Day 2, part 2. Calculate players total score when round result is known."
  [input-file]
  (let [rounds (with-open [rdr (clojure.java.io/reader input-file)]
                 (reduce conj [] (line-seq rdr)))]
    (reduce (fn [acc val]
              (let [[opp pla] (str/split val #" ")
                    kw-o (keyword opp)
                    kw-p (keyword pla)
                    o-hand (-> opponent-hand kw-o)
                    p-hand (-> player-hand-p2 kw-p)]
                (+ acc
                   (p-hand round-end-scores)
                   (cond
                     (= p-hand :lose)
                     (condp = o-hand
                       :rock (:scissors rps)
                       :paper (:rock rps)
                       :scissors (:paper rps))
                     (= p-hand :win)
                     (condp = o-hand
                       :rock (:paper rps)
                       :paper (:scissors rps)
                       :scissors (:rock rps))
                     :else
                     (o-hand rps)))))
            0
            rounds)))

(defn calculate-rps-score-p1
  "Day 2, part 1. Calculate players total score when hand is known."
  [input-file]
  (let [rounds (with-open [rdr (clojure.java.io/reader input-file)]
                 (reduce conj [] (line-seq rdr)))]
    (reduce (fn [acc val]
              (let [[opp pla] (str/split val #" ")
                    kw-o (keyword opp)
                    kw-p (keyword pla)
                    o-hand (-> opponent-hand kw-o rps)
                    p-hand (-> player-hand-p1 kw-p rps)]
                (+ acc
                   p-hand
                   (cond
                     (= -2 (- p-hand o-hand))
                     (:win round-end-scores)
                     (= 0 (- p-hand o-hand))
                     (:draw round-end-scores)
                     (= 1 (- p-hand o-hand))
                     (:win round-end-scores)
                     :else
                     (:lose round-end-scores)))))
            0
            rounds)))

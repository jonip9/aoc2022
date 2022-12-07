(ns aoc2022.day5
  (:require [clojure.string :as str]))

;;; Starting state

;;             [J] [Z] [G]            
;;             [Z] [T] [S] [P] [R]    
;; [R]         [Q] [V] [B] [G] [J]    
;; [W] [W]     [N] [L] [V] [W] [C]    
;; [F] [Q]     [T] [G] [C] [T] [T] [W]
;; [H] [D] [W] [W] [H] [T] [R] [M] [B]
;; [T] [G] [T] [R] [B] [P] [B] [G] [G]
;; [S] [S] [B] [D] [F] [L] [Z] [N] [L]
;;  1   2   3   4   5   6   7   8   9

(def crates {:1 [\S \T \H \F \W \R]
             :2 [\S \G \D \Q \W]
             :3 [\B \T \W]
             :4 [\D \R \W \T \N \Q \Z \J]
             :5 [\F \B \H \G \L \V \T \Z]
             :6 [\L \P \T \C \V \B \S \G]
             :7 [\Z \B \R \T \W \G \P]
             :8 [\N \G \M \T \C \J \R]
             :9 [\L \G \B \W]})

(defn input->mapv
  "Transfrom input into more usable format."
  [input-file]
  (let [input-lines (->> input-file
                         slurp
                         str/split-lines
                         (map #(str/split % #" ")))]
    (map #(->> %
               (partition 2)
               (map (fn [vec]
                      (vector (keyword (first vec))
                              (Integer/parseInt (second vec)))))
               flatten
               (apply hash-map))
         input-lines)))

(defn move-crates-1
  "Day 5, part 1."
  [input-file]
  (let [procedures (input->mapv input-file)]
    (sort (reduce #(let [to-kw (-> %2 :to str keyword)
                         from-kw (-> %2 :from str keyword)
                         amount (-> %2 :move)
                         taken (reverse (take-last amount (from-kw %1)))
                         rema (drop-last amount (from-kw %1))]
                     (assoc %1
                            to-kw
                            (concat (to-kw %1) taken)
                            from-kw
                            rema))
                  crates
                  procedures))))

(defn move-crates-p2
  "Day 5, part 2."
  [input-file]
  (let [procedures (input->mapv input-file)]
    (sort (reduce #(let [to-kw (-> %2 :to str keyword)
                         from-kw (-> %2 :from str keyword)
                         amount (-> %2 :move)
                         taken (take-last amount (from-kw %1))
                         rema (drop-last amount (from-kw %1))]
                     (assoc %1
                            to-kw
                            (concat (to-kw %1) taken)
                            from-kw
                            rema))
                  crates
                  procedures))))

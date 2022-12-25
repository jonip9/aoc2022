(ns aoc2022.day7
  (:require [clojure.string :as s]
            [clojure.java.io :as io]))

(defn path-vec->path-str
  "Day 7. Transform a vector of directories into a string."
  [path-vec]
  (s/replace (s/join "/" path-vec) #"//" "/"))

(defn update-sizes
  "Day 7. Sum file size into each directory in the given path."
  [path-vec dir-map size]
  (loop [path path-vec
         dmap dir-map]
    (if (empty? path)
      dmap
      (recur (pop path)
             (update dmap
                     (path-vec->path-str path)
                     +
                     size)))))

(defn input->dir-sizes
  "Day 7. Count the total size of each directory in the file system."
  [input-file]
  (loop [lines (-> (io/resource input-file)
                   slurp
                   s/split-lines)
         dir-sizes {}
         wd []]
    (let [[arg1 arg2 arg3] (when-not (empty? lines)
                             (s/split (first lines) #" "))]
      (cond
        (empty? lines) dir-sizes 
        (= arg2 "cd") (if (= arg3 "..")
                        (recur (rest lines)
                               dir-sizes
                               (pop wd))
                        (recur (rest lines)
                               (assoc dir-sizes (path-vec->path-str (conj wd arg3)) 0)
                               (conj wd arg3)))
        (re-find #"^\d+" arg1) (recur (rest lines)
                                      (update-sizes wd dir-sizes (Integer/parseInt arg1))
                                      wd)
        :else (recur (rest lines) dir-sizes wd)))))

(defn filter-and-sum-dirs
  "Day 7, part 1. Find and sum together all directories with the size of at least 100000."
  [dirs]
  (let [filtered (filter #(<= (second %) 100000) dirs)]
    (reduce #(+ %1 (second %2)) 0 filtered)))

(defn find-smallest-dir-to-del
  "Day 7, part 2. Find the size of a directory small enough to free enough space."
  [dirs]
  (let [space-needed (->> "/"
                          (get dirs)
                          (- 70000000)
                          (- 30000000))]
    (apply min (reduce #(if (>= (second %2) space-needed)
                          (conj %1 (second %2))
                          %1)
                       []
                       dirs))))

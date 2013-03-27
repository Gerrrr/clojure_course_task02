(ns clojure-course-task02.core
  (:import java.io.File)
  (:gen-class))


(defn find-files [file-name path]
  (let [acc (ref [])
         pattern (re-pattern file-name)
         walk (fn walk [p]
                (let [lst (.listFiles p)
                      files (->> lst
                              (filter #(.isFile %))
                              (map #(.getName %))
                              (filter #(re-matches pattern %)))
                      dirs (filter #(.isDirectory %) lst)]
                  (dosync
                    (alter acc concat files))
                  (dorun (pmap (fn [x] (walk x)) dirs))))]
    (walk (File. path))
    (deref acc)))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))

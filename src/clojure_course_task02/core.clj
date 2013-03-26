(ns clojure-course-task02.core
  (:import java.io.File)
  (:gen-class))



(defn find-files [file-name path]
  "TODO: Implement searching for a file using his name as a regexp.
make a search using tree-seq"
  (filter #(re-matches (re-pattern file-name) %)
    (map #(.getName %)
      (tree-seq
        #(.isDirectory %)
        #(.listFiles %)
        (File. path)))))

(defn usage []
  (println "Usage: $ run.sh file_name path"))

(defn -main [file-name path]
  (if (or (nil? file-name)
          (nil? path))
    (usage)
    (do
      (println "Searching for " file-name " in " path "...")
      (dorun (map println (find-files file-name path))))))

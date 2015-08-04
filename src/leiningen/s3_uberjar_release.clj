(ns leiningen.s3-uberjar-release
  (:require [lein-s3-uberjar-release.core :refer :all]))

(defn s3-uberjar-release
  "Release an uberjar to s3"
  [project & args]
  (do-release project))
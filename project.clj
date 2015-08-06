(defproject com.rafflecopter/lein-s3-uberjar-release "0.1.5-SNAPSHOT"
  :description "Upload an uberjar to s3 as a release task"
  :url "http://github.com/Rafflecopter/lein-s3-uberjar-release"
  :license {:name "MIT"
            :url "http://github.com/Rafflecopter/lein-s3-uberjar-release/blob/master/LICENSE"}
  :eval-in-leiningen true
  :deploy-repositories [["releases" :clojars]])

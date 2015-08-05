(ns lein-s3-uberjar-release.core
  (:require [leiningen.uberjar :as uberjar]
            [leiningen.jar :as jar]
            [leiningen.core.eval :as eval]
            [leiningen.vcs :as vcs]
            [clojure.string :as string]
            [clojure.java.io :as io]))

(defn- capture-sh [& args]
  (let [{:keys [exit err out]} (apply clojure.java.shell/sh args)]
    (if (< 0 exit) (throw (Exception. err)))
    (clojure.string/trim out)))

(defn- basename [filename]
  (-> filename
      io/file
      .getName))

(defn- uberjar-local [project]
  (jar/get-jar-filename project :standalone))
(defn- shafile-local [project]
  (string/replace (uberjar-local project) #"\.jar" ".sha512"))

(defn git-version [project]
  (try (capture-sh "git" "rev-parse" "HEAD")
    (catch Exception e "")))

(defn- uberjar-target [project]
  (let [{:keys [bucket prefix filename]} (:s3-uberjar-release project)
        prefix (if prefix (format prefix (git-version project)) "")]
       (str "s3://" bucket prefix "/" (basename (or filename (uberjar-local project))))))

(defn- shafile-target [project]
  (string/replace (uberjar-target project) #"\.jar" ".sha512"))

(defn- calc-uberjar-sha [project]
  (-> (capture-sh "shasum" "-a" "512" (uberjar-local project))
      (clojure.string/split #"[ \t]")
      first))

(defn- make-shafile [project]
  (let [file (shafile-local project)
        sha (calc-uberjar-sha project)]
    (spit file sha)
    (println "Wrote shafile" (shafile-local project) "as" sha)))

(defn- upload-uberjar [project]
  (println "Uploading uberjar" (uberjar-target project))
  (eval/sh "s3cmd" "put" (uberjar-local project)
                         (uberjar-target project)))

(defn- upload-shafile [project]
  (println "Uploading uberjar sha512" (shafile-target project))
  (eval/sh "s3cmd" "put" "-P" (shafile-local project)
                              (shafile-target project)))

(defn- tag-release [project]
  (eval/sh "git" "tag" "-s" "release" "-m" "Marked for release" "-f")
  (eval/sh "git" "push" "origin" "+release"))


(defn do-release [project]
  (doto project
    (tag-release)
    (uberjar/uberjar)
    (make-shafile)
    (upload-uberjar)
    (upload-shafile)))
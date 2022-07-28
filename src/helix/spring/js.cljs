(ns helix.spring.js
  (:require
    ["react-spring" :refer [animated]]))

(defn get-animated [type]
  (aget animated type))

(ns helix.framer-motion.core
  (:require
    ["framer-motion" :refer [motion]]))

(defn get-motion [type]
  (aget motion type))
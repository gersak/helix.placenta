(ns helix.framer_motion.core
  (:require
    ["framer-motion" :refer [motion]]))

(defn get-motion [type]
  (aget motion type))

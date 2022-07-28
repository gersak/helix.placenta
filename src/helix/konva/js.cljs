(ns helix.konva.js
  (:require ["react-konva" :as konva]))

(defn get-konva [type]
  (aget konva type))

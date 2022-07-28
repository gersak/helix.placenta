(ns helix.virtual-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.virtual.main :as vir]))


(def ^:export default
  #js {:title "Virtual components/Fixed"})

#_(defn ^:export PrintText []
    ($ vir/PrintText))

#_(defn ^:export NonVirtualRows []
    ($ vir/NonVirtualRows))

(defn ^:export FixedRows []
  ($ vir/FixedRows))

(defn ^:export FixedColumns []
  ($ vir/FixedColumns))

(defn ^:export FixedGrid []
  ($ vir/FixedGrid))
(ns helix.virtual5-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.virtual.main :as vir]))


(def ^:export default
  #js {:title "Virtual components/Tables"})

(defn ^:export FixedTable []
  ($ vir/FixedTable))

(defn ^:export DynamicTable []
  ($ vir/DynamicTable))
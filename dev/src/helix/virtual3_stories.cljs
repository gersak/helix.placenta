(ns helix.virtual3-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.virtual.main :as vir]))


(def ^:export default
  #js {:title "Virtual components/Dynamic"})

(defn ^:export DynamicRows []
  ($ vir/DynamicRows))

(defn ^:export DynamicColumns []
  ($ vir/DynamicColumns))

(defn ^:export DynamicGrid []
  ($ vir/DynamicGrid))

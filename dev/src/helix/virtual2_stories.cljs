(ns helix.virtual2-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.virtual.main :as vir]))


(def ^:export default
  #js {:title "Virtual components/Variable"})

(defn ^:export VariableRows []
  ($ vir/VariableRows))

(defn ^:export VariableColumns []
  ($ vir/VariableColumns))

(defn ^:export VariableGrid []
  ($ vir/VariableGrid))
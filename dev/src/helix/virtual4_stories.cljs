(ns helix.virtual4-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.virtual.main :as vir]))


(def ^:export default
  #js {:title "Virtual components/Window"})

(defn ^:export WindowRows []
  ($ vir/WindowRows))

(defn ^:export WindowColumns []
  ($ vir/WindowColumns))

(defn ^:export WindowGrid []
  ($ vir/WindowGrid))
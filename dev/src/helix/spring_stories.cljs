(ns helix.spring-stories
  (:require
    [helix.core :refer [$]]
    [helix.dom :as d]
    [helix.spring.basic :as s]))


(def ^:export default
  #js {:title "Spring components"
       :component s/Hello})


(defn ^:export BiloKak []
  ($ s/Hello))

(defn ^:export ByBy []
  (d/div "Pozdrav iz klasnicnog DOM-a"))
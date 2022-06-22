(ns helix.spring-stories
  (:require
    [helix.core :refer [$]]
    [helix.spring.basic :as s]))


(def ^:export default
  #js {:title "Spring components"
       :component s/Hello})


(defn ^:export Hello []
  ($ s/Hello))

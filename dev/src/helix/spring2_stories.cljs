(ns helix.spring2-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/StartingExamples"
       :component s/Hello})

(defn ^:export DinamicTextColorChange []
  ($ s/Hello))

(defn ^:export JustADiv []
  (d/div "Pozdrav iz klasnicnog DOM-a"))


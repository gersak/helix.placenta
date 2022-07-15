(ns helix.spring1-stories
   (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/Hover Triggered Animations"
       :component s/BiloKak})

(defn ^:export HoverAnim []
  ($ s/change-hover-color))
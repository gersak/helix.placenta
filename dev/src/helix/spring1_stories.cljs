(ns helix.spring1-stories
   (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/Hover Triggered Animations"
       :component s/Hello})

(defn ^:export HoverAnim []
  ($ s/change-hover-color))

(defn ^:export HoverRotateImg []
  ($ s/hover-rotate-img))

#_(defn ^:export ImgZoomOut []
  ($ s/ImgZoomOut))

#_(defn ^:export TextExpand []
  ($ s/ExpandButton))

#_(defn ^:export ScaleDownSquare[]
  ($ s/ScaleDownSquare))
(ns helix.spring1-stories
   (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/Hover Triggered Animations"
       :component s/change-hover-color})

(defn ^:export HoverAnim []
  ($ s/change-hover-color))

(defn ^:export HoverRotateImg []
  ($ s/hover-rotate-img))

#_(defn ^:export ImgZoomOut []
  ($ s/ImgZoomOut))

(defn ^:export ImageTextHover []
  ($ s/image-text-hover))

(defn ^:export RaiseIconAnim []
  ($ s/raise-icon-anim))

(defn ^:export IconMovement []
  ($ s/icon-movement))

(defn ^:export HoverSocial []
  ($ s/hover-social))

(defn ^:export PopUpInfo []
  ($ s/pop-up-info))

(defn ^:export StandOutCardApp []
  ($ s/stand-out-card))

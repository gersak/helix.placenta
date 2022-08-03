(ns helix.spring-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.konva :as konva]
   [helix.spring.basic :as s]))


(def ^:export default
  #js {:title "Spring components/SelfTriggeredAnimations"
       :component s/dissapear-reapear})

(defn ^:export ReappearingEmoji []
  ($ s/dissapear-reapear))

(defn ^:export ChangeBgColor []
  ($ s/change-bg-color))

(defn ^:export TextTopAnim []
  ($ s/text-top-anim))

(defn ^:export ImgIterate []
  ($ s/img-iterate))

(defn ^:export Konva []
  (konva/Stage
    {:width 200 :height 200}
    (konva/Layer
      (konva/Text
        {:text "Hello from KONVA"}))))
(ns helix.spring3-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/SelfTriggeredAnimations/MovingSquares"
       :component s/Hello})

(defn ^:export DiagonalMovement []
  ($ s/AmoTamo))

(defn ^:export Rotation []
  ($ s/LoadingRotation))
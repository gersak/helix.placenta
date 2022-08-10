(ns helix.konva-stories
  (:require
   [helix.core :refer [$]]
   [helix.konva.main :as kon]))


(def ^:export default
  #js {:title "Konva components/Testing"})

(defn ^:export BasicShapes []
  ($ kon/BasicShape))

(defn ^:export CustomShape []
  ($ kon/CustomShape))

(defn ^:export MovableStars []
  ($ kon/MovableStars))

(defn ^:export ExportImage []
  ($ kon/ExportImage))

(defn ^:export RenderImage []
  ($ kon/Images))

(defn ^:export DropIntoCanvas []
  ($ kon/DropIntoCanvas))

(defn ^:export ObjectTransformer []
  ($ kon/RenderTransform))

(defn ^:export UndoRedo []
  ($ kon/UndoRedo))

(defn ^:export ImageTransformer []
  ($ kon/ImageTransformer))

(defn ^:export SliderTesting []
  ($ kon/SliderTesting))

(defn ^:export AvatarEditor []
  ($ kon/avatar-editor))


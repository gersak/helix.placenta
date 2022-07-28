(ns helix.spring4-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/OnClick Triggered Animations"
       :component s/Hello})

(defn ^:export WavyTextAppear []
  ($ s/Wave))

(defn ^:export FlipSides []
  ($ s/flip-example))

(defn ^:export Charging []
  ($ s/Reload))

(defn ^:export TextFadeOut []
  ($ s/ClickFadeOut))

(defn ^:export sideWindowSlide []
  ($ s/sideWindowSlide))

(defn ^:export OptionSelect []
  ($ s/option-select))

(defn ^:export ExpandButton []
  ($ s/expand-button))

(defn ^:export AnimatedModal []
  ($ s/animated-modal))

(defn ^:export ToggleAnimTrail []
  ($ s/toggle-anim-trail))

(defn ^:export ItemsFadeout []
  ($ s/items-fadeout))

#_(defn ^:export SlidingLetters []
  ($ s/sliding-letters))
(ns helix.spring4-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))

(def ^:export default
  #js {:title "Spring components/OnClick Triggered Animations"
       :component s/BiloKak})

(defn ^:export WavyTextAppear []
  ($ s/Wave))

(defn ^:export FlipSides []
  ($ s/flip-example))

(defn ^:export Charging []
  ($ s/Reload))

(defn ^:export TextFadeOut []
  ($ s/ClickFadeOut))
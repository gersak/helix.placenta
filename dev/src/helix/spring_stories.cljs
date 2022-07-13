(ns helix.spring-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.spring.basic :as s]))


(def ^:export default
  #js {:title "Spring components"
       :component s/Hello})


(defn ^:export BiloKak []
  ($ s/Hello))

(defn ^:export ByBy []
  (d/div "Pozdrav iz klasnicnog DOM-a"))

(defn ^:export ClickFadeOut []
  ($ s/ClickFadeOut))

(defn ^:export AmoTamo []
  ($ s/AmoTamo))

(defn ^:export Reload[]
  ($ s/Reload))

(defn ^:export Flip[]
  ($ s/Flip))

 ;; ())
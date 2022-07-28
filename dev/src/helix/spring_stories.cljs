(ns helix.spring-stories
  (:require
   [helix.core :refer [$]]
   [helix.dom :as d]
   [helix.konva :as konva]
   [helix.spring.basic :as s]))


(def ^:export default
  #js {:title "Spring components/SelfTriggeredAnimations"
       :component s/Hello})



;(defn ^:export BiloKak []
;  ($ s/Hello))
;
;(defn ^:export ByBy []
;  (d/div "Pozdrav iz klasnicnog DOM-a"))

;(defn ^:export ClickFadeOut []
; ($ s/ClickFadeOut))

;(defn ^:export AmoTamo []
;  ($ s/AmoTamo))
;

;(defn ^:export Reload[] 
; ($ s/Reload))

;(defn ^:export Flip[]
;  ($ s/flip-example))

;(defn ^:export TrackCursor[]
;  ($ s/TrackCursor))


;(defn ^:export Wave[]
;  ($ s/Wave))

(defn ^:export ReappearingEmoji []
  ($ s/DissapearReapear))

;(defn ^:export LoadingRotation[]
;  ($ s/LoadingRotation))


(defn ^:export ChangeBgColor []
  ($ s/change-bg-color))

(defn ^:export TextTopAnim []
  ($ s/text-top-anim))
;(defn ^:export HoverAnim []
;  ($ s/change-hover-color))
 ;; ())



(defn ^:export Konva []
  (konva/Stage
    {:width 200 :height 200}
    (konva/Layer
      (konva/Text
        {:text "Hello from KONVA"}))))
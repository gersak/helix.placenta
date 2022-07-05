(ns helix.spring.basic
  (:require
   ["react-dom" :as rdom]
   [helix.core :refer [defnc $]]
   [helix.dom :as d]
   [helix.spring :as spring]
   [helix.styled-components :refer [defstyled]]))


(defnc Yellow [{:keys [className]}]
  (let [[style] (spring/use-spring
                 ;; Props
                 {:from {:opacity 0.7
                         :color "red"}
                  :loop {:reverse true}
                  :to {:opacity 1
                       :color "blue"}
                  ;; Konfiguracija
                  :config {:duration 15000}})]
    (spring/div {:className className :style style} "Bok marko")))


(defstyled small Yellow
  {:font-size 10})

(defstyled medium Yellow
  {:font-size 14})

(defstyled large Yellow
  {:font-size 20})

(defnc Hello []
  (d/div
   (d/div "bi biiip")
   ($ small)
   ($ medium)
   ($ large)))

(defn ^:dev/after-load init []
  (rdom/render
   ($ Hello)
   (.getElementById js/document "app")))

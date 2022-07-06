(ns helix.spring.basic
  (:require
    ["react-dom" :as rdom]
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    [helix.spring :as spring]
    [helix.styled-components :refer [defstyled]]))


(defnc Yellow []
  (let [[style] (spring/use-spring
                  {:from  {:opacity 0}
                   :to {:opacity 1}})]
    (spring/div {:style style} "DIDID")))

(defstyled yellow Yellow
  {:color "yellow"})

(defnc Hello []
  (d/div
    (d/div "bi biiip")
    ($ Yellow)))

(defn ^:dev/after-load init []
  (rdom/render
    ($ Hello)
    (.getElementById js/document "app")))

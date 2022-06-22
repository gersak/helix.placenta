(ns helix.spring.basic
  (:require
    [helix.core :refer [defnc $]]
    [helix.dom :as d]
    [helix.spring :as spring]
    ["react-dom" :as rdom]))


(defnc Yellow []
  (let [[style] (spring/use-spring
                  {:from  {:opacity 0}
                   :to {:opacity 1}})]
    (spring/div {:style style} "DIDID")))

(defnc Hello []
  (d/div
    (d/div "bi biiip")
    ($ Yellow)))

(defn ^:dev/after-load init []
  (rdom/render
    ($ Hello)
    (.getElementById js/document "app")))

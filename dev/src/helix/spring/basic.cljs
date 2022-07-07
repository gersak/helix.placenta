(ns helix.spring.basic
  (:require
   ["react-dom" :as rdom]
   ["react-spring" :refer [useSpring]]
   [clojure.core.async :as async]
   [cljs-bean.core :refer [->js ->clj]] ;;https://github.com/mfikes/cljs-bean/blob/master/src/cljs_bean/core.cljs - ugrađeno nešto - mi to tu koristimo
   [helix.core :refer [defnc $ <>]]
   [helix.dom :as d]
   [helix.spring :as spring]
   [helix.hooks :as hooks]
   [helix.styled-components :refer [defstyled]]
   ["react-use-measure" :default useMeasure]))


(defnc Yellow [{:keys [className ime prezime]}]
  (let [[style] (spring/use-spring
                 ;; Props
                 ;;ovo sve je argument koji se predaje (initial u spring.cljc)
                 {:from {:opacity 0.7
                         :color "red"}
                  :loop {:reverse true}
                  :to {:opacity 1
                       :color "blue"}
                  :config {:duration 10000}})
                  ;;skroz do tud je taj argument initial
        ]
    (<>
     (spring/div
      {:className className :style style}
      (str "Bok " ime " " prezime ". className je: " className))
     (spring/h1 {:className className :style style} className)
     (spring/h3 {:className className :style style} className))))


(defstyled small Yellow
  {:font-size 10})

(defstyled medium Yellow
  {:font-size 14})

(defstyled large Yellow
  {:font-size 20})


(defnc Hello []
  (d/div
   ($ Yellow {:ime "Robi" :prezime "G" :className "etotigana"})
   ($ small {:ime "Noa" :prezime "A"})
   ($ medium {:ime "Marko" :prezime "B"})
   ($ large {:ime "Leon" :prezime "C"})
   ($ large {:ime "Novi LARGE" :prezime "NL"})))


(defstyled container "div"
  {:display "flex"
   :align-items "center"
   :justify-content "center"
   :height "100%"
   ".text" {:font-weight 600
            :font-size "8em"
            :will-change "opacity"}})


(defnc ClickFadeOut [{:keys [className]}]
  (let [[state toggle] (hooks/use-state true)
        style (useSpring (->js {:from {:x 0}
                                :x (if state 1 0)
                                :config {:duration 1000}}))]
    ($ container
       {:className className
        :onClick (fn [] (toggle not))}
       (spring/div
        {:className "text"
         :style {:opacity (.to (.-x style) (->js {:range [0 1] :output [0.3 1]}))
                 :scale (.to (.-x style) (->js {:range [0, 0.25, 0.35, 0.45, 0.55, 0.65, 0.75, 1]
                                                :output [1, 0.97, 0.9, 1.1, 0.9, 1.1, 1.03, 1]}))}}
        "click"))))


(defstyled relContainer "div"
  {:display "flex"
   :align-items "center"
   :justify-content "center"
   :height "100%"})

(defstyled relMain "div"
  {:position "relative"
   :width "200px"
   :height "50px"
   :cursor "pointer"
   :border-radius "5px"
   :border "2px solid #272727"
   :overflow "hidden"
   ".fill" {:position "absolute"
            :top 0
            :left 0
            :width "100%"
            :height "100%"
            :background "hotpink"}
   ".content" {:position "absolute"
               :top 0
               :left 0
               :width "100%"
               :height "100%"
               :display "flex"
               :align-items "center"
               :justify-content "center"
               :color "#272727"}})

(defnc Reload []
  (let [[open toggle] (hooks/use-state false)
        [ref measure] (useMeasure)
        [props] (spring/use-spring
                 {:width (if open (.-width measure)  0)
                  :config {:duration 5000}})]
    ($ relContainer
       ($ relMain
          {;:className className
           :ref ref
           :onClick (fn [] (toggle not))}
          (spring/div
           {:className "fill"
            :style props})
          (spring/div
           {:className "content"}
           (.to ^js (.-width props) (fn [x] (.toFixed x 0))))))))


(defnc AmoTamo []
  (let [[style api] (spring/use-spring {:from {:x 0 :y 0}})]
    ;;initial vrijednost koja se salje funkciji use-spring
    (hooks/use-effect
     ;;prima 2 argumenta - prvi je funkcija, drugi je polje???
     :always

     (api :start
          {:x 100
           :y 100
           :opacity 1
           :loop {:reverse true}})
     #_(async/go (async/<! (async/timeout 2000)) (api :set {:x 50 :y 50}) (println "setao sam ga"))
     (async/go (async/<! (async/timeout 5000)) (api :stop) (println "stopaosam ga")))
    ;;konverzija ovog dijela koda u js kako bi js mogao to citati

    (d/div
     {:style {:width "100%"
              :height "100%"
              :display "flex"
              :align-items "center"
              :justify-content "center"}}
     ;;spring/div se nalazi unutar ovog d/div-a 
     (spring/div
      {:style
       (merge
        (->clj style)
        {:width 80
         :height 80
         :backgroundColor "#46e891"
         :borderRadius 16})
       ;merga se mapa style (ono sto se dobije kao povratna vrijednost funkcije spring/use-spring) s mapom {:width 80 ...} =>
       ;to je sve stil ovog spring/div-a - pogledaj 85. liniju - {:style {:width "100%" ...}}
       ;ono sto se dobije je {:style (91.linija) {mergana_mapa}} 
       }))))


;(defn ^:dev/after-load init []
;  (rdom/render
;   ($ Hello)
;   (.getElementById js/document "app")))
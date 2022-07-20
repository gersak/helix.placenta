(ns helix.spring.basic
  (:require
   ["react-spring" :refer [useSpring useTransition config useSprings useTrail animated interpolate]]
   [clojure.core.async :as async]
   [cljs-bean.core :refer [->js]] ;;https://github.com/mfikes/cljs-bean/blob/master/src/cljs_bean/core.cljs - ugrađeno nešto - mi to tu koristimo
   [helix.core :refer [defnc $ <>]]
   [helix.dom :as d]
   [helix.spring :as spring]
   [helix.hooks :as hooks]
   [helix.styled-components :refer [defstyled]]
   ["react-use-measure" :default useMeasure]
   ;["@material-ui/icons/ExpandMore" :refer ExpandButton]
   ;[helix.spring.Accordion :as acc]
   ))


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


;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;DOCUMENTATION
(defstyled body "div"
  {:height "100%"
   :width "100%"

   ".documentationContainer" {:display "flex"
                              :width "100%"
                              :justify-content "center"
                              :padding-bottom "5%"}
   ".documentationInfo" {:display "block"
                         :width "80%"
                         :border "2px solid black"
                         :border-radius "20px"
                         ;:background-color "#DCDCDC"
                         }
   ".titleContainer" {:display "flex"
                      :justify-content "center"
                      :align-items "centers"}
   ;".titleContainer" {:display "block"
    ;                  :padding-left "5%"}

   ".descriptionContainer" {:display "flex"
                            :justify-content "center"
                            :padding "0% 2% 0% 2%"
                            :font-family "sans-serif"}
   ".horizontalLine" {:border "0.5px solid grey"
                      :width "90%"}


   ".categoriesContainer" {:display "flex"
                           :justify-content "center"
                           :align-items "center"}
   ".categoryHeaderContainer" {:width "80%"
                               :padding-left "5%"}})

(defn documentation
  [title description]
  ($ body
     ;{:className :className}
     (d/div
      {:className "documentationContainer"}
      (d/div
       {:className "documentationInfo"}
       (d/div
        {:className "titleContainer"}
        (d/h1
         title))
       (d/div
        (d/hr
         {:className "horizontalLine"}))

       (d/div
        {:className "categoriesContainer"}
        (d/h1
         {:className "categoryHeaderContainer"}
         "Overview")
        (d/div
         {:className "descriptionContainer"}
         #_(d/h3
            "Overview")
         (d/p
          description)))))
     (d/div
      {:className "categoriesContainer"}
      (d/div
       {:className "categoryHeaderContainer"}
       (d/h1
        "Example")))))

;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________
;__________________________________________________________________________________________________________________________________________________________________________________

#_(defn gettingAllProps
    "This function will access all of the properties in object that is passed to the function as parameter. All of the properties will be saved to 
   a vector."
    [obj]
    (keys obj)
  ;vraca sve keyeve u predanom objektu
    )
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defstyled container "div"
  {:display "flex"
   :align-items "center"
   :justify-content "center"
   :height "100vh"
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
                 :scale (.to
                         (.-x style)
                         (->js {:range [0, 0.25, 0.35, 0.45, 0.55, 0.65, 0.75, 1]
                                :output [1, 0.97, 0.9, 1.1, 0.9, 1.1, 1.03, 1]}))}}
        "click"))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

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
                  :config {:duration 2500}})]
    (println "props rastavljen na keyeve: " (.keys js/Object props))


    #_(documentation "Reload" "This is reload animation")

    (d/div
     (documentation "Charging"
                    "The following example is triggered on click. When clicked on, battery charging-like animation will get displayed
                     What is happening under the hood is that div colored in pink will get transitioned along x-axis ")
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
            (.to ^js (.-width props) (fn [x] (.toFixed x 0)))))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defstyled flipContainer "div"
  {:display "flex"
   :align-items "center"
   :height "100vh"
   :justify-content "center"})



(defnc Flip [{:keys [className]}]
  (println "ClassName: " className)
  (let [[flipped set] (hooks/use-state false)
        satisfiedCond (if flipped 180 0)
        [styles] (spring/use-spring
                  {:opacity (if flipped 1 0)
                   :transform (str "perspective(600px) rotateX(" satisfiedCond "deg)")
                   :config {:mass 5 :tension 500 :friction 80}})]
    ($ flipContainer
       {:onClick (fn [] (set not))}
       (spring/div
        {:className className
         :style {:background-image "url(https://images.unsplash.com/photo-1540206395-68808572332f?ixlib=rb-1.2.1&w=1181&q=80&auto=format&fit=crop)"
                 :opacity (.to ^js (.-opacity styles) (fn [o] (- 1 o))) ;:transform
                 :transform (.-transform styles)}})
       (spring/div
        {:className className
         :style {:transform (.-transform styles)
                 :opacity (.-opacity styles)
                 :background-image "url(https://images.unsplash.com/photo-1544511916-0148ccdeb877?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1901&q=80i&auto=format&fit=crop)"
                 :rotateX "180deg"}}))))

(defstyled flip-example Flip
  {:position "absolute"
   :max-width "500px"
   :max-height "500px"
   :width "350px"
   :height "200px"
   :cursor "pointer"
   :will-change "opacity, transform"
   :background-size "cover"})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc AmoTamo []
  (let [[style api] (spring/use-spring
                     (fn []
                       {:from
                        {:x 0 :y 0
                         :width 80
                         :height 80
                         :backgroundColor "#46e891"
                         :borderRadius 16}}))]
    (println style)
    (println "API: " api)
    ;;initial vrijednost koja se salje funkciji use-spring
    (hooks/use-effect
     ;;prima 2 argumenta - prvi je funkcija, drugi je polje???
     :always

     (api :start
          {:x 100
           :y 100
           :opacity 1
           :loop {:reverse true}})
     (async/go (async/<! (async/timeout 2000)) (api :set {:x 50 :y 50}) (println "setao sam ga"))
     (async/go (async/<! (async/timeout 5000)) (api :stop) (println "stopaosam ga")))
    ;;konverzija ovog dijela koda u js kako bi js mogao to citati

    (d/div
     (documentation "Diagonal Movement"
                    "This square keeps moving diagonaly")
     (d/div
      {:style {:width "100%"
               :height "100%"
               :display "flex"
               :align-items "center"
               :justify-content "center"}}
     ;;spring/div se nalazi unutar ovog d/div-a 
      (spring/div
       {:style style
       ;merga se mapa style (ono sto se dobije kao povratna vrijednost funkcije spring/use-spring) s mapom {:width 80 ...} =>
       ;to je sve stil ovog spring/div-a - pogledaj 85. liniju - {:style {:width "100%" ...}}
       ;ono sto se dobije je {:style (91.linija) {mergana_mapa}} 
        })))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;(defstyled trackContainer "div"
;  {:position "absolute"
;   :will-change "transform"
;   :border-radius "50%"
;   :background "lightcoral"
;   :box-shadow "10px 10px 5px 0px rgba(0 0 0 0.75)"
;   :opacity 0.6})
;(defn TrackCursor []
;  (let [fast {:tension 1200 :friction 40}
;       slow {:mass 10 :tenstion 200 :friction 50}
;       trans (fn [x y] (str "translate3d(" x "px," y "px,0) translate3d(-50%,-50%,0)"))
;        [trail, api] (useTrail (fn [3 i] (:x 0 :y 0
;                                 :config (if (= i 0) fast slow))
;                                 ))
;        [ref {left top}] useMeasure
;        handleMouseMove (fn [e] (.start api {:x (- (.-clientX e) left) :y (- (.-clientY e) top)}))
;        ]
;    ))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defstyled waveContainer "div"
  {:display "flex"
   :align-items "center"
   :height "100%"
   :width "100%"
   :cursor "pointer"
   :justify-content "center"
   :background-color "#16181d"
   "svg" {:width 500}})

(def AnimFeTurbulence (spring/animated "feTurbulence"))
(def AnimFeDisplacementMap (spring/animated "feDisplacementMap"))

(defnc Wave []
  (let [[open toggle] (hooks/use-state false)
        [obj] (spring/use-spring {:reverse open
                                  :from
                                  {:opacity 0
                                   :transform "scale(0.9)"
                                   :scale 10
                                   :freq "0.0175, 0.0"}
                                  :to
                                  {:opacity 1
                                   :transform "scale(1)"
                                   :scale 150
                                   :freq "0.0, 0.0"}
                                  :config {:duration 3000}})
        [freq transform scale opacity] [(.-freq obj)
                                        (.-transform obj)
                                        (.-scale obj)
                                        (.-opacity obj)]]
    (println "FREQ" freq)
    (println "SCALE" scale)
    (println "OPACITY" opacity)
    (println "ANIMFETURBULENCE" (->js AnimFeTurbulence))

    ($ waveContainer
       {:onClick (fn [] (toggle not))}
       (spring/svg
        {:style {:transform transform
                 :opacity opacity}
         :viewBox "0 0 1278 446"}
        (d/defs
          ($ "filter" {:id "water"}
             ($ AnimFeTurbulence
                {:type "fractalNoise" :baseFrequency freq :numOctaves "1.5"
                 :result "TURB" :seed "8"})
             ($ AnimFeDisplacementMap
                {:xChannelSelector "R"
                 :yChannelSelector "G"
                 :in "SourceGraphic"
                 :in2 "TURB"
                 :result "DISP"
                 :scale scale})))
        (d/g
         {:filter "url(#water)"}
         (spring/path
          {:d "M179.53551,113.735463 C239.115435,113.735463 292.796357,157.388081 292.796357,245.873118 L292.796357,415.764388 L198.412318,415.764388 L198.412318,255.311521 C198.412318,208.119502 171.866807,198.681098 151.220299,198.681098 C131.753591,198.681098 94.5898754,211.658904 94.5898754,264.749925 L94.5898754,415.764388 L0.205836552,415.764388 L0.205836552,0.474616471 L94.5898754,0.474616471 L94.5898754,151.489079 C114.646484,127.893069 145.321296,113.735463 179.53551,113.735463 Z M627.269795,269.469127 C627.269795,275.95803 626.679895,285.396434 626.089994,293.065137 L424.344111,293.065137 C432.012815,320.790448 457.378525,340.257156 496.901841,340.257156 C520.497851,340.257156 554.712065,333.768254 582.437376,322.560149 L608.392987,397.47748 C608.392987,397.47748 567.09997,425.202792 494.54224,425.202792 C376.562192,425.202792 325.240871,354.414762 325.240871,269.469127 C325.240871,183.343692 377.152092,113.735463 480.974535,113.735463 C574.178773,113.735463 627.269795,171.545687 627.269795,269.469127 Z M424.344111,236.434714 L528.166554,236.434714 C528.166554,216.378105 511.649347,189.242694 476.255333,189.242694 C446.17042,189.242694 424.344111,216.378105 424.344111,236.434714 Z M659.714308,0.474616471 L754.098347,0.474616471 L754.098347,415.764388 L659.714308,415.764388 L659.714308,0.474616471 Z M810.13887,0.474616471 L904.522909,0.474616471 L904.522909,415.764388 L810.13887,415.764388 L810.13887,0.474616471 Z M1097.42029,113.735463 C1191.80433,113.735463 1257.87315,183.343692 1257.87315,269.469127 C1257.87315,355.594563 1192.98413,425.202792 1097.42029,425.202792 C997.727148,425.202792 936.967423,355.594563 936.967423,269.469127 C936.967423,183.343692 996.547347,113.735463 1097.42029,113.735463 Z M1097.42029,340.257156 C1133.9941,340.257156 1163.48912,308.402543 1163.48912,269.469127 C1163.48912,230.535711 1133.9941,198.681098 1097.42029,198.681098 C1060.84647,198.681098 1031.35146,230.535711 1031.35146,269.469127 C1031.35146,308.402543 1060.84647,340.257156 1097.42029,340.257156 Z"
           :fill "lightblue"}))))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defnc LoadingRotation []
  (let [[styles api] (spring/use-spring
                      (fn []
                        {:from {:rotateZ 0}
                         :width 80
                         :height 80
                         :backgroundColor "#46e891"
                         :borderRadius 20
                         :config {:duration 1500}
                         :background-image "url(https://www.google.com/search?q=loading+symbol&sxsrf=ALiCzsa6FT-ThEwBAJwgy559Oh8wFYP8Ow:1657287674791&source=lnms&tbm=isch&sa=X&ved=2ahUKEwiPyZXZten4AhXTQfEDHVFVAwoQ_AUoAXoECAEQAw&biw=1536&bih=722&dpr=1.25#imgrc=INVO_XMDfWFhSM)"}))]
    (hooks/use-effect
     :always
     (api :start
          {:loop true
           :to {:rotateZ 180}}))

    (d/div
     (documentation "Rotation"
                    "This square keeps rotating")
     (d/div
      {:style {:width "100%"
               :height "100%"
               :display "flex"
               :align-items "center"
               :justify-content "center"}}
      (spring/div
       {:style styles})))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc DissapearReapear []
  (let [[show set] (hooks/use-state false)
        transitions (useTransition
                     show
                     #js {:from  #js {:opacity 0}
                          :enter #js {:opacity 1}
                          :leave #js {:opacity 0}
                          :reverse show
                          :delay 500
                          :config (.-molasses config)
                          :onRest (fn []
                                    (println "Mijenjam se")
                                    (set not))})]
    (transitions
     (fn [styles item]
       (spring/div {:style styles} "✌️")))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc ChangeBgColor [{:keys [className]}]
  (let [[bg] (spring/use-spring
              (fn []
                {:from {:background "var(--from, blue)"}
                 :to {:background "var(--to, lightblue)"}
                 :config (.-molasses config)
                 :loop {:reverse true}}))]
    (d/div
     (documentation "ChangeBgColor"
                    "This square keeps changing color")
     (d/div
      {:className className}
      (spring/div
       {:className "block"
        :style {:background (.-background bg)}})))))


(defstyled change-bg-color ChangeBgColor
  {:display "flex"
   :align-items "center"
   :height "100%"
   :justify-content "center"
   ".block" {:width "20vw"
             :height "20vw"
             :display "block"
             :border-radius 8}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;https://codesandbox.io/s/animate-auto-height-6trvh?file=/src/accordion.js
(defnc ExpandButton [{:keys [className]}]
  (let [defaultHeight "100px"
        [open toggle] (hooks/use-state false)
        [contentHeight setContentHeight] (hooks/use-state defaultHeight)
        [ref helperHeight] (useMeasure)
        [expand api] (spring/use-spring
                      {:config {:friction 10}
                       :height (if open (str contentHeight "px") defaultHeight)})
        [spinHelper] (spring/use-spring
                      {:config {:friction 10}
                       :transform (if open "rotate(180deg)" "rotate(0deg)")})
        height (.-height helperHeight)
          ;expand (.-expand expandHelper)
          ;^js spin (.-spin spinHelper)
        ]

    ;(.log js/console "HEIGHT: " height)
    ;(.log js/console "SETCONTENTHEIGHT FUNCTION: " setContentHeight)
    ;(.log js/console "SETCONTENTHEIGHT poziv fje: " (setContentHeight height))
    ;(.log js/console "SPIN HELPER: " spinHelper)
    ;(println "SPINHELPER CONFIG " (.-config spinHelper))
    ;(println "SPIN HELPER " spinHelper)
    ;(println "SETCONTENTHEIGHT: " setContentHeight)
    ;(println fn? setContentHeight)
    ;(println "TIP: " type setContentHeight)
    ;(println "TF SE DOGADA: " (hooks/use-state defaultHeight))

    (hooks/use-effect
     [height]
     ;(println "USAO SAM")
     (setContentHeight height)
     (.addEventListener js/window "resize" (setContentHeight height))
     (.removeEventListener js/window "resize" (setContentHeight height)))

    (d/div
     {:className className}
     (spring/div
      {:className "accordion"
       :style expand}
      (d/div
       {:className "content"
        :ref (.-ref ref)}
       (d/p
        "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Morbi at
            augue laoreet, eleifend turpis a, tincidunt velit. Curabitur vitae
            felis sit amet arcu blandit pellentesque quis vitae odio. Aenean
            pharetra eu felis non suscipit. Etiam fermentum enim sit amet magna
            scelerisque, eu mattis ligula tristique. Aliquam sed cursus odio,
            sit amet condimentum eros. Proin molestie commodo urna, eget
            accumsan tellus laoreet ut. Morbi id est eu lorem tempor cursus.
            Aenean vitae ultrices sem. Phasellus venenatis velit in ultrices
            interdum. Cras semper, justo a maximus iaculis, nisl metus luctus
            nisl, ac sodales odio mauris et ante. Donec ipsum est, auctor a
            lorem ac, rutrum elementum magna.")))
     (spring/button
      {:className "expand"
       :onClick (fn [] (toggle not))
       :style spinHelper}
      "^"))))

(defstyled expand-button ExpandButton
  {:max-width "500px"
   ;:height "100vh"
   ;:display "flex"
   ;:align-items "center"
   ;:justify-content "center"
   ".expand" {:background "#2bced6"
              :border "none"
              :height "50px"
              :width "50px"
              :border-radius "100px"
              :margin-top "-25px"
              :display "block"
              :float "right"
              :margin-right "30px"
              :color "#393e46"}

   ".content" {:padding "15px 30px"}
   ".wrapper" {:max-width "500px"}
   ".accordion" {:background "#eeeeee"
                 :border-radius "30px"
                 :overflow "hidden"
                 :position "relative"
                 :padding-bottom "30px"}

   ".accordion::after" {:content ""
                        :height "50px"
                        :width "100%"
                        :background "linear-gradient(
                                     to bottom,
                                     rgba(238, 238, 238, 0) 0%,
                                     rgba(238, 238, 238, 0.85) 100%)"
                        :position "absolute"
                        :bottom 0}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc HoverAnim [{:keys [className]}]
  (let [[helperHover set] (spring/use-spring (fn []
                                               {:y 100
                                                :color "#fff"}))
        color (.-color helperHover)
        ^js y (.-y helperHover)]

    ;(println "helperHover: " helperHover)
    ;(println "helper rastavljen na kljuceve: " (.keys js/Object helperHover))
    ;vratit će y i color jer mu je to poslano s funkcijom use-spring
    ;(println "y: " y)
    (println "color: " color)
    (println "color rastavljen na kljuceve: " (.keys js/Object color))
    (println "color rastavljen na valueve: " (.values js/Object color))
    ;(println "VRIJEDNOST VEZANA UZ KEY: " (get color .-color))
    (println "SET TYPE: " (type set))

    (d/div
     {:style {:display "flex"
              :justify-content "center"
              :align-items "center"}}

     (d/button
      {:className className
       :onMouseEnter (fn []
                       (println "USAO SAM U ENTER")
                       (println "VRIJEDNOST COLORA JE:" (.-color color))
                       (println "YON" (.-y y))
                       (set
                        {:y 0
                         :color "#000"}))
       :onMouseLeave (fn []
                       (println "IZASAO")
                       (set
                        {:y 100
                         :color "#fff"}))}
      (spring/span

       {:className "spanHover"
        :style {:color color}}
       "react spring is way easy")
      (spring/div

       {:className "glance"
        :style {:transform (.interpolate y (fn [v] (str "translateY(" v "%)")))}})))))

(defstyled change-hover-color HoverAnim
  {:display "inline-block"
   :padding "1em 2em"
   :background "#5dc77a"
   :color "#fff"
   :text-decoration "none"
   :font-size "1.5em"
   :font-weight "700"
   :border "none"
   :cursor "pointer"
   :position "relative"
   :overflow "hidden"
   :margin-bottom "2em"

   ".glance" {:background "rgba(0, 0, 0, 0.25)"
              :width "100%"
              :height "100%"
              :position "absolute"
              :top 0
              :left 0
              :z-index 1
              :transform "translateY(50%)"}
   ".spanHover" {:z-index 2
                 :position "relative"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;https://codesandbox.io/s/732j6q4620?file=/src/index.js
#_(defnc ImgZoomOut [{:keys [image background]}]
                      ;uzima kljuceve iz mape koja mu je predana 
                      ;vrijednosti kljuceva predaje simbolima image i backgorund
    (let [[open setOpen] (hooks/use-state false)
          [helperImgZoom] (spring/use-spring {:f (if open 0 1)
                                              :r (if open -3 3)})
          f (.-f helperImgZoom)
          r (.-r helperImgZoom)
          cards (useSprings 5 (. [0 1 2 3 4] map (fn [i]
                                                   {:opacity (+ 0.2 (/ i 5))
                                                    :z (if open (* (/ i 5) 80) 0)})))]
      ($ imgZoomOutBody
         (d/div
          {:className "imageZoomOutContainer"}
          :onMouseEnter (fn []
                          (setOpen true))
          :onMouseLeave (fn []
                          (setOpen false))
          (. cards map (fn
                         [[z opacity] index]
                         (spring/div
                          :style {:opacity opacity
                                  :background background
                                  :transform (interpolate (fn
                                                            [z
                                                             (. f interpolate #js [0 0.2 0.6 1] #js [0 index index 0])
                                                             r]
                                                            (fn [z f r]
                                                              (str "translate3d(0,0," z "px) rotateX(" (* f r) "deg)"))))}
                          (when (= index 4)
                            (spring/img
                             :style {:transform (. f interpolate [0 1] ["scale(0.7)" "scale(1)"])}
                             :src image))))))
         (d/div
          {:className "imgZoomOutMain"}
          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/9qWx-1.png"
                         :background "#52649e"})
          ;salje se mapa koja ima keyeve image i background react elementu ImgZoomOut


          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/T0hA-3.png"
                         :background "#f7f295"})

          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/QoXU-2.png"
                         :background "#ee7074"})

          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/QoXU-2.png"
                         :background "#ee7074"})

          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/9qWx-1.png"
                         :background "#52649e"})

          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/T0hA-3.png"
                         :background "#f7f295"})

          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/T0hA-3.png"
                         :background "#f7f295"})
          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/QoXU-2.png"
                         :background "#ee7074"})
          ($ ImgZoomOut {:image "https://uploads.codesandbox.io/uploads/user/b3e56831-8b98-4fee-b941-0e27f39883ab/9qWx-1.png"
                         :background "#52649e"})))))

#_(defstyled imgZoomOutBody "div"
    {:overflow "hidden"
     :margin 0
     :padding 0
     :width "100%"
     :height "100%"
     :display "flex"
     :align-items "center"
     :justify-content "center"
     :perspective "350px"
     :perspective-origin "50% 50%"
     ".imageZoomOutContainer" {:width "150px"
                               :height "150px"
                               :margin "15px"
                               :transform-style "flat"}
     ".imgZoomOutMain" {:display "grid"
                        :grid-template-columns "1fr 1fr 1fr"
                        :grid-template-rows "1fr 1fr"}
     ".imageZoomOutContainer > div" {:position "absolute"
                                     :width "150px"
                                     :height "150px"
                                     :background "grey"
                                     :display "flex"
                                     :align-items "center"
                                     :justify-content "center"
                                     :overflow "hidden"
                                     :will-change "transform"
                                     :cursor "pointer"}})




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;https://codesandbox.io/s/react-accordion-by-saleh-m-dm5sp?file=/src/App.js
#_(defnc OptionSelect []
    (let [titleAnimation (spring/use-spring {:from {:transform "translateY(-30px)"}
                                             :to {:transform "translateY(15px)"}
                                             :config {:mass 3
                                                      :tension 500
                                                      :friction 25}})]
      (d/div
       (spring/h1
        {:style titleAnimation} "React Accordion")
       (d/div
        (acc/Accordion
         {:title "Item 1 - Lorem ipsum dolor sit amet"
          :text "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"})
        (acc/Accordion
         {:title "Item 2 - Lorem ipsum dolor sit amet"
          :text "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"})))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
#_(defnc StringFadeOut []
    (let [items ["Item1" "Item2" "Item3" "Item4"]
          config {:mass 5
                  :tension 2000
                  :friction 200}
          [toggle set] (hooks/use-state true)
          trail (useTrail (count items) {:config config
                                         :opacity (if toggle 1 0)
                                         :x (if toggle 0 20)
                                         :height (if toggle 80 0)
                                         :from {:opacity 0
                                                :x 20
                                                :height 0}})
          height (.-height trail)]
      (d/div
       {:onClick (fn []
                   (set not))}
       (d/div
        {. trail map (fn [[x height]])}
        (spring/div
         (def key))))))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

#_(defnc ScaleDownSquare []
    (d/div
     {:style {;:text-align "center"
              :width "100vw"
              :height "100vh"
              :display "flex"
              :align-items "center"
              :justify-content "center"
              :background "rgba(0, 85, 255, 1)"
              :margin 0
              :padding 0
              :perspective "1000px"}}
     (spring/div
      {:style {:height 150
               :width 150
               :border-radius 30
               :background "#fff"}
       :hover {:transform "scale(0.75)"}})))

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defstyled containerSlide "div"
  {:font-family "sans-serif"
   :background-color "#e6c34a"
   :height "100vh"
   :padding-left "15px"
   :padding-top "15px"
   ".openButton" {:background-color "#719c70"
                  :color "white"
                  :padding "10px 15px"
                  :font-size "20px"
                  :border-radius "8px"
                  :border "none"
                  :cursor "pointer"}
   ".openButton:hover" {:border "1px solid black"}
   ".drawer" {:display "flex"
              :justify-content "center"
              :width "100%"
              :color "white"
              :font-size "25px"
              :padding-top "20px"}})

(defnc Drawer [{:keys [show children] :as props}]
  (println "Drawer: " props)
  (let [[props] (spring/use-spring
                 {:left (if show
                          (- (.-innerWidth js/window) 300)
                          (.-innerWidth js/window))
                  :position "absolute"
                  :top "1rem"
                  ;stavio sam 1rem jer sam vidio na storybooku da je div 
                  ;unutar kojeg se nalazi cijeli moj izgled od topa udaljen 1rem 
                  :backgroundColor "#806290"
                  :height "100vh"
                  :width "300px"
                  ;:background-color "blue"
                  })]
    (spring/div
     {:style props}
     (d/div
      {:className "drawer"}
      "Animated Drawer!"
      children))));; 


;https://codesandbox.io/s/slide-in-drawer-react-spring-vx5pz?file=/src/App.js
(defnc sideWindowSlide [{:keys [show children]}]
  (let [[isDrawerShowing setDrawerShowing] (hooks/use-state false)
        handleToggleDrawer (fn []
                             (setDrawerShowing (not isDrawerShowing)))
        ;render-drawer  (fn [{:keys [show] :as props} & children]
        ;    (println children)
        ;    (let [[props] (spring/use-spring
        ;                   {:left
        ;                    (if show (- (.-innerWidth js/window) 300)
        ;                        (.-innerWidth js/window))
        ;                    :position "absolute"
        ;                    :top "1rem"
        ;                                ;stavio sam 1rem jer sam vidio na storybooku da je div
        ;                                ;unutar kojeg se nalazi cijeli moj izgled od topa udaljen 1rem 
        ;                    :backgroundColor "#806290"
        ;                    :height "100vh"
        ;                    :width "300px"})]
        ;      (spring/div
        ;       {:style props}
        ;       (d/div
        ;        {:className "drawer"}
        ;        "Animated Drawer!"
        ;        children))))
        ]
    ;; (.log js/console "SHOW OUTSIDE: " + show)
    ($ containerSlide
       (d/h1
        "Hi Medium")
       (d/h2
        "Custom slide-in drawer react-spring")
       (d/button
        {:className "openButton"
         :onClick handleToggleDrawer}
        (if isDrawerShowing "Close" "Open"))
     ;ovo je ono sto ce pisati na gumbu - kao kad imamo header,
      ; necemo pisati unutar njegovih propertya vec izmedu zagrada 
       ;- if nam govori sto ce pisat na gumbu
       #_(render-drawer
          {:show isDrawerShowing}
          (d/div "poydrav iz draweraa")
          (d/div "hey marko"))
       ($ Drawer {:show isDrawerShowing}))))


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


(defnc RotatingImg [{:keys [className]}]
  (let [calc (fn
               [x y]
               [(- 0 (/ (- y (/ (.-innerHeight js/window) 2)) 20))
                ; === -(y - window.innerHeight / 2) / 20
                (/ (- x (/ (.-innerWidth js/window) 2)) 20)
                ; === (x - window.innerWidth / 2) / 20 
                1.1]) ; === 1.1
        trans (fn [x y s]
                (str "perspective(600px) rotateX(" x "deg) rotateY(" y "deg) scale(" s ")"))
        [^js props api] (spring/use-spring (fn []
                                             {;:x 0
                                        ;:y 0
                                        ;:s 1
                                              :xys [0 0 1]
                                              :config {:mass 5
                                                       :tension 350
                                                       :friction 40}}))]
    (d/div
     {:className className}
     (spring/div
      {:className "card"
       :onMouseMove (fn [evt]
                      (.log js/console evt)
                      (api :set
                           {:xys (calc (.-clientX evt) (.-clientY evt))}))
       :onMouseLeave (fn [] (api :set {:xys [0 0 1]}))
       :style {:transform (. (.-xys props) interpolate trans)}}))))

(defstyled hover-rotate-img RotatingImg
  {:width "100%"
   :height "100vh"
   :margin 0
   :padding 0
   :background-color "white"
   :display "flex"
   :align-items "center"
   :justify-content "center"
   :overflow "hidden"
   :background "radial-gradient(ellipse at center, #eef0f2 0%, #90a2b2 100%)"
   ".card" {:max-width "45ch"
            :max-height "45ch"
            :width "50%"
            :height "50%"
            :background "grey"
            :border-radius "5px"
            :background-image "url(https://drscdn.500px.org/photo/435236/q%3D80_m%3D1500/v2?webp=true&sig=67031bdff6f582f3e027311e2074be452203ab637c0bd21d89128844becf8e40)"
            :background-size "cover"
            :background-position "center center"
            :box-shadow "0px 10px 30px -5px rgba(0, 0, 0, 0.3)"
            :transition "box-shadow 0.5s"
            :will-change "transform"
            :border "15px solid white"}
   ".card:hover" {:box-shadow "0px 30px 100px -10px rgba(0 0 0 0.4)"}})


;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

#_(defnc ImgIterate []
  ;https://codesandbox.io/s/github/pmndrs/react-spring/tree/master/demo/src/sandboxes/image-fade?file=/src/App.tsx:976-1010
    (let [[index set] (hooks/use-state 0)
          transitions (useTransition index
                                     {:key index
                                      :from {:opacity 0}
                                      :enter {:opacity 1}
                                      :leave {:opacity 0}
                                      :config {:duration 3000}
                                      :onRest (fn [_a _b item]
                                                (if (= index item)
                                                  (set
                                                   (mod (+ 1)
                                                        (count ["photo-1544511916-0148ccdeb877",
                                                                "photo-1544572571-ab94fd872ce4",
                                                                "reserve/bnW1TuTV2YGcoh1HyWNQ_IMG_0207.JPG",
                                                                "photo-1540206395-68808572332f"]))) nil))
                                      :exitBeforeEnter true})]
      (d/div
       (transitions (fn
                      [style i]
                      (spring/div
                       {:style style
                        :background-image (str "url(https://images.unsplash.com/"
                                               (get ["photo-1544511916-0148ccdeb877",
                                                     "photo-1544572571-ab94fd872ce4",
                                                     "reserve/bnW1TuTV2YGcoh1HyWNQ_IMG_0207.JPG",
                                                     "photo-1540206395-68808572332f"] i)
                                               "?w=1920&q=80&auto=format&fit=crop)")}))))))

;(defn ^:dev/after-load init []
;  (rdom/render)
;   ($ Hello)
;   (.getElementById js/document "app")))

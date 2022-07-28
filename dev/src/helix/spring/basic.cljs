(ns helix.spring.basic
  (:require ["@fortawesome/react-fontawesome" :refer [FontAwesomeIcon]]
            ["@fortawesome/free-brands-svg-icons" :refer [faYoutube faDiscord faLinkedinIn faGithub]]
            ["@fortawesome/free-solid-svg-icons" :refer [faCircleInfo faChevronDown]]
            ["react-spring" :refer [config useSpring useTransition Transition]]
            ["react-use-measure" :default useMeasure] ;["@material-ui/icons/ExpandMore" :refer ExpandButton]
 ;[helix.spring.accordion :as acc]
            ["@use-gesture/react" :refer [useScroll]] ;["@material-ui/icons/ExpandMore" :refer ExpandButton]
            [goog.object]
            [cljs-bean.core :refer [->js]] ;;https://github.com/mfikes/cljs-bean/blob/master/src/cljs_bean/core.cljs - ugrađeno nešto - mi to tu koristimo 
            [clojure.string :as string]
            [helix.core :refer [$ <> defnc]]
            [helix.dom :as d]
            [helix.framer-motion :as motion]
            [helix.hooks :as hooks]
            [helix.spring :as spring]
            [helix.styled-components :refer [defstyled]]
            ["react-icons-kit" :refer [Icon]]
            ["react-icons-kit/md/ic_room" :refer [ic_room]]
            ["react-icons-kit/md/ic_thumb_up" :refer [ic_thumb_up]]
            ["react-icons-kit/md/ic_search" :refer [ic_search]]
            ["react-icons-kit/md/ic_settings" :refer [ic_settings]]
            ["react-icons-kit/md/ic_keyboard_arrow_right" :refer [ic_keyboard_arrow_right]]))


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

    (.log js/console "STYLE TYPE: " (type style))
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
   :width "196px" ;border zauzima 4px widtha s obje strane
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
                  :config {:duration 1500}})]
    (println "props rastavljen na keyeve: " (.keys js/Object props))
    (.log js/console "MEASURE WIDTH: " (.-width measure))

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
                        {:x -100 :y 0
                         :rotateZ 0}
                        :config {:duration 1750}
                        :width 80
                        :height 80
                         ;:backgroundColor "#46e891"
                        :borderRadius 16}))]
    (println style)
    (println "API: " api)
    ;;initial vrijednost koja se salje funkciji use-spring
    (hooks/use-effect
     ;;prima 2 argumenta - prvi je funkcija, drugi je polje???
     :always

     (api :start
          {:x 100
           :y 0
           :opacity 1
           :loop {:reverse true}
           :rotateZ 180})
     ;(async/go (async/<! (async/timeout 2000)) (api :set {:x 50 :y 50}) (println "setao sam ga"))
     ;(async/go (async/<! (async/timeout 5000)) (api :stop) (println "stopaosam ga"))
     )
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
        }
       (d/div
        {:style {:background-image "url(data:image/jpeg;base64,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)"
                 :background-size "cover"
                 :height "100%"
                 :width "100%"}}))))))


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
                         ;:backgroundColor "#46e891"
                         :borderRadius 20
                         :background-image "url(https://cdn-icons.flaticon.com/png/512/4489/premium/4489772.png?token=exp=1658407526~hmac=680d38107dd1acada626cc0059d31917)"
                         :config {:duration 1500}
                         :display "block"}))]
    (hooks/use-effect
     :always
     (api :start
          {:loop true
           :to {:rotateZ 180}})
     ;(async/go (async/<! (async/timeout 5000)) (api :stop))
     )

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
       {:style styles}
       (d/div
        {:style {:background-image "url(https://media.istockphoto.com/vectors/hourglass-with-transparent-glass-vector-id944011350?k=20&m=944011350&s=612x612&w=0&h=LPDY_8p_zWve9NrFKETZrxhHkAbz3AghsWhKj2e9-QY=)"
                 :background-size "cover"
                 :height "100%"
                 :width "100%"}})
       #_(d/div
          {:style {:height "100%"
                   :width "100%"
                   :background-image "url(https://cdn-icons.flaticon.com/png/512/4489/premium/4489772.png?token=exp=1658407526~hmac=680d38107dd1acada626cc0059d31917)"
                 ;:background-size "cover"
                   }}))))))

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
  (let [defaultHeight "50px"
        [open toggle] (hooks/use-state false)
        [contentHeight setContentHeight] (hooks/use-state defaultHeight)
        [ref helperHeight] (useMeasure)
        [expand] (spring/use-spring
                  {:config {:friction 10}
                   :height (if open (str contentHeight "px") defaultHeight)})
        [spinHelper] (spring/use-spring
                      {:config {:friction 10}
                       :transform (if open "rotate(180deg)" "rotate(0deg)")})
        height (.-height helperHeight)]

    (.log js/console "OPEN" open)
    (.log js/console "HELPER HEIGHT " helperHeight)
    ;ODNOSI SE NA ONAJ ELEMENT KOD KOJEG JE REF
    ;TO ZNACI DA SE REFERIRA NA TAJ ELEMENT - NAD NJIME SE RADE MJERE

    (.log js/console "HEIGHT: " height)
    (hooks/use-effect
     [height]
     (setContentHeight height)
     (.addEventListener js/window "resize" (setContentHeight height))
     (.removeEventListener js/window "resize" (setContentHeight height)))

    (d/div
     {:style {:display "flex"
              :align-items "center"
              :justify-content "center"
              :height "100vh"}} (d/div
                                 {:className className}
                                 (spring/div
                                  {:className "accordion"
                                   :style expand}
                                  (d/div
                                   {:className "content"
                                    :ref ref}
                                   (d/p
                                    {:className "paragraph"}
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
                                  ($ FontAwesomeIcon
                                     {:icon faChevronDown}))))))

(defstyled expand-button ExpandButton
  {:max-width "500px"
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

   ".accordion::after" {:content " "
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

#_(defnc ImgZoomOut [{:keys [image background]}]
                      ;uzima kljuceve iz mape koja mu je predana 
                      ;vrijednosti kljuceva predaje simbolima image i backgorund
    (let [[open setOpen] (hooks/use-state false)
          [helperImgZoom] (spring/use-spring {:f (if open 0 1)
                                              :r (if open -3 3)})
          ^js f (.-f helperImgZoom)
          ^js r (.-r helperImgZoom)
          cards (useSprings 5 (map
                               (fn [i]
                                 (println "USAO SAM U MAP FUNKCIJU")
                                 {:opacity (+ 0.2 (/ i 5))
                                  :z (if open (* (/ i 5) 80) 0)})
                               [0 1 2 3 4]))]
          ;[cards] (useSprings 5 (. [0 1 2 3 4] map (fn [i]
                                                   ;{:opacity (+ 0.2 (/ i 5))
                                                    ;:z (if open (* (/ i 5) 80) 0)})))
      (.log js/console "CARDS TYPE: " (type cards))
      (.log js/console "CARDS CONTENT: " cards)
      (.log js/console "0TH ELEM OF CARDS: " (get cards 0))
      ($ imgZoomOutBody
         (d/div
          {:className "imageZoomOutContainer"
           :onMouseEnter (fn []
                           (setOpen true))
           :onMouseLeave (fn []
                           (setOpen false))}
          ;(.cards map (fn ...))
          (map (fn
                 [z opacity index]
                 (spring/div
                  {:style  {:opacity opacity
                            :background background
                            :transform (interpolate [z
                                                     (.interpolate f [0 0.2 0.6 1] [0 index index 0]);(.interpolate f #js [0 0.2 0.6 1] #js [0 index index 0])
                                                     r]
                                                    (fn [z f r]
                                                      (str "translate3d(0,0," z "px) rotateX(" (* f r) "deg)")))}})
                 (spring/img
                  :style {:transform (.interpolate f [0 1] ["scale(0.7)" "scale(1)"])}
                  :src image))
               cards)))
                          ;(when (= index 4)

                             ;)
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
                      :background "#52649e"}))))




;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;https://codesandbox.io/s/react-accordion-by-saleh-m-dm5sp?file=/src/App.js
(defnc Accordion [{:keys [title text]}]
  (let [[open setOpen] (hooks/use-state false)
        toggleHandler (fn [e]
                        (setOpen not))
        styles {:accordionTitle {:color (if open "#10d6f5" "#fff")}}
        [openAnimation] (spring/use-spring
                         {:from {:opacity "0"
                                 :maxHeight "25px"}
                          :to {:opacity "1"
                               :maxHeight (if open "200px" "25px")}
                          :config {:duration "300"}})
        [iconAnimation] (spring/use-spring
                         {:from {:transform "rotate(0deg)"
                                 :color "#ffff"}
                          :to {:transform (if open "rotate(180deg)" "rotate(0deg)")
                               :color (if open "#10d6f5" "#fff")}
                          :config {:duration "120"}})]

    ;(println "OPENANIMATION: " openAnimation)
    ;(.log js/console "CONSOLE OPENANIMATION: " openAnimation)
    ;(.log js/console "STYLES: " styles)
    ;(.log js/console "STYLES PROP: " (.-styles styles))
    (.log js/console "STYLES TYPE: " (type styles))

    (.log js/console "OPEN ANIM TYPE: " (type openAnimation))
    (.log js/console "ICON ANIM TYPE: " (type iconAnimation))

    (spring/div
     {:className "accordion__item"
      :style openAnimation}
     (d/div
      {:className "accordion__header"
       :onClick toggleHandler}
      (d/h4
       {:style (.-styles styles)}
       ;"TU BI TREBAO ICI PROPS TITLE - 44. LINIJA") 
       title)
      (spring/i
       {:style iconAnimation}
       ($ FontAwesomeIcon
          {:icon faChevronDown})))
     (d/p
      {:className "accordion__content"}
      ;"Ovo je neki tekst lalalla")
      text))))

(defnc OptionSelect [{:keys [className]}]
  (let [[titleAnimation] (spring/use-spring {:from {:transform "translateY(-30px)"}
                                             :to {:transform "translateY(15px)"}
                                             :config {:mass 3
                                                      :tension 500
                                                      :friction 25}})]
    ;(println "TITLEANIMATION " titleAnimation)
    ;(.log js/console "TITLEANIM TYPE: " (type titleAnimation))


    (d/div
     {:className className}
     (d/div
      {:className "main"}
      (spring/h1
       {:style titleAnimation}
       "React Accordion")
      (d/div
       {:className "accordion"}
       ($ Accordion
          {:title "Item 1 - Lorem ipsum dolor sit amet"
           :text "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"})
       ($ Accordion
          {:title "Item 2 - Lorem ipsum dolor sit amet"
           :text "Lorem ipsum dolor sit amet, consectetur adipiscing elit, sed do eiusmod tempor incididunt ut labore et dolore magna aliqua. Ut enim ad minim veniam"}))))))
(defstyled option-select OptionSelect
  {:margin 0
   :padding 0
   :background-color "#1c2938"
   :display "flex"
   :align-items "center"
   :justify-content "center"
   :height "100vh"
   ".main" {:display "flex"
            :height "100vh"
            :width "100%"
            :align-items "center"
            :flex-direction "column"
            :row-gap "20px"}
   "h1" {:margin "10px"
         :font-size "40px"
         :color "rgb(255, 255, 255)"}
   ".accordion" {:margin "30px"
                 :width "fit-content"
                 :display "flex"
                 :flex-direction "column"
                 :align-items "center"}
   ".accordion__item" {:width "50%"
                       :max-height "25px"
                       :padding "17px 10px"
                       :border-bottom "1px solid #c9c9c9"
                       :color "#fff"
                       :overflow "hidden"
                       :cursor "pointer"}
   ".accordion__header" {:display "flex"
                         :align-items "center"
                         :justify-content "space-between"}
   ".accordion__header h4" {:transition "0.2s ese-in-out"
                            :font-size "16px"
                            :font-weight 400
                            :margin-bottom "10px"}
   ".accordion__header i" {:transition "0.2s ease-in-out"
                           :transform-origin "center"
                           :margin-bottom "10px"}
   ".accordion__header:hover h4" {:color "#10d6f5 !important"}
   ".accordion__header:hover i" {:color "#10d6f5"}
   ".accordion__content" {:margin "5px"}
   "@media (min-width: 900px)" {".accordion__item" {:width "40%"}}
   "@media (max-width: 600px)" {"h1" {:font-size "30px"}
                                ".accordion__item" {:width "80%"}}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



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
    (.log js/console "PROPS TYPE" (type props))
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

;https://codesandbox.io/s/ymmq5jyp19?file=/src/index.js
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

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc TextScroller [{:keys [text]}]
  (let [[key setKey] (hooks/use-state 1)
        [scrolling] (spring/use-spring
                     {:from {:transform "translate(65%,0)"}
                      :to {:transform "translate (-65%, 0)"}
                      :config {:duration 15000}
                      :reset true
                      :onRest (fn []
                                (setKey (+ key 1)))})]
    (d/div
     {:key key
      ;:style {:margin "0% 15% 0% 15%"}
      }
     (spring/div
      {:style scrolling} text))))

(defnc TextTopAnim [{:keys [className]}]
  (d/div
   {:className className}
   ($ TextScroller
      {:text "This is some text"})
   (d/h1
    "Look up!")))

(defstyled text-top-anim TextTopAnim
  {:text-align "center"
   :font-family "sans-serif"})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;https://codesandbox.io/s/03k16zq670?file=/src/index.js

#_(def slideHelper (vector {:id 0
                          :url "photo-1544511916-0148ccdeb877?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1901&q=80i"
                          :content "Slide1"}
                         {:id 1
                          :url "photo-1544572571-ab94fd872ce4?ixlib=rb-1.2.1&ixid=eyJhcHBfaWQiOjEyMDd9&w=1534&q=80"
                          :content "Slide2"}
                         {:id 2
                          :url "reserve/bnW1TuTV2YGcoh1HyWNQ_IMG_0207.JPG?ixlib=rb-1.2.1&w=1534&q=80"
                          :content "Slide3"}
                         {:id 3
                          :url "photo-1540206395-68808572332f?ixlib=rb-1.2.1&w=1181&q=80"
                          :content "Slide4"}))
#_(defnc PictureSlider [{:keys [className]}]
  (.log js/console "SLIDEHELPER" slideHelper)
  (println "SLIDEHELPER PRN" slideHelper)
  (println "PRVI " (get slideHelper 0))
  (let [;slides (string/split-lines slideHelper)
        [index set] (hooks/use-state 0)
        transitions (useTransition
                     (get slideHelper index)
                     (fn [item]
                       (:id item))
                     {:from {:opacity 0}
                      :enter {:opacity 1}
                      :leave {:opacity 0}
                      :config (.-molasses config)})]
    (.log js/console "slideHelper" slideHelper)

    (hooks/use-effect ([]
                       (js/setInterval (fn []
                                         (set (fn [state]
                                                (mod (+ state 1) (count slideHelper)))
                                              4000)))))
    (map (fn
           [^js obj]
           (.log js/console "OBJECT:" obj)
           (goog.object/set (.-props obj) "backgroundImage" (str "url(https://images.unsplash.com/" (:url (.-item obj)) "&auto=format&fit=crop)"))
           (spring/div
            {:key (.-key obj)
             :className "bg"
             :style (.-props obj)}
            #_(d/h1
               (.-content obj))))
         transitions)))

#_(defstyled picture-slider PictureSlider
  {:font-family "sans-serif"
   :text-align "center"
   ".bg" {:position "absolute"
          :top 0
          :left 0
          :width "100vw"
          :height "300px"
          :background-size "cover"
          :background-position "center"
          :will-change "opacity"
          :display "flex"
          :justify-content "center"
          :align-items "center"}
   ".bg h1" {:font-size "120px"
             :color "#fff"
             :text-shadow "0px 6px 6px rgba(0, 0, 0, 0.4)"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



#_(def text-variants
    (motion/variant
     {:inital {:y -100}
      :animate {:y 0 :transition {:delay 0.7}}}))

;https://codesandbox.io/s/image-hover-22ozd?file=/src/Styles.js
(defnc ImageTextHover [{:keys [className]}]
  (let [imageVariants {:beforeHover {}
                       :onHover {:scale 1.4}}
        textVariants {:beforeHover {:opacity 0}
                      :onHover {:opacity 1}}
        H1Variants {:initial {:y -100}
                    :animate {:y 0
                              :transition {:delay 0.7}}}]
    (d/div
     {:className className}
     (d/div
      {:className "container"}
      (motion/h1
       {:className "H1"
        :variants H1Variants}
       "Hover over the box")
      (motion/div
       {:className "box"
        :initial "beforeHover"
        :whileHover "onHover"}
       (motion/div
        {:className "image-box"
         :variants imageVariants})
       (motion/div
        {:className "text-box"
         :variants textVariants}
        "You have hoverd over the box, nice! Now look at this picture of waves. Very cool"))))))

(defstyled image-text-hover ImageTextHover
  {:height "100vh"
   ".container" {:width "100vw"
                 :height "100vh"
                 :display "flex"
                 :flex-direction "column"
                 :align-items "center"
                 ;:background "#111"
                 }
   ".box" {:width "25rem"
           :height "20rem"
           :border-radius "5px"
           :background "#fff"
           :display "flex"
           :flex-direction "column"
           :justify-content "flex-end"
           :overflow "hidden"
           :margin-top "4rem"
           :position "relative"}
   ".H1" {:color "#000"
          :margin-top "2rem"}
   ".image-box" {:position "absolute"
                 :top 0
                 :left 0
                 :width "100%"
                 :height "100%"
                 :background "url(https://images.unsplash.com/photo-1540206395-68808572332f?ixlib=rb-1.2.1&w=1181&q=80&auto=format&fit=crop)"
                 :background-size "cover"
                 :transition "transform 0.3s"}
   ".text-box" {:color "#fff"
                :padding "1rem"
                :background "#344"
                :overflow "hidden"
                :z-index "2"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc Modal [{:keys [style closeModal]}]
  (.log js/console "STYLE: " style)
  (spring/div
   {:style style
    :className "modal"}
   (d/h3
    {:className "modal-title"}
    "Modal title")
   (d/p
    {:className "modal-content"}
    "Lorem ipsum dolor sit amet consectetur adipisicing elit. Iusto dolores
      molestias praesentium impedit. Facere, perferendis voluptate at, amet
      excepturi ratione mollitia nemo ipsum odit impedit doloremque rerum.
      Quisquam, dolorum at?")
   (d/button
    {:className "modal-close-button"
     :onClick (fn []
                (closeModal))}
    "Close")))

(defnc AnimatedModal [{:keys [className]}]
  (let [[modalVisible setModalVisible] (hooks/use-state false)
        transitions (useTransition modalVisible "nil" {:from
                                                       {:opacity 0
                                                        :transform "translateY(-40px)"}
                                                       :enter
                                                       {:opacity 1
                                                        :transform "translateY80px"}
                                                       :leave
                                                       {:opacity 0
                                                        :transform "translateY(-40px)"}})]
       ;U TRANSITIONS BI TREBALI BITI SPREMLJENI BOOLEAN (modalVisible), STRING ("nil") I style TJ PROPS

    (d/div
     {:className className}
     (d/button
      {:className "show-modal-button"
       :onClick (fn []

                  (setModalVisible true))}
      "Show modal")
     (map (fn [obj]
            (if modalVisible
              ($ Modal
                 {:style (.-style obj)
                  :closeModal (fn []
                                (setModalVisible false))
                  ;:modalVisible modalVisible
                  })))transitions))))

(defstyled animated-modal AnimatedModal
  {:font-family "sans-serif"
   :text-align "center"
   ".show-modal-button" {:padding "16px"
                         :background-color "#6929c4"
                         :color "#fff"
                         :font-size "1em"
                         :border "none"
                         :margin-top "16px"
                         :align-self "center"
                         :cursor "pointer"
                         :position "fixed"
                         :top "calc(50% - 8px)"
                         :left "calc(50% - 100px)"
                         :width "200px"}
   ".modal" {:width "400px"
             :height "250px"
             :color "#fff"
             :background "#6929c4"
             :padding "40px"
             :position "absolute"
             :z-index "90"
             :top "calc(50% - 145px)"
             :left "calc(50% - 220px)"
             :display "flex"
             :flex-direction "column"}
   ".modal-close-button" {:padding "16px"
                          :background-color "#fff"
                          :color "#6929c4"
                          :font-size "1em"
                          :border "none"
                          :margin-top "16px"
                          :width "90%"
                          :align-self "center"
                          :cursor "pointer"
                          :transition "background-color 0.1s linear"}
   ".modal-close-button:hover, .modal-close-button:focus" {:background-color "#e8daff"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;https://codesandbox.io/s/react-spring-fun-scroll-vmncd

;(def movie1 "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dHJvcGljYWwlMjBiZWFjaHxlbnwwfHwwfHw%3D&w=1000&q=80")
;(def movie2 "https://media.istockphoto.com/photos/tropical-white-sand-beach-with-coco-palms-picture-id1181563943?k=20&m=1181563943&s=612x612&w=0&h=r46MQMvFnvrzzTfjVmvZED5nZyTmAYwISDvkdtM2i2A=")
;(def movie3 "https://www.wander-lust.nl/wp-content/uploads/2016/10/Beautiful-Destinations-job-offer1-e1478609871184.jpg")
;(def movie4 "https://bstatic.com/xdata/images/xphoto/1182x887/149368627.jpg?k=e88f4a7b474e451b3dfbcd14a6f0c974541e809786ba4a5a2de70e3e842b07d7&o=?size=S")
;(def movie5 "https://img.delicious.com.au/N4HZuhK0/del/2018/12/seljalandsfoss-iceland-98669-2.jpg")

#_(def clampAt 30)
#_(def movieHelper "https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dHJvcGljYWwlMjBiZWFjaHxlbnwwfHwwfHw%3D&w=1000&q=80
                  https://media.istockphoto.com/photos/tropical-white-sand-beach-with-coco-palms-picture-id1181563943?k=20&m=1181563943&s=612x612&w=0&h=r46MQMvFnvrzzTfjVmvZED5nZyTmAYwISDvkdtM2i2A=
                  https://www.wander-lust.nl/wp-content/uploads/2016/10/Beautiful-Destinations-job-offer1-e1478609871184.jpg
                  https://bstatic.com/xdata/images/xphoto/1182x887/149368627.jpg?k=e88f4a7b474e451b3dfbcd14a6f0c974541e809786ba4a5a2de70e3e842b07d7&o=?size=S
                  https://img.delicious.com.au/N4HZuhK0/del/2018/12/seljalandsfoss-iceland-98669-2.jpg")
;(def movies (hash-map "movie1 " movie1 "movie2" movie2
;                      "movie3" movie3 "movie4" movie4 "movie5" movie5))
#_(defnc FunScroll [{:keys [className]}]
    (let [clamp (fn [value]
                  (if (> value 0)
                    (if (> value clampAt) clampAt value)
                    (if (< value (- 0 clampAt)) (- 0 clampAt) value)))
        ;movies (vector movie1 movie2 movie3 movie4 movie5
                  ;"https://images.unsplash.com/photo-1507525428034-b723cf961d3e?ixlib=rb-1.2.1&ixid=MnwxMjA3fDB8MHxzZWFyY2h8MXx8dHJvcGljYWwlMjBiZWFjaHxlbnwwfHwwfHw%3D&w=1000&q=80"
                  ;"https://media.istockphoto.com/photos/tropical-white-sand-beach-with-coco-palms-picture-id1181563943?k=20&m=1181563943&s=612x612&w=0&h=r46MQMvFnvrzzTfjVmvZED5nZyTmAYwISDvkdtM2i2A="
                  ;"https://www.wander-lust.nl/wp-content/uploads/2016/10/Beautiful-Destinations-job-offer1-e1478609871184.jpg"
                  ;"https://bstatic.com/xdata/images/xphoto/1182x887/149368627.jpg?k=e88f4a7b474e451b3dfbcd14a6f0c974541e809786ba4a5a2de70e3e842b07d7&o=?size=S"
                  ;"https://img.delicious.com.au/N4HZuhK0/del/2018/12/seljalandsfoss-iceland-98669-2.jpg"
                ;)
          movies (string/split-lines movieHelper)  ;ako bi samo zapisao u vector sve movie, iterirao bi mi po svakom znaku u 1. linku pa bi dobio masu
        ;sourceva koji se sastoje od jednog slova 
          [style api] (spring/use-spring (fn []
                                           {:transform "perspective(500px) rotateY(0deg)"}))
          bind (useScroll (fn [^js event]
                            (.log js/console "USAO SAM U USESCROLL")
                            (.log js/console "EVENT " event)
                            (.log js/console "SCROLLING " (.-scrolling event))
                            (.log js/console "DELTA[0]" (get (.-delta event) 0))
                            (.log js/console "CLAMP: " (clamp (get (.-delta event) 0)))
                            (api {:transform (str "perspective(500px) rotateY("
                                                  (if (.-scrolling event)
                                                    (clamp (get (.-delta event) 0)) 0) "deg)")})))]
      (.log js/console "CLAMP: " clamp)
      (.log js/console "BIND: " bind)
      (.log js/console "STYLE: " style)
      (.log js/console "API: " api)
    ;(.log js/console "MOVIES: " movieHelper)
    ;(.log js/console "BIND SVE: " (& bind))
      (d/div
       {:className className}
       (d/div
        {:className "container" & (bind)}
        (map (fn [src]
            ;(.log js/console "SOURCE " src)
            ;(.log js/console "MOVIES " movies)
               (goog.object/set style "backgroundImage" (str "url(" src ")"))
               (println "MERGE EX: ")
               (.log js/console style)
               (spring/div
                {:key src
                 :className "card"
               ; :style {:background-image (str "url(" src ")")}
                 :style style}))
             movies)))))

#_(defstyled fun-scroll FunScroll
    {:height "80vh"
     :width "100%"

     "::-webkit-scrollbar"  {:width "0px"} ;mjesto gdje se horizontalni i vertikalni scrollbar susrecu
                                         ;izmedu njih nema razmaka jer je 0px
     ".container" {:display "flex"
                   :overflow-x "scroll"
                   :width "100%"
                   :padding "20px 0"
                   :margin-top "60px"
                   :height "80vh"
                 ;:justify-content "center"
                 ;:align-items "center"
                   }
     ".card" {:flex-shrink 0
              :width "300px"
              :height "200px"
              :border-radius "10px"
              :margin-left "10px"
              :background-size "cover"
              :background-repeat "no-repeat"
              :background-position "center center"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;https://codesandbox.io/s/react-spring-raise-animation-gf3qj?file=/src/Raise.js
(defnc Raise [{:keys [height timing children]}]
  (let [[isRaised setIsRaised] (hooks/use-state false)
        [style] (spring/use-spring {:display "inline-block"
                                    :backfaceVisibility "hidden"
                                    :transform (if isRaised (str "translateY(-" height "px)") "translateY(0px)")
                                    :config {:mass 1
                                             :tension 400
                                             :friction 15}})]

    (.log js/console "CHILDREN: " children)
    ;(.log js/console "IS RAISED NA POCETKU: " isRaised)
    (hooks/use-effect [isRaised timing]
                      (if isRaised (let [timeoutId
                                         (js/setTimeout (fn []
                                                          (.log js/console "Ušel sam u funkciju")
                                                          (setIsRaised false)) timing)]
                                     (.log js/console "U FUNKCIJI SAM")
                                     (.log js/console "TIMEOUT: " timeoutId)
                                     ;(setIsRaised false)
                                     (.log js/console "SPUSTIO SAM U FALSE")
                                     (js/clearTimeout js/window timeoutId))))

    (spring/span
     {:onMouseEnter (fn []
                      (.log js/console "USAO U MOUSE ENTER U MOUSE")
                      (.log js/console "IS RAISED PRIJE: " isRaised)
                      (setIsRaised true)
                      (.log js/console "IS RAISED POSLIJE U MOUSE: " isRaised))
      :style style}
     children)))

(defnc RaiseIconAnim [{:keys [className]}]
  (d/div
   {:className className}
   ($ Raise
      {:height 10
       :timing 200}
      ($ Icon {:icon ic_room
               :size 90
               :className "Icon"}))
   ($ Raise {:height 10
             :timing 200}
      ($ Icon {:icon ic_thumb_up
               :size 90
               :className "Icon"}))))
(defstyled raise-icon-anim RaiseIconAnim
  {:font-family "sans-serif"
   :margin-top "20%"
   :display "grid"
   :justify-content "space-evenly"
   :grid-template-columns "auto auto"
   ".Icon" {:color "#1f3b4d"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;



;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

;https://codesandbox.io/s/react-spring-icon-animation-examples-fioib?file=/src/useMove.js
(defnc Move
  [{:keys [x y rotation scale timing springConfig children]
    :or {x 0
         y 0
         rotation 0
         scale 1
         timing 200
         springConfig {:tension 400
                       :friction 15}}}]
  (let [[isTouched setIsTouched] (hooks/use-state false)
        [style] (spring/use-spring
                 {:display "inline-block"
                  :backfaceVisibility "hidden"
                  :transform (if isTouched
                               (str "translate(" x "px," y "px)"
                                    " rotate(" rotation "deg)"
                                    " scale(" scale ")")
                               (str "translate(0px, 0px)"
                                    " rotate(0deg)"
                                    " scale(1)"))
                  :config springConfig})]
    (hooks/use-effect
     [isTouched timing]
     (when isTouched
       (let [timeoutId
             (js/setTimeout
              (fn []
                (setIsTouched false))
              timing)]
         (js/clearTimeout js/window timeoutId))))
    (spring/span
     {:style style
      :onMouseEnter (fn [] (setIsTouched true))}
     children)))

(defnc IconMovement [{:keys [className]}]
  (d/div
   {:className className}
   ($ Move
      {:rotation 90
       :timing 200
       :springConfig {:tension 200
                      :friction 7}}
      ($ Icon
         {:icon ic_settings
          :size 90
          :className "Icon"}))
   ($ Move
      {:scale 1.5
       :springConfig {:mass 2
                      :tension 100
                      :friction 20}}
      ($ Icon
         {:icon ic_search
          :size 90
          :className "Icon"}))
   ($ Move
      {:x 20}
      ($ Icon
         {:icon ic_keyboard_arrow_right
          :size 90
          :className "Icon"}))))

(defstyled icon-movement IconMovement
  {:font-family "sans-serif"
   :margin-top "20%"
   :display "grid"
   :place-items "center"
   :grid-template-columns "auto auto auto"
   ".Icon" {:color "#1f3b4d"}})
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;


;https://codesandbox.io/s/trailing-transition-pu3yp?file=/src/index.tsx

(def itemsHelper "one
                  two
                  three")
(def items (string/split-lines itemsHelper))

(defnc ToggleAnimTrail [{:keys [className]}]
    (let [[isHidden setHidden] (hooks/use-state false)
          [trail] (spring/use-trail
                 (count items)
                 {:opacity (if isHidden 0 1)})
          toggle (fn []
                   (setHidden not))]
      (.log js/console "TRAIL: " trail)
      (.log js/console "COUNT ITEMS: " (count items))
      (d/div
       {:className className}
       (d/section
        (d/button
         {:onClick toggle}
         "TOGGLE")
        (d/ul
         (map-indexed
          (fn [idx obj]
            (.log js/console "Object: " idx obj)
            (let [item (get items idx)]
              (.log js/console "ITEM: " item)
              (.log js/console "OPACITY: " (.-opacity obj))
              (spring/li
               {:style obj
                :key item}
               item)))
          trail))))))

(defstyled toggle-anim-trail ToggleAnimTrail
    {:width "100%"
     :height "100vh"
     :display "flex"
     :justify-content "center"
     :background "black"
     "section" {:display "flex"
                :flex-direction "column"
                :justify-content "center"
                :align-items "center"}
     "button" {:margin-bottom "16px"
               :padding "8px 16px"
               :border "1px solid white"
               :background "transparent"
               :letter-spacing "0.1em"
               :cursor "pointer"}
     "ul" {:list-style "none"}
     "li" {:margin-bottom "8px"}
     "*" {:box-sizing "border-box"
          :margin 0
          :padding 0
          :color "white"
          :font-family "monospace"
          :font-size "16px"}})


(def itemsHelper2 "ITEM1
                  ITEM2
                  ITEM3
                  ITEM4
                   ITEM5")

(defnc ItemsFadeout [{:keys [className]}]
  (let [[toggle set] (hooks/use-state true)
        items (string/split-lines itemsHelper2)
        [trail] (spring/use-trail (count items) {:config {:mass 5
                                                      :tension 2000
                                                      :friction 200}
                                             :opacity (if toggle 1 0)
                                             :x (if toggle 0 20)
                                             :height (if toggle 80 0)
                                             :from {:opacity 0
                                                    :x 20
                                                    :height 0}})
        ;mora se prebaciti u js jer koristimo njihovu funkciju (nije napisana u clj)
        ]
    (.log js/console "TRAIL: " trail)
    (.log js/console "LENGTH: " (count items))
    (d/div
     {:className className}
     (d/div
      {:className "trails-main"
       :onClick (fn []
                  (set (fn [state]
                         (not state))))}
      (d/div
       (map-indexed
        (fn [index ^js obj]
          (.log js/console "X: " (.-x obj))
          (.log js/console "OBJECT: " obj)
          (spring/div
           {:key (get items index)
            :className "trails-text"
            :style {:opacity (.-opacity obj)
                    :config (.-config obj)
                    :from (.-from obj)
                    :transform (.interpolate (.-x obj) (fn [x]
                                                         (str "translate3d(0," x "px,0)")))}}
           (spring/div
            {:style
             {:height (.-height obj)}}
            (get items index)))) trail))))))

(defstyled items-fadeout ItemsFadeout
  {:width "100%"
   :height "100vh"
   :margin 0
   :padding 0
   :font-family "-apple-system, BlinkMacSystemFont, avenir next, avenir,
    helvetica neue, helvetica, ubuntu, roboto, noto, segoe ui, arial, sans-serif"
   :background "transparent"
   :cursor "default"
   :background-color "#f0f0f0"
   ".trails-main" {:position "relative"
                   :width "100%"
                   :height "100%"
                   :overflow "hidden"
                   :cursor "pointer"
                   :display "flex"
                   :justify-content "center"
                   :align-items "center"}
   ".trails-text" {:position "relative"
                   :width "100%"
                   :height "80px"
                   :line-height "80px"
                   :color "rgb(66, 61, 63)"
                   :font-size "5em"
                   :font-weight "800"
                   :text-transform "uppercase"
                   :will-change "transform, opacity"
                   :overflow "hidden"}
   ".trails-text > div" {:overflow "hidden"}})


#_(def pages (vector
              (fn [style]
                (.log js/console "POZVAN SAM")
                (spring/div
                 {:style style}
                 "A"))
              (fn [style]
                (spring/div
                 {:style style}
                 "B"))
              (fn [style]
                (spring/div
                 {:style style}
                 "C"))))
#_(defnc SlidingLetters [{:keys [className]}]
    (let [state {:index 0}
          toggle (fn []
                   (js/setState (fn [state]
                                  {:index (if (= (:index state) (- (count pages) 1)) 0 (+ (:index state) 1))})))]
   ;(println "PAGES" pages) 


      (.log js/console "ELEMENT0: " ($ ((get pages 0) {:background-color "yellow"
                                                       :width "40%"
                                                       :height "200px"})))
    ;2 zagrade zato što prva evaluira get metodu (vraća fj-u), a druga poziva funkciju


    ;(.log js/console "ELEMENT1: " (get pages 1))
   ;(.log js/console "ELEMENT2: " (get pages 2))
   ;(.log js/console "INDEX .- " (.-index state))
   ;(.log js/console ":INDEX " (:index state))
   ;(println "(- (count pages) 1)" (- (count pages) 1))
   ;(println "(= (:index state) (- (count pages) 1))" (= (:index state) (- (count pages) 1)))
   ;(println "(+ (:index state) 1)" (+ (:index state) 1))

      (.log js/console "TRANSITION: " Transition)
      (.log js/console "Icon: " Icon)
      (println "TRANSITION NEW" Transition)
      (println "ICON NEW" Icon)

      (.log js/console "ISPIS " (fn [index]
                                  (.log js/console "TU SAM")
                                  (fn [props]
                                    (println "A TU?")
                                    (let [Page ((get pages index))]
                                      (.log js/console "PAGE CONSOLE: " Page)
                                      (println "PAGE PRINTLN: " Page)
                                      ($ Page
                                         {:style props})))))
      (.log js/console "ISPIS EVALUACIJA " (fn [index]
                                             (.log js/console "TU SAM")
                                             (fn [props]
                                               (println "A TU?")
                                               (let [Page ((get pages index))]
                                                 (.log js/console "PAGE CONSOLE: " Page)
                                                 (println "PAGE PRINTLN: " Page)
                                                 ($ Page
                                                    {:style props})))))
      (d/div
       {:className className}
       (d/div
        {:className "main"
         :onClick toggle}
        "PRIJE TRANSITION"
        ($ Transition
           {:items (:index state)
            :from {:opacity 0
                   :transform "translate3d(100%,0,0)"}
            :enter {:opacity 1
                    :transform "translate3d(0%,0,0)"}
            :leave {:opacity 0
                    :transform "translate3d(-50%,0,0)"}}

           #_(let [Page (get pages (:index state))]
               (.log js/console "PAGE CONSOLE: " Page)
               (println "PAGE PRINTLN: " Page)
               ($ Page
                  {:style props}))
           (fn [index]
             (.log js/console "TU SAM")
             (fn [props]
               (println "A TU?")
               (let [Page ((get pages index))]
                 (.log js/console "PAGE CONSOLE: " Page)
                 (println "PAGE PRINTLN: " Page)
                 ($ Page
                    {:style props})))))))))
#_(defstyled sliding-letters SlidingLetters
    {:width "100%"
     :height "50vh"
     :margin 0
     :padding 0
     :background "#dfdfdf"
     ".main > div" {:cursor "pointer"
                    :position "absolute"
                    :width "100%"
                    :height "100%"
                    :display "flex"
                    :justify-content "center"
                    :align-items "center"
                    :color "white"
                    :font-family "Kanit, sans-serif"
                    :font-weight 800
                    :font-size "26em"
                    :will-change "transform, opacity"}})

;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc HoverSocial [{:keys [className]}]
  (d/div
   {:className className}
   (d/ul
    (d/li
     (d/a
      {:href "https://www.youtube.com/channel/UCvcJsbMu_DiCTQUrLJ7pGQg/featured"}
      (d/i
       ($ FontAwesomeIcon
          {:icon faYoutube
           :className "icon"})
       )))
    (d/li
     (d/a
      {:href "#"}
      (d/i
       ($ FontAwesomeIcon
          {:icon faDiscord
           :className "icon"})
       )))
    (d/li
     (d/a
      {:href "https://hr.linkedin.com/company/neyho"}
      (d/i
       ($ FontAwesomeIcon
          {:icon faLinkedinIn
           :className "icon"})
       )))
    (d/li
     (d/a
      {:href "#"}
      (d/i
       ($ FontAwesomeIcon
          {:icon faGithub
           :className "icon"})
       ))))))

(defstyled hover-social HoverSocial
  {:margin 0
   :padding 0
   :background "#262626"
   :height "100vh"
   "ul" {:display "flex"
         :position "absolute"
         :top "50%"
         :left "50%"
         :transform "translate(-50%, 50%)"}
   "ul li" {:list-style "none"}
   "ul li a" {:width "80px"
              :height "80px"
              :background-color "#fff"
              :text-align "center"
              :line-height "80px"
              :font-size "35px"
              :margin "0 10px"
              :display "block"
              :border-radius "50%"
              :position "relative"
              :overflow "hidden"
              :border "3px solid #fff"
              :z-index "1"}
   "ul li a .icon" {:position "relative"
                    :color "#262626"
                    :transition ".5s"
                    :z-index "3"}
   "ul li a:hover .icon" {:color "#fff"
                          :transform "rotateY(360deg)"}
   "ul li a:before" {:content "''"
                     :position "absolute"
                     :top "100%"
                     :left 0
                     :width "100%"
                     :height "100%"
                     :background "#f00"
                     :transition ".5s"
                     :z-index "2"}
   "ul li a:hover:before" {:top 0}
   "ul li:nth-child(1) a:before" {:background "#FF0000"}
   "ul li:nth-child(2) a:before" {:background "#7289DA"}
   "ul li:nth-child(3) a:before" {:background "#0077b5"}
   "ul li:nth-child(4) a:before" {:background "#26a641"}})
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;
;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;;

(defnc PopUpInfo [{:keys [className]}]
  (.log js/console "INFO: " faInfo)
  (.log js/console "FB:" faGithub)
  (d/div
   {:className className}
   (d/div
    {:className "card"
     :tabindex "0"}
    (d/span
     {:className "card__infoicon"}
     (d/i
      ($ FontAwesomeIcon
         {:icon faCircleInfo
          :className "icon"})))
    (d/h1
     {:className "card__title"}
     "This is a title")
    (d/p
     {:className "card__description"}
     (str "Here are some interesting info about this website:"
          "This website was made on 28th of July 2022."
          "It has been built by Neyho employee in Buzin, Croatia")))))

(defstyled pop-up-info PopUpInfo
  {:box-sizing "border-box"
   :height "100vh"
   :display "grid"
   :place-content "center"
   :align-items "center"
   :font-family "nunito, sans-serif"
   :background-color "#0099ff" 
   ".icon" {:font-weight 900
            :font-family "Font Awesome 5 Free"}
   ".card" {:position "relative"
            :padding "42px 32px"
            :clip-path "circle(5% at 95% 11%)"
            :transition "all ease-in-out .3s"
            :background-color "#ffffff"
            "&__infoicon" {:position "absolute"
                           :top "10px"
                           :right "28px"
                           :font-size "1.4em"
                           :color "#000000"
                           :transition "ease-out .3s"}
            "&__title" {:margin 0
                        :font-size "3em"
                        :line-height "1.6"}
            "&__description" {:margin 0
                              :font-size "1.1em"
                              :line-height "1.6"}
            "&:hover" {:border-bottom-color "#0099ff"}
            "&:hover, &:focus" {:clip-path "circle(75%)"
                                :border-radius "20px"
                                :box-shadow "0px 3px 9px rgba(0,0,0,0.12),
                0px 3px 18px rgba(0,0,0,0.08)"
                                :background "#ffffff"
                                :outline "none"
                                ".card_infoicon" {:opacity 0}}
            "&:focus" {:box-shadow "0px 3px 9px rgba(0,0,0,0.12), 
                0px 3px 18px rgba(0,0,0,0.08), 
                0px 0px 0px 4px rgba(0,0,0,0.2)"}}})
;(defn ^:dev/after-load init []
;  (rdom/render)
;   ($ Hello)
;   (.getElementById js/document "app")))

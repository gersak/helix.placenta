(ns helix.konva.main
  (:require
   [clojure.core.async :as async]
   [cljs-bean.core :refer [->js]] ;;https://github.com/mfikes/cljs-bean/blob/master/src/cljs_bean/core.cljs - ugrađeno nešto - mi to tu koristimo
   [helix.core :refer [defnc $ <>]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [helix.styled-components :refer [defstyled]]
   [helix.konva :as konva]
   [helix.image :refer [use-image]]))

;
; KONVA TESTING
; 

; Basic premade shapes

(defnc BasicShape []
  (<>
   (konva/Stage {:width 500,
                 :height 500}
                (konva/Layer
                 (konva/Text {:text "Neki tekst kao koji je tekstan",
                              :fontSize 15})
                 (konva/Rect {:x 20,
                              :y 50,
                              :width 100,
                              :height 100,
                              :fill "blue",
                              :shadowBlur 10})
                 (konva/Circle {:x 200,
                                :y 100,
                                :radius 50,
                                :fill "green"})
                 (konva/Line {:x 20,
                              :y 200,
                              :points (->js [0, 0, 100, 0, 100, 100]),
                              :tension 0.5,
                              :closed true,
                              :stroke "black",
                              :fillLinearGradientStartPoint (->js {:x -50, :y -50}),
                              :fillLinearGradientEndPoint (->js {:x 50, :y 50}),
                              :fillLinearGradientColorStops (->js [0, "red", 1, "yellow"])})))))

; Custom created shape

(defnc CustomShape []
  (<>
   (konva/Stage
    {:width 700,
     :height 700}
    (konva/Layer
     (konva/Shape
      {:sceneFunc (fn [^js context, ^js shape]
                    (.beginPath context)
                    (.moveTo context 20, 50)
                    (.lineTo context 220, 80)
                    (.quadraticCurveTo context 190, 100, 260, 170)
                    (.closePath context)
                    (.fillStrokeShape context shape)),
       :fill "turquoise",
       :stroke "black",
       :strokeWidth 5})))))

; Star that is draggable

(defnc MovableStars []
  (let [#_[helper] #_(hooks/use-state (repeatedly 10 " "))
        [stars setStars] (hooks/use-state
                          (->js {:id (.toString 0),
                                 :x (rand-int 700),
                                 :y (rand-int 700),
                                 :rotation (rand-int 180),
                                 :isDragging false}))]
    (<>
     (konva/Stage {:width 700,
                   :height 700}
                  (konva/Layer
                   (konva/Text {:text "Dragging of 1 star"})
                   (konva/Star
                    {:key (.-id stars),
                     :id (.-id stars),
                     :x (.-x stars),
                     :y (.-y stars),
                     :numPoints 5,
                     :innerRadius 20,
                     :outerRadius 40,
                     :draggable true,
                     :rotation (.-rotation stars),
                     :opacity 0.8,
                     :fill "turquoise",
                     :shadowColor "black",
                     :shadowBlur 10,
                     :shadowOpacity 0.6,
                     :shadowOffsetX (cond
                                      (= (.-isDragging stars) true) 10
                                      (= (.-isDragging stars) false) 5),
                     :shadowOffsetY (cond
                                      (= (.-isDragging stars) true) 10
                                      (= (.-isDragging stars) false) 5),
                     :scaleX (cond
                               (= (.-isDragging stars) true) 1.2
                               (= (.-isDragging stars) false) 1),
                     :scaleY (cond
                               (= (.-isDragging stars) true) 1.2
                               (= (.-isDragging stars) false) 1),
                     :onDragStart (fn [^js e]
                                    (.log js/console (.id (.-target e)))
                                    (setStars ->js (:isDragging (cond (= (.-id stars) (.id (.-target e))) true
                                                                      :else false)))),
                     :onDragEnd (fn [] (setStars ->js (:isDragging false)))}))))))

; Render of images using useImage

(defnc LionImage []
  (let [[image] (use-image "https://konvajs.org/assets/lion.png" "anonymous")]
    (konva/Image {:image image})))

(defnc YodaImage []
  (let [[image] (use-image "https://konvajs.org/assets/yoda.jpg" "anonymous")]
    (konva/Image {:image image
                  :x 200})))

(defnc Images []
  (konva/Stage {:width (.-innerWidth js/window),
                :height (.-innerHeight js/window)}
               (konva/Layer
                ($ LionImage)
                ($ YodaImage))))

; Transforming objects - resize, rotate, move

(defnc Rectangle [{:keys [shapeProps, isSelected, onSelect, onChange]}]
  (let [shapeRef (hooks/use-ref nil)
        trRef (hooks/use-ref nil)]
    (hooks/use-effect [isSelected] (when (= isSelected true)
                                     ^js (.nodes @trRef #js [@shapeRef])
                                     ^js (.batchDraw ^js (.getLayer @trRef))))
    (<>
     (konva/Rect
      {:x (:x shapeProps),
       :y (:y shapeProps),
       :height (:height shapeProps),
       :width (:width shapeProps),
       :fill (:fill shapeProps),
       :id (:id shapeProps),
       :onClick onSelect,
       :ref #(reset! shapeRef %),
       :draggable true,
       :onDragMove (fn [e]
                     ->js (onChange
                           {:width (:width shapeProps),
                            :height (:height shapeProps),
                            :fill (:fill shapeProps),
                            :id (:id shapeProps),
                            :x (.x (.-target e)),
                            :y (.y (.-target e))}))
       :onTransformEnd (fn []
                         (let [node ^js @shapeRef
                               scaleX (.scaleX node)
                               scaleY (.scaleY node)]
                           (.scaleX node 1)
                           (.scaleY node 1)
                           (onChange {:fill (:fill shapeProps),
                                      :id (:id shapeProps),
                                      :x #_150 (.x node),
                                      :y #_150 (.y node),
                                      :width #_250 (* (.width node) scaleX)
                                      :height #_250 (* (.height node) scaleY)})
                           #_(.log js/console scaleX)))})
     (when isSelected
       (konva/Transformer
        {:ref #(reset! trRef %)
         :boundBoxFunc (fn [oldBox, newBox]
                         (if (or (< (.-width newBox) 5) (< (.-height newBox) 5))
                           oldBox newBox))})))))


(defn checkDeselect [e, selectShape]
  (let [clickedOnEmpty (cond
                         (= (.-target e) ^js (.getStage (.-target e))) true
                         :else false)]
    (when (= clickedOnEmpty true) (selectShape nil))))

(defnc RenderTransform []
  (let [[rectangles, setRectangles] (hooks/use-state
                                     [{:x 10,
                                       :y 10,
                                       :width 100,
                                       :height 100,
                                       :fill "red",
                                       :id "rect1"},

                                      {:x 150,
                                       :y 150,
                                       :width 100,
                                       :height 100,
                                       :fill "green",
                                       :id "rect2"}])
        [selectedId, selectShape] (hooks/use-state nil)]
    (<>
     (konva/Stage {:width (.-innerWidth js/window),
                   :height (.-innerHeight js/window),
                   :onMouseDown (fn [e] (let [clickedOnEmpty
                                              (cond
                                                (= (.-target e) ^js (.getStage (.-target e))) true
                                                :else false)]
                                          (when (= clickedOnEmpty true) (selectShape nil))))}
                  (konva/Layer
                   (map
                    (fn [rect]
                      #_(.log js/console rect)
                      ($ Rectangle
                         {:shapeProps rect
                          :isSelected (if
                                       (= (:id rect) selectedId) true
                                       false)
                          :onSelect (fn []
                                      (selectShape (:id rect)))
                          :onChange (fn [newAttrs]
                                      #_(.log js/console (nth rectangles 1))
                                      #_(setRectangles update rectangles (.indexOf rectangles rect) merge newAttrs)
                                      (setRectangles (assoc rectangles (.indexOf rectangles rect) newAttrs)))}))
                    rectangles))))))

; Predefined image transformer - resize, rotate, move
; Image properties should be saved in state (images)

(defnc ImageRender [{:keys [shapeProps, selected, onSelect, onChange]}]
  (let [shapeRef (hooks/use-ref nil)
        trRef (hooks/use-ref nil)
        isSelected (= selected @shapeRef)]
    #_(.log js/console shapeProps)
    (hooks/use-effect
     [isSelected]
     (when (= isSelected true)
       (.nodes ^js  @trRef #js [@shapeRef])
       (.batchDraw ^js (.getLayer @trRef))))
    (<>
     (konva/Image
      {:image (:image shapeProps),
       :width (:width shapeProps),
       :height (:height shapeProps),
       :onClick (fn [e] (onSelect (.. e -target))),
       :ref #(reset! shapeRef %),
       :draggable true,
       :onDragMove (fn [e]
                     (let [node ^js @shapeRef]
                       ->js (onChange
                             {:image (:image shapeProps),
                              :width (.width node),
                              :height (.height node),
                              :x (.x (.-target e)),
                              :y (.y (.-target e))})))
       :onTransformEnd (fn []
                         (let [node ^js @shapeRef
                               scaleX ^js (.scaleX node)
                               scaleY ^js (.scaleY node)]
                           (.scaleX node 1)
                           (.scaleY node 1)
                           ->js (onChange {:image (:image shapeProps),
                                           :x #_150 (.x node),
                                           :y #_150 (.y node),
                                           :width #_250 (* (.width node) scaleX)
                                           :height #_250 (* (.height node) scaleY)}))
                         #_(.log js/console scaleX))})
     (when (= isSelected true)
       (konva/Transformer
        {:ref #(reset! trRef %)
         :boundBoxFunc (fn [oldBox, newBox]
                         (if (or (< (.-width newBox) 5) (< (.-height newBox) 5))
                           oldBox newBox))})))))


(defnc ImageTransformer []
  (let [[image-load] (use-image "https://konvajs.org/assets/yoda.jpg" "anonymous")
        [images set-images] (hooks/use-state [])
        [selected select!] (hooks/use-state nil)]
    (hooks/use-effect
     [image-load]
     (do (.log js/console image-load) (set-images [{:image image-load}]))
     #_(when (not= image-load js/undefined) (setImages [image-load])))
    #_(when (and (= (count images) 0) (not= image-load js/undefined))
        (setImages (conj images image-load)))
    (<>
     (konva/Stage
      {:width (.-innerWidth js/window),
       :height (.-innerHeight js/window),
       :onMouseDown (fn [e]
                      (let [clickedOnEmpty
                            (cond
                              (= (.-target e) ^js (.getStage (.-target e))) true
                              :else false)]
                        (when (= clickedOnEmpty true) (select! nil))))}
      (konva/Layer

       (map (fn [imgg]
              (.log js/console imgg)
              ($ ImageRender
                 {:shapeProps imgg
                  :selected selected
                  :onSelect (fn [ref] (select! ref))
                  :onChange (fn [newAttrs]
                              #_(.log js/console (nth rectangles 1))
                              (set-images (assoc images (.indexOf images imgg) newAttrs)))}))
            images)
       #_(.log js/console images))))))

; Export image (returns base64 string of image)

(defn DownloadURI [uri, name]
  (let [link (.createElement js/document "a")]
    (.log js/console link)
    #_(->js (.-download link name))
    #_(->js (.-href link uri))
    (.appendChild (.-body js/document) link)
    (.click link)
    (.removeChild (.-body js/document) link)))

(defnc ExportImage []
  (let [width (/ (.-innerWidth js/window) 2)
        height (/ (.-innerHeight js/window) 2)
        stageRef (hooks/use-ref nil)]
    (<>
     (d/button {:onClick (fn []
                           (DownloadURI (.toDataURL (.-current stageRef)), "slika.png")
                           (.log js/console (.toDataURL (.-current stageRef))))} (str "Click to log URI"))
     (konva/Stage
      {:width width,
       :height height,
       :ref #(reset! stageRef %)}
      (konva/Layer
       (konva/Rect
        {:x 0,
         :y 0,
         :width 80,
         :height 80,
         :fill "red"})
       (konva/Rect
        {:x (- width 80),
         :y 0,
         :width 80,
         :height 80,
         :fill "red"})
       (konva/Rect
        {:x (- width 80),
         :y (- height 80),
         :width 80,
         :height 80,
         :fill "blue"})
       (konva/Rect
        {:x 0,
         :y (- height 80),
         :width 80,
         :height 80,
         :fill "blue"}))))))

; Drop image from DOM into canvas

(defnc URLImage
  [{{:keys [x y src]} :image}]
  (let [[img] (use-image src "anonymous")]
    (when img
      (konva/Image
       {:image img,
        :x x
        :y y,
        :offsetX (/ (.-width img) 2),
        :offsetY (/ (.-height img) 2)}))))

(defnc DropIntoCanvas []
  (let [dragURL (hooks/use-ref nil)
        stageRef (hooks/use-ref nil)
        [images, setImages] (hooks/use-state [])]
    (d/div
     (d/br)
     (d/img
      {:alt "Lavko",
       :src "https://konvajs.org/assets/lion.png",
       :draggable true,
       :onDragStart (fn [e]
                      (reset! dragURL (.-src (.-target e))))})
     (d/div {:onDrop (fn [e]
                       (.preventDefault e)
                       (.setPointersPositions (.-current stageRef) e)
                       (setImages (conj images {:x (.-x (.getPointerPosition (.-current stageRef))),
                                                :y (.-y (.getPointerPosition (.-current stageRef))),
                                                :src (.-current dragURL)}))),
             :onDragOver (fn [e] (.preventDefault e))}
            (konva/Stage
             {:width (.-innerWidth js/window),
              :height (.-innerHeight js/window),
              :style {:border "1px solid grey"},
              :ref #(reset! stageRef %)}
             (konva/Layer
              (if (empty? images) ()
                  (map
                   (fn [image]
                     ($ URLImage {:image image}))
                   images))))))))

; Undo/redo position of objects

(defnc UndoRedo []
  (let [[history setHistory] (hooks/use-state [{:x 20
                                                :y 20}])
        #_(historyStep (atom 0))
        [historyStep setHistoryStep] (hooks/use-state 0)

        [state setState] (hooks/use-state (nth history 0))]
    (.log js/console)
    (konva/Stage {:width (.-innerWidth js/window),
                  :height (.-innerHeight js/window)}
                 (konva/Layer
                  (konva/Text {:text "Undo",
                               :onClick
                               (fn [] (if (= historyStep 0) () (let [previous (nth history (- historyStep 1))]
                                                                 (setHistoryStep (- historyStep 1))
                                                                 (setState previous))))})
                  (konva/Text {:text "Redo",
                               :x 50,
                               :onClick
                               (fn [] (if (= historyStep (- (count history) 1)) () (let [next (nth history (+ historyStep 1))]
                                                                                     (setHistoryStep (+ historyStep 1))
                                                                                     (setState next))))})
                  (konva/Rect {:x (:x state),
                               :y (:y state),
                               :width 50,
                               :height 50,
                               :fill "black",
                               :draggable true,
                               :onDragEnd (fn [e]
                                            (let [pos {:x (.x (.-target e)),
                                                       :y (.y (.-target e))}]
                                              (setHistory (subvec history 0 (+ historyStep 1)))
                                              (setHistoryStep (+ historyStep 1))
                                              (setHistory (conj history pos))
                                              (setState pos)))})))))

; Simple slider with CSS

(defstyled SliderCSS "div"
  {".slidercontainer" {:width "25%"},
   ".slider" {:-webkit-appearance "none",
              :appearance "none",
              :width "70%",
              :height "15px",
              :border-radius "5px",
              :background "#d3d3d3",
              :outline "none",
              :opacity 0.7,
              :-webkit-transition ".3s",
              :transition "opacity .3s"}
   ".slider:hover" {:opacity 1},
   ".slider::-webkit-slider-thumb" {:-webkit-appearance "none",
                                    :appearance "none",
                                    :width "25px",
                                    :height "25px",
                                    :border-radius "50%",
                                    :background "teal",
                                    :cursor "pointer"},
   ".slider::-moz-range-thumb" {:width "25px",
                                :height "25px",
                                :border-radius "50%",
                                :background "teal",
                                :cursor "pointer"},
   ".label" {:font-family "Arial"}})

(defnc SliderTesting []
  (let [[state set-state] (hooks/use-state "50")]
    (<>
     #_(.log js/console state)
     ($ SliderCSS
        (d/div {:className "slidercontainer"}
               (d/label {:for "simple-slider",
                         :className "label"}
                        "Zoom  ")
               (d/input
                {:id "simple-slider",
                 :name "simple-slider",
                 :type "range",
                 :min "1",
                 :max "100",
                 :value state,
                 :className "slider",
                 :onChange (fn [e]
                             (set-state (.-value (.-target e))))})
               (d/label {:for "simple-slider"
                         :className "label"} (str "  " state "%")))))))

; Simple avatar editor (combined image export and transform) - rotate, drag, zoom

(defn HandleScroll [^js e, zooming,  set-zoom]
  (let [scale-by 1.02
        old-scale zooming
        new-scale (if (< (.-deltaY (.-evt e)) 0) (* old-scale scale-by) (/ old-scale scale-by))]
    (set-zoom new-scale)))

(defnc AvatarRender [{:keys [shapeProps, selected, onSelect, onChange, rotation, zooming, set-zoom]}]
  (let [shape-ref (hooks/use-ref nil)
        tr-ref (hooks/use-ref nil)
        isSelected (= selected @shape-ref)]
    (hooks/use-effect
     [isSelected]
     (when (= isSelected true)
       (.nodes ^js  @tr-ref #js [@shape-ref])
       (.batchDraw ^js (.getLayer @tr-ref))))
    (<>
     (konva/Image
      {:& shapeProps,
       :image (:image shapeProps),
       :width (:width shapeProps),
       :height (:height shapeProps),
       :rotation rotation,
       :offsetX (if (= (:image shapeProps) js/undefined)
                  0
                  (/ (.-width (:image shapeProps)) 2)) #_(.-width (:image shapeProps)),
       :offsetY (if (= (:image shapeProps) js/undefined)
                  0
                  (/ (.-height (:image shapeProps)) 2)) #_(.-height (:img shapeProps)),
       :x (if (= (:image shapeProps) js/undefined) ; manually calculated for stage 500x500 (numberOfPixels / 2)
            0
            250),
       :y (if (= (:image shapeProps) js/undefined)
            0
            250)
       :scaleX (* zooming (if (= (:image shapeProps) js/undefined)
                            (:width shapeProps)
                            (if (> (.-width (:image shapeProps)) (.-height (:image shapeProps)))
                              (/ 500 (.-width (:image shapeProps)))
                              (/ 500 (.-height (:image shapeProps)))))),
       :scaleY (* zooming (if (= (:image shapeProps) js/undefined)
                            (:width shapeProps)
                            (if (> (.-width (:image shapeProps)) (.-height (:image shapeProps)))
                              (/ 500 (.-width (:image shapeProps)))
                              (/ 500 (.-height (:image shapeProps)))))),
       :onClick (fn [e] (onSelect (.. e -target))),
       :ref #(reset! shape-ref %),
       :draggable true,
       :onWheel (fn [e] (HandleScroll e zooming set-zoom))
       :onDragMove (fn [e]
                     (let [node ^js @shape-ref]
                       (onChange
                        {:image (:image shapeProps),
                         :width (.width node),
                         :height (.height node),
                         :x (.x (.-target e)),
                         :y (.y (.-target e))})))
       :onTransformEnd (fn []
                         (let [node ^js @shape-ref
                               scaleX ^js (.scaleX node)
                               scaleY ^js (.scaleY node)]
                           (.scaleX node 1)
                           (.scaleY node 1)
                           (onChange {:image (:image shapeProps),
                                      :x #_150 (.x node),
                                      :y #_150 (.y node),
                                      :width #_250 (max 10, * (.width node) scaleX)
                                      :height #_250 (max 10, * (.height node) scaleY)}))
                         #_(.log js/console scaleX))})
     (when (= isSelected true)
       (konva/Transformer
        {:ref #(reset! tr-ref %),
         :centeredScaling true,
         :boundBoxFunc (fn [old-box, new-box]
                         (if (or (< (.-width new-box) 5) (< (.-height new-box) 5))
                           old-box new-box))})))))



; vertical image https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Polignano_a_Mare_-_Isola_di_San_Paolo_-_startrail.png/800px-Polignano_a_Mare_-_Isola_di_San_Paolo_-_startrail.png
; large horizontal image https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1920px-Image_created_with_a_mobile_phone.png
; small (yoda) image https://konvajs.org/assets/yoda.jpg


(defn DownloadURI2 [uri, name, mime]
  (let [link (doto (.createElement js/document "a")
               (set! -download name)
               (set! -href uri)
               (set! -type mime))]
    (.appendChild (.-body js/document) link)
    (.click link)
    (.removeChild (.-body js/document) link)))

(defnc AvatarEditor []
  (let [[image-load] (use-image "https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Polignano_a_Mare_-_Isola_di_San_Paolo_-_startrail.png/800px-Polignano_a_Mare_-_Isola_di_San_Paolo_-_startrail.png" "anonymous")
        [images set-images] (hooks/use-state [])
        [selected select!] (hooks/use-state nil)
        stage-ref (hooks/use-ref nil)
        [rotate set-rotate] (hooks/use-state 0)
        [zoom set-zoom] (hooks/use-state 1)]
    (hooks/use-effect
     [image-load]
     (do (.log js/console image-load) (set-images [{:image image-load}])))

    (<>
     (d/div {:style {:width "502px"
                     :background "grey"}}
            (konva/Stage
             {:width 500 #_(.-innerWidth js/window),
              :height 500 #_(.-innerHeight js/window),
              :style {:border "1px solid black",
                      :borderRadius "250px",
                      :overflow "hidden"
                      :background "white"}
              :ref #(reset! stage-ref %),
              :onMouseDown (fn [e]
                             (let [clickedOnEmpty
                                   (cond
                                     (= (.-target e) ^js (.getStage (.-target e))) true
                                     :else false)]
                               (when (= clickedOnEmpty true) (select! nil))))}
             (konva/Layer
              (map (fn [imgg]
                     (.log js/console imgg)
                     ($ AvatarRender
                        {:shapeProps imgg
                         :selected selected
                         :onSelect (fn [ref] (select! ref))
                         :onChange (fn [new-attrs]
                                     (set-images (assoc images (.indexOf images imgg) new-attrs)))
                         :rotation rotate
                         :zooming zoom
                         :set-zoom set-zoom}))
                   images)
              #_(.log js/console images))))

     (d/button {:onClick (fn []
                           (DownloadURI2 (.toDataURL (.-current stage-ref)), "user-avatar.png", "image/png")
                           (.log js/console (.toDataURL (.-current stage-ref))))}
               (str "Download/log URI"))
     (d/button {:onClick (fn []
                           (set-images [{:image image-load,
                                         :offsetX (if (= image-load js/undefined)
                                                    0
                                                    (/ (.-width image-load) 2)) #_(.-width (:image shapeProps)),
                                         :offsetY (if (= image-load js/undefined)
                                                    0
                                                    (/ (.-height image-load) 2)) #_(.-height (:img shapeProps)),
                                         :x (if (= image-load js/undefined) ; manually calculated for stage 500x500 (numberOfPixels / 2)
                                              0
                                              250),
                                         :y (if (= image-load js/undefined)
                                              0
                                              250)}])
                           (set-zoom 1)
                           (set-rotate 0))} (str "Reset"))
     (d/br)
     (d/br)
     (d/button {:onClick (fn []
                           (set-rotate (if (> (- rotate 90) 0) (- rotate 90) 0)))} "Left")
     (d/button {:onClick (fn []
                           (set-rotate (if (< (+ rotate 90) 360) (+ rotate 90) 360)))} "Right")

     ($ SliderCSS
        (d/br)
        (d/div {:className "slidercontainer"}
               (d/label {:for "rotation-slider",
                         :className "label"}
                        "Rotation ")
               (d/input
                {:id "rotation-slider",
                 :name "rotation-slider",
                 :type "range",
                 :min "0",
                 :max "359",
                 :value rotate,
                 :className "slider",
                 :onChange (fn [e]
                             (set-rotate (.-value (.-target e))))})
               (d/label {:for "rotation-slider"
                         :className "label"}
                        (str "  " rotate "°")))
        (d/br)
        (d/div {:className "slidercontainer"}
               (d/label {:for "zoom-slider",
                         :className "label"}
                        "Zooming ")
               (d/input
                {:id "zoom-slider",
                 :name "zoom-slider",
                 :type "range",
                 :min "0.01",
                 :max "3",
                 :step 0.01,
                 :value zoom,
                 :className "slider",
                 :onChange (fn [e]
                             (set-zoom (.-value (.-target e))))})
               (d/label {:for "zoom-slider"
                         :className "label"}
                        (str "  " (int (* zoom 100)) "%")))))))
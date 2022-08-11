(ns helix.konva.main
  (:require
   [helix.core :refer [defnc $ <>]]
   [helix.hooks :as hooks]
   [helix.dom :as d]
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
                              :points #js [0, 0, 100, 0, 100, 100],
                              :tension 0.5,
                              :closed true,
                              :stroke "black",
                              :fillLinearGradientStartPoint #js {:x -50, :y -50},
                              :fillLinearGradientEndPoint #js {:x 50, :y 50}
                              :fillLinearGradientColorStops #js [0, "red", 1, "yellow"]})))))

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
        [{:keys [dragging? id x y rotation]} setStars]
        (hooks/use-state
         {:id (.toString 0)
          :x (rand-int 700),
          :y (rand-int 700),
          :rotation (rand-int 180),
          :dragging? false})]
    (konva/Stage
     {:width 700,
      :height 700}
     (konva/Layer
      (konva/Text {:text "Dragging of 1 star"})
      (konva/Star
       {:key id
        :id id,
        :x x,
        :y y,
        :numPoints 5,
        :innerRadius 20,
        :outerRadius 40,
        :draggable true,
        :rotation rotation,
        :opacity 0.8,
        :fill "turquoise",
        :shadowColor "black",
        :shadowBlur 10,
        :shadowOpacity 0.6,
        :shadowOffsetX (if dragging? 10 5),
        :shadowOffsetY (if dragging? 10 5),
        :scaleX (if dragging? 1.2 1),
        :scaleY (if dragging? 1.2 1),
        :onDragStart (fn [^js e]
                       (.log js/console (.id (.-target e)))
                       (setStars assoc :dragging? (= id (.. e -target -id)))),
        :onDragEnd (fn [] (setStars assoc :dragging? false))})))))

; Render of images using useImage

(defnc LionImage []
  (let [[image] (use-image "https://konvajs.org/assets/lion.png" "anonymous")]
    (konva/Image {:image image})))

(defnc YodaImage []
  (let [[image] (use-image "https://konvajs.org/assets/yoda.jpg" "anonymous")]
    (konva/Image {:image image
                  :x 200})))

(defnc Images []
  (konva/Stage
   {:width (.-innerWidth js/window),
    :height (.-innerHeight js/window)}
   (konva/Layer
    ($ LionImage)
    ($ YodaImage))))

; Transforming objects - resize, rotate, move

(defnc Rectangle [{:keys [selected?, onSelect, onChange]
                   {:keys [x y height width fill id]} :shapeProps}]
  (let [shapeRef (hooks/use-ref nil)
        trRef (hooks/use-ref nil)]
    ;;
    (hooks/use-effect
     [selected?]
     (when selected?
       ^js (.nodes @trRef #js [@shapeRef])
       ^js (.batchDraw ^js (.getLayer @trRef))))
    ;;
    (<>
     (konva/Rect
      {:x x
       :y y
       :height height,
       :width width,
       :fill fill,
       :id id,
       :onClick onSelect,
       :ref #(reset! shapeRef %),
       :draggable true,
       :onDragMove (fn [e]
                     (onChange
                      {:id id,
                       :x (.x (.-target e)),
                       :y (.y (.-target e))}))
       :onTransformEnd (fn []
                         (let [node ^js @shapeRef
                               scaleX (.scaleX node)
                               scaleY (.scaleY node)]
                           (.scaleX node 1)
                           (.scaleY node 1)
                           (onChange
                            {:x (.x node),
                             :y (.y node),
                             :width (* (.width node) scaleX)
                             :height (* (.height node) scaleY)})))})
     (when selected?
       (konva/Transformer
        {:ref #(reset! trRef %)
         :boundBoxFunc (fn [old-box,new-box]
                         (if (or (< (.-width new-box) 5) (< (.-height new-box) 5))
                           old-box
                           new-box))})))))


(defnc RenderTransform []
  (let [[rectangles,set-rectangles!] (hooks/use-state
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
        [selected select!] (hooks/use-state nil)]
    (<>
     (konva/Stage
      {:width (.-innerWidth js/window),
       :height (.-innerHeight js/window),
       :onMouseDown (fn [^js e]
                      (when (= (.-target e) (.. e -target -getStage))
                        (select! nil)))}
      (konva/Layer
       (map
        (fn [{:keys [id] :as rect}]
          ($ Rectangle
             {:shapeProps rect
              :selected? (= id selected)
              :onSelect (fn [] (select! id))
              :onChange (fn [delta]
                          (set-rectangles!
                           update (.indexOf rectangles rect)
                           merge delta))}))
        rectangles))))))

; Predefined image transformer - resize, rotate, move
; Image properties should be saved in state (images)

(defnc ImageRender
  [{:keys [selected onSelect onChange]
    {:keys [image width height]} :shapeProps}]
  (let [shapeRef (hooks/use-ref nil)
        trRef (hooks/use-ref nil)
        selected? (= selected @shapeRef)]
    (hooks/use-effect
     [selected?]
     (when selected?
       (.nodes ^js  @trRef #js [@shapeRef])
       (.batchDraw ^js (.getLayer @trRef))))
    (<>
     (konva/Image
      {:image image
       :width width,
       :height height,
       :onClick (fn [e] (onSelect (.. e -target))),
       :ref #(reset! shapeRef %),
       :draggable true,
       :onDragMove (fn [e]
                     (let [node ^js @shapeRef]
                       (onChange
                        {:width (.width node),
                         :height (.height node),
                         :x (.x (.-target e)),
                         :y (.y (.-target e))})))
       :onTransformEnd (fn []
                         (let [node ^js @shapeRef]
                           (.scaleX ^js node 1)
                           (.scaleY ^js node 1)
                           (onChange
                            {:x (.x node),
                             :y (.y node),
                             :width (* (.width node) (.scaleX node))
                             :height (* (.height node) (.scaleY node))})))})
     (when selected?
       (konva/Transformer
        {:ref #(reset! trRef %)
         :boundBoxFunc (fn [old-box, new-box]
                         (if (or (< (.-width new-box) 5) (< (.-height new-box) 5))
                           old-box new-box))})))))


(defnc ImageTransformer []
  (let [[image-load] (use-image "https://konvajs.org/assets/yoda.jpg" "anonymous")
        [images set-images] (hooks/use-state [])
        [selected select!] (hooks/use-state nil)]
    (hooks/use-effect
     [image-load]
     (.log js/console image-load)
     (set-images [{:image image-load}]))
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

       (map
        (fn [imgg]
          (.log js/console imgg)
          ($ ImageRender
             {:shapeProps imgg
              :selected selected
              :onSelect (fn [ref] (select! ref))
              :onChange (fn [image]
                          #_(.log js/console (nth rectangles 1))
                          (set-images update (.indexOf images imgg) merge image))}))
        images))))))

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
     (d/button
      {:onClick (fn []
                  (.log js/console (.toDataURL (.-current stageRef))))}
      (str "Click to log URI"))
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
     (d/div
      {:onDrop (fn [e]
                 (.preventDefault e)
                 (.setPointersPositions (.-current stageRef) e)
                 (setImages
                  (conj images {:x (.-x (.getPointerPosition (.-current stageRef))),
                                :y (.-y (.getPointerPosition (.-current stageRef))),
                                :src (.-current dragURL)}))),
       :onDragOver (fn [e] (.preventDefault e))}
      (konva/Stage
       {:width (.-innerWidth js/window),
        :height (.-innerHeight js/window),
        :style {:border "1px solid grey"},
        :ref #(reset! stageRef %)}
       (konva/Layer
        (when (not-empty images)
          (map
           (fn [image]
             ($ URLImage {:image image}))
           images))))))))


; Undo/redo position of objects


(defnc UndoRedo []
  (let [[history setHistory] (hooks/use-state
                              [{:x 20
                                :y 20}])
        #_(historyStep (atom 0))
        [historyStep setHistoryStep] (hooks/use-state 0)

        [state setState] (hooks/use-state (nth history 0))]
    (.log js/console)
    (konva/Stage
     {:width (.-innerWidth js/window),
      :height (.-innerHeight js/window)}
     (konva/Layer
      (konva/Text {:text "Undo",
                   :onClick
                   (fn []
                     (when (not= historyStep 0)
                       (let [previous (nth history (- historyStep 1))]
                         (setHistoryStep (- historyStep 1))
                         (setState previous))))})
      (konva/Text {:text "Redo",
                   :x 50,
                   :onClick
                   (fn []
                     (when (not= historyStep (- (count history) 1))
                       (let [next (nth history (+ historyStep 1))]
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

(defstyled slider-css "div"
  {".slidercontainer" {:width "25%"},
   ".slider" {:-webkit-appearance "none",
              :appearance "none",
              :width "75%",
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
     ($ slider-css
        (d/div
         {:className "slidercontainer"}
         (d/label
          {:for "simple-slider",
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
         (d/label
          {:for "simple-slider"
           :className "label"}
          (str "  " state "%")))))))

; Simple avatar editor (combined image export and transform) - rotate, drag, zoom

(defnc avatar-render
  [{:keys [selected onSelect onChange]
    {:keys [width image] :as avatar} :avatar}]
  (let [shape-ref (hooks/use-ref nil)
        tr-ref (hooks/use-ref nil)
        selected? (and
                   (some? @shape-ref)
                   (= selected @shape-ref))
        panorama? (when image (> (.-width image) (.-height image)))]
    (hooks/use-effect
     [image selected?]
     (when (and selected? image)
       (.nodes ^js  @tr-ref #js [@shape-ref])
       (.batchDraw ^js (.getLayer @tr-ref))))
    (hooks/use-effect
     [image]
     (when (and
            (not selected?)
            (some? image)
            (some? @shape-ref))
       (onSelect @shape-ref)))
    (when image
      (<>
       (konva/Image
        {:& avatar
         :offsetX (if image (/ (.-width image) 2) 0)
         :offsetY (if image (/ (.-height image) 2) 0)
         :x (if image 250 0)
         :y (if image 250 0)
         :scaleX (cond
                   (nil? image) 0
                   panorama? (/ 500 (.-width image))
                   :else (/ 500 (.-height image)))
         :scaleY (cond
                   (nil? image) 0
                   panorama? (/ 500 (.-width image))
                   :else (/ 500 (.-height image)))
         :onClick (fn [e] (onSelect (.. e -target)))
         :ref #(reset! shape-ref %)
         :draggable true
         :rotation 0
         :onDragMove (fn [e]
                       (let [node ^js @shape-ref]
                         (onChange
                          {:width (.width node)
                           :height (.height node)
                           :x (.x (.-target e))
                           :y (.y (.-target e))})))
         :onTransformEnd (fn []
                           (let [node ^js @shape-ref
                                 scaleX (.scaleX node)
                                 scaleY (.scaleY node)]
                             (.scaleX node 1)
                             (.scaleY node 1)
                             (onChange {:x (.x node)
                                        :y (.y node)
                                        :width (max 10 (* (.width node) scaleX))
                                        :height (max 10 (* (.height node) scaleY))
                                        :offsetX (/ (max 10 (* (.width node) scaleX)) 2)
                                        :offsetY (/ (max 10 (* (.height node) scaleY)) 2)})))})
       (when selected?
         (konva/Transformer
          {:ref #(reset! tr-ref %),
           :centeredScaling true,
           :rotateEnabled false,
           :boundBoxFunc (fn [old-box, new-box]
                           (if (or (< (.-width new-box) 5) (< (.-height new-box) 5))
                             old-box new-box))}))))))



; vertical image https://upload.wikimedia.org/wikipedia/commons/thumb/b/bf/Polignano_a_Mare_-_Isola_di_San_Paolo_-_startrail.png/800px-Polignano_a_Mare_-_Isola_di_San_Paolo_-_startrail.png
; large horizontal image https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1920px-Image_created_with_a_mobile_phone.png
; small (yoda) image https://konvajs.org/assets/yoda.jpg


(defn download-uri [uri, name, mime]
  (let [link (doto (.createElement js/document "a")
               (set! -download name)
               (set! -href uri)
               (set! -type mime))]
    (.appendChild (.-body js/document) link)
    (.click link)
    (.removeChild (.-body js/document) link)))

(defn handle-scroll
  [^js e, layer-zoom,  set-layer-zoom]
  (let [scale-by 1.02
        old-scale layer-zoom
        new-scale (if (< (.-deltaY (.-evt e)) 0)
                    (* old-scale scale-by)
                    (/ old-scale scale-by))]
    (set-layer-zoom new-scale)))

(defstyled button-css "div"
  {".button" {:background-color "teal"
              :border "2px solid white"
              :color "white"
              :padding "10px 24px"
              :text-align "center"
              :text-decoration "none"
              :font-size "15px"
              :transition-duration "0.3s"
              :margin "1px"
              :border-radius "5px"}
   ".button:hover" {:background-color "white"
                    :color "teal"
                    :border "2px solid teal"}})

(defnc avatar-editor []
  (let [[image-load] (use-image "https://upload.wikimedia.org/wikipedia/commons/thumb/b/b6/Image_created_with_a_mobile_phone.png/1920px-Image_created_with_a_mobile_phone.png" "anonymous")
        [{:keys [image rotation] :as avatar
          :or {rotation 0}} set-avatar!] (hooks/use-state nil)
        [selected select!] (hooks/use-state nil)
        stage-ref (hooks/use-ref nil)
        [layer-zoom set-layer-zoom] (hooks/use-state 0.5)]
    (hooks/use-effect
     [image-load]
     (set-avatar! {:image image-load}))
    (<>
     (d/div
      {:style {:width "501px"}}
      (konva/Stage
       {:width 500
        :height 500
        :style {:border "1px solid black"}
        :ref #(reset! stage-ref %)
        :onMouseDown (fn [^js e]
                       (when (= (.-target e) (.getStage (.-target e)))
                         (select! nil)))}
       (konva/Layer
        {:x 250
         :y 250
         :offsetX 250
         :offsetY 250
         :scaleX layer-zoom
         :scaleY layer-zoom
         :onWheel (fn [e] (handle-scroll e layer-zoom set-layer-zoom))}
        ($ avatar-render
           {:avatar avatar
            :selected selected
            :onSelect (fn [ref] (select! ref))
            :onChange (fn [delta] (set-avatar! merge delta))}))
       (konva/Layer
        (konva/Rect
         {:x -50
          :y -50
          :visible true
          :cornerRadius 200
          :listening false
          :width 600
          :height 600
          :strokeWidth 130,
          :stroke "#000000bb"
          :style {:z-index "10"}}))))
     ($ button-css
        (d/button
         {:className "button"
          :onClick (fn []
                     (let [tr-elements (.-children (nth (.-children (.-current stage-ref)) 0))
                           rect-elements (.-children (nth (.-children (.-current stage-ref)) 1))]
                       (cond (= (count tr-elements) 2)
                             (.hide (nth tr-elements 1)))
                       (.hide (nth rect-elements 0))
                       (download-uri (.toDataURL (.-current stage-ref)), "user-avatar.png", "image/png")
                       (.log js/console (.toDataURL (.-current stage-ref)))
                       (.show (nth rect-elements 0))
                       (cond (= (count tr-elements) 2)
                             (.show (nth tr-elements 1)))))}
         (str "Download/log URI"))
        (d/button
         {:className "button"
          :onClick (fn []
                     (set-avatar! merge
                                  {:image image-load
                                   #_:offsetX #_(if (= image-load js/undefined) 0
                                                    (/ (.-width image-load) 2))
                                   #_:offsetY #_(if (= image-load js/undefined) 0
                                                    (/ (.-height image-load) 2))

                                   :rotation 0
                                   #_:width #_250 #_(.-width image-load)
                                   #_:height #_250 #_(.-height image-load)
                                   :scaleX (cond
                                             (> (.-width image-load) (.-height image-load)) (/ 500 (.-width image-load))
                                             :else (/ 500 (.-height image-load)))
                                   :scaleY (cond
                                             (> (.-width image-load) (.-height image-load)) (/ 500 (.-width image-load))
                                             :else (/ 500 (.-height image-load)))
                                   :x (if (= image-load js/undefined) 0
                                          250)
                                   :y (if (= image-load js/undefined) 0
                                          250)
                       ; manually calculated for stage 500x500 (numberOfPixels / 2)
                                   })
                     (set-layer-zoom 0.5))}
         (str "Re-center"))
        (d/br)
        (d/br)
        (d/button
         {:className "button"
          :onClick (fn []
                     (set-avatar! assoc :rotation (if (> (- rotation 90) 0) (- rotation 90) 0)))}
         "Left")
        (d/button
         {:className "button"
          :onClick (fn []
                     (set-avatar! assoc :rotation (if (< (+ rotation 90) 360) (+ rotation 90) 360)))}
         "Right"))

     ($ slider-css
        (d/br)
        (d/div
         {:className "slidercontainer"}
         (d/label
          {:for "rotation-slider",
           :className "label"}
          "Rotation  ")
         (d/input
          {:id "rotation-slider",
           :name "rotation-slider",
           :type "range",
           :min "0",
           :max "360",
           :value rotation,
           :className "slider",
           :style {:width "70%"}
           :onChange (fn [e] (set-avatar! assoc :rotation (or (int (.-value (.-target e))) 0)))})
         (d/label
          {:for "rotation-slider"
           :className "label"} (str "  " rotation "°")))
        (d/br)
        (d/div
         {:className "slidercontainer"}
         (d/label
          {:for "zoom-slider",
           :className "label"}
          (str "Zooming   "))
         (d/input
          {:id "zoom-slider",
           :name "zoom-slider",
           :type "range",
           :min "0.01",
           :max "3",
           :step 0.01,
           :value layer-zoom,
           :className "slider",
           :style {:width "70%"}
           :onChange (fn [e] (set-layer-zoom (.-value (.-target e))))})
         (d/label
          {:for "zoom-slider"
           :className "label"}
          (str "  " (int (* layer-zoom 100)) "%")))))))
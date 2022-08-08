(ns helix.virtual.main
  (:require
   [clojure.core.async :as async]
   [cljs-bean.core :refer [->js]] ;;https://github.com/mfikes/cljs-bean/blob/master/src/cljs_bean/core.cljs - ugrađeno nešto - mi to tu koristimo
   [helix.core :refer [defnc $ <>]]
   [helix.dom :as d]
   [helix.hooks :as hooks]
   [helix.virtual :refer [use-virtualizer, use-window-virtualizer]]
   [helix.styled-components :refer [defstyled]]))


;
;
; TANSTACK REACT VIRTUALIZER EXAMPLES
;
;

; Global CSS

(defstyled centeredContent "div"
  {:margin "auto",
   :width "50%",
   :textAlign "center"})

;
; FIXED VIRTUALIZER
;

; CSS for fixed

(defstyled FixedRowCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "400px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative",
                    :width "100%"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :width "100%"}})

(defstyled FixedColumnCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "100px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative",
                    :height "100%"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :height "100%"}})

(defstyled FixedGridCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "400px",
                     :width "100%",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"}
   ".innerElement" {:position "relative"}
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0}})


; Fixed Rows

(defnc FixedRows []
  (let [parentRef (hooks/use-ref nil)
        rowVirtualizer (use-virtualizer
                        {:count 10000,
                         :getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [] 35)
                         :overscan 5})]
    (<>
     ($ centeredContent
        ($ FixedRowCSS
           (d/h2 (str "Fixed rows"))
           (d/h4 "Visina redaka je uvijek jednaka te se određuje pri inicijalizaciji virtualizera")
           (d/div {:className "scrollElement"
                   :ref #(reset! parentRef %)}
                  (d/div
                   {:className "innerElement",
                    :style {:height (str (.getTotalSize rowVirtualizer) "px")}}
                   (map
                    (fn [^js virtualItem]
                      (d/div
                       {:className "visibleItems",
                        :key (.-index virtualItem),
                        :style {:height (str (.-size virtualItem) "px"),
                                :transform (str "translateY(" (.-start virtualItem) "px)")}}
                       (str "Row " (.-index virtualItem))))
                    (.getVirtualItems rowVirtualizer)))))))))

; Fixed columns

(defnc FixedColumns []
  (let [parentRef (hooks/use-ref nil)
        columnVirualizer (use-virtualizer
                          {:horizontal true,
                           :count 10000,
                           :getScrollElement (fn [] @parentRef),
                           :estimateSize (fn [] 100)
                           :overscan 5})]
    (<>
     ($ centeredContent
        ($ FixedColumnCSS
           (d/h2 (str "Fixed columns"))
           (d/h4 "Širina stupaca je uvijek jednaka te se određuje pri inicijalizaciji virtualizera")
           (d/div {:ref #(reset! parentRef %)
                   :className "scrollElement"}
                  (d/div
                   {:style {:width (str (.getTotalSize columnVirualizer) "px")},
                    :className "innerElement"}
                   (map
                    (fn [^js virtualItem]
                      (d/div
                       {:key (.-index virtualItem),
                        :style {:width (str (.-size virtualItem) "px"),
                                :transform (str "translateX(" (.-start virtualItem) "px)")},
                        :className "visibleItems"}
                       (str "Column " (.-index virtualItem))))
                    (.getVirtualItems columnVirualizer)))))))))


; Fixed grid

(defnc FixedGrid []
  (let [parentRef (hooks/use-ref nil)
        columnVirualizer (use-virtualizer
                          {:horizontal true,
                           :count 10000,
                           :getScrollElement (fn [] @parentRef),
                           :estimateSize (fn [] 100)
                           :overscan 5})
        rowVirtualizer (use-virtualizer
                        {:count 10000,
                         :getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [] 35)
                         :overscan 5})]
    (<>
     ($ centeredContent
        ($ FixedGridCSS
           (d/h2 (str "Fixed grid"))
           (d/h4 "Visina redaka i širina stupaca su uvijek jednake 
                  te se određuju pri inicijalizaciji virtualizera")
           (d/div {:ref #(reset! parentRef %)
                   :className "scrollElement"}
                  (d/div
                   {:className "innerElement"
                    :style {:width (str (.getTotalSize columnVirualizer) "px"),
                            :height (str (.getTotalSize rowVirtualizer) "px")}}
                   (map
                    (fn [^js virtualRow]
                      < {:key (.-index virtualRow)} >
                      (map
                       (fn [^js virtualColumn]
                         (d/div
                          {:key (.-index virtualColumn),
                           :className "visibleItems",
                           :style {:height (str (.-size virtualRow) "px"),
                                   :width (str (.-size virtualColumn) "px"),
                                   :transform (str "translateX(" (.-start virtualColumn)
                                                   "px) translateY(" (.-start virtualRow) "px)")}}
                          (str "Grid " (.-index virtualRow) ", " (.-index virtualColumn))))
                       (.getVirtualItems columnVirualizer)))
                    (.getVirtualItems rowVirtualizer)))))))))

;
; VARIABLE VIRTUALIZER
;

; CSS for variable

(defstyled VariableRowCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "400px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative",
                    :width "100%"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :width "100%"}})

(defstyled VariableColumnCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "100px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative",
                    :height "100%"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :height "100%"}})

(defstyled VariableGridCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "400px",
                     :width "100%",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"}
   ".innerElement" {:position "relative"}
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0}})

; Variable rows

(defnc VariableRows []
  (let [parentRef (hooks/use-ref nil)
        rows (repeatedly 10000  #(+ 25 (rand-int 75)))
        rowVirtualizer (use-virtualizer
                        {:count (count rows),
                         :getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [i] (nth rows i))
                         :overscan 5})]
    (<>
     ($ centeredContent
        ($ VariableRowCSS
           (d/h2 (str "Variable rows"))
           (d/h4 "Visina redaka ovisi o potrebnoj visini, ali se 
                  određuje pri inicijalizaciji virtualizera (dug loading)")
           (d/div {:ref #(reset! parentRef %),
                   :className "scrollElement"}
                  (d/div
                   {:style {:height (str (.getTotalSize rowVirtualizer) "px")},
                    :className "innerElement"}
                   (map
                    (fn [^js virtualItem]
                      (d/div
                       {:key (.-index virtualItem),
                        :className "visibleItems",
                        :style {:height (str (.-size virtualItem) "px"),
                                :transform (str "translateY(" (.-start virtualItem) "px)")}}
                       (str "Row " (.-index virtualItem))))
                    (.getVirtualItems rowVirtualizer)))))))))


; Variable columns

(defnc VariableColumns []
  (let [parentRef (hooks/use-ref nil)
        columns (repeatedly 10000  #(+ 75 (rand-int 50)))
        columnVirualizer (use-virtualizer
                          {:horizontal true,
                           :count (count columns),
                           :getScrollElement (fn [] @parentRef),
                           :estimateSize (fn [i] (nth columns i))
                           :overscan 5})]
    (<>
     ($ centeredContent
        ($ VariableColumnCSS
           (d/h2 (str "Variable columns"))
           (d/h4 "Širina stupaca ovisi o potrebnoj širini, ali se 
                  određuje pri inicijalizaciji virtualizera (dug loading)")
           (d/div {:ref #(reset! parentRef %)
                   :className "scrollElement"}
                  (d/div
                   {:style {:width (str (.getTotalSize columnVirualizer) "px")},
                    :className "innerElement"}
                   (map
                    (fn [^js virtualItem]
                      (d/div
                       {:key (.-index virtualItem),
                        :className "visibleItems"
                        :style {:width (str (.-size virtualItem) "px"),
                                :transform (str "translateX(" (.-start virtualItem) "px)")}}
                       (str "Column " (.-index virtualItem))))
                    (.getVirtualItems columnVirualizer)))))))))


; Variable grid

(defnc VariableGrid []
  (let [parentRef (hooks/use-ref nil)
        rows (repeatedly 10000  #(+ 25 (rand-int 75)))
        columns (repeatedly 10000  #(+ 75 (rand-int 50)))
        columnVirualizer (use-virtualizer
                          {:horizontal true,
                           :count (count columns),
                           :getScrollElement (fn [] @parentRef),
                           :estimateSize (fn [i] (nth columns i))
                           :overscan 5})
        rowVirtualizer (use-virtualizer
                        {:count (count rows),
                         :getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [i] (nth rows i))
                         :overscan 5})]
    (<>
     ($ centeredContent
        ($ VariableGridCSS
           (d/h2 (str "Variable grid"))
           (d/h4 "Visina redaka i širina stupaca ovise o potrebnim veličinama, 
                  ali se određuju pri inicijalizaciji virtualizera (dug loading)")
           (d/div {:ref #(reset! parentRef %)
                   :className "scrollElement"}
                  (d/div
                   {:style {:width (str (.getTotalSize columnVirualizer) "px"),
                            :height (str (.getTotalSize rowVirtualizer) "px")},
                    :className "innerElement"}
                   (map
                    (fn [virtualRow]
                      < {:key (.-index virtualRow)} >
                      (map
                       (fn [^js virtualColumn]
                         (d/div
                          {:key (.-index virtualColumn),
                           :className "visibleItems",
                           :style {:height (str (.-size virtualRow) "px"),
                                   :width (str (.-size virtualColumn) "px"),
                                   :transform (str "translateX(" (.-start virtualColumn)
                                                   "px) translateY(" (.-start virtualRow) "px)")}}
                          (str "Grid " (.-index virtualRow) ", " (.-index virtualColumn))))
                       (.getVirtualItems columnVirualizer)))
                    (.getVirtualItems rowVirtualizer)))))))))


;
; DYNAMIC VIRTUALIZER
;

; Dynamic CSS

(defstyled DynamicRowCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "400px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:width "100%",
                    :position "relative"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :width "100%"}})

(defstyled DynamicColumnCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "100px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative",
                    :height "100%"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :height "100%"}})

(defstyled DynamicGridCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "400px",
                     :width "100%",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0}})

; Dynamic rows

(defnc DynamicRows []
  (let [parentRef (hooks/use-ref nil)
        [rows] (hooks/use-state (repeatedly 10000  #(+ 25 (rand-int 75))))
        rowVirtualizer (use-virtualizer
                        {:count (count rows),
                         :getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [] 125)})]
    (<>
     ($ centeredContent
        ($ DynamicRowCSS
           (d/h2 (str "Dynamic rows"))
           (d/h4 "Visina redaka ovisi o potrebnoj visini i visina se 
                  određuje prije samog renderanja elementa")
           (d/div {:ref #(reset! parentRef %),
                   :className "scrollElement"}
                  (d/div
                   {:className "innerElement",
                    :style {:height (.getTotalSize rowVirtualizer)}}
                   (map
                    (fn [^js virtualItem]
                      (d/div
                       {:key (.-index virtualItem),
                        :ref (.-measureElement virtualItem),
                        :className "visibleItems",
                        :style {:transform (str "translateY(" (.-start virtualItem) "px)")}}
                       (d/div {:style {:height (str (nth rows (.-index virtualItem)) "px")}}
                              (str "Row " (.-index virtualItem)))))
                    (.getVirtualItems rowVirtualizer)))))))))


; Dynamic columns

(defnc DynamicColumns []
  (let [parentRef (hooks/use-ref nil)
        [columns] (hooks/use-state (repeatedly 10000  #(+ 50 (rand-int 50))))
        columnVirualizer (use-virtualizer
                          {:horizontal true,
                           :count (count columns),
                           :getScrollElement (fn [] @parentRef),
                           :estimateSize (fn [] 125)})]
    (<>
     ($ centeredContent
        ($ DynamicColumnCSS
           (d/h2 (str "Dynamic columns"))
           (d/h4 "Širina stupaca ovisi o potrebnoj širini i širina se 
                  određuje prije samog renderanja elementa")
           (d/div {:ref #(reset! parentRef %),
                   :className "scrollElement"}
                  (d/div
                   {:style {:width (.getTotalSize columnVirualizer)},
                    :className "innerElement"}
                   (map
                    (fn [^js virtualItem]
                      (d/div
                       {:key (.-index virtualItem),
                        :ref (.-measureElement virtualItem),
                        :className "visibleItems",
                        :style {:transform (str "translateX(" (.-start virtualItem) "px)")}}
                       (d/div {:style {:width (nth columns (.-index virtualItem))}}
                              (str "Column " (.-index virtualItem)))))
                    (.getVirtualItems columnVirualizer)))))))))


; Dynamic grid

(defnc DynamicGrid []
  (let [parentRef (hooks/use-ref nil)
        [rows] (hooks/use-state (repeatedly 10000  #(+ 25 (rand-int 75))))
        [columns] (hooks/use-state (repeatedly 10000  #(+ 75 (rand-int 50))))
        columnVirualizer (use-virtualizer
                          {:horizontal true,
                           :count (count columns),
                           :getScrollElement (fn [] @parentRef),
                           :estimateSize (fn [] 125)})
        rowVirtualizer (use-virtualizer
                        {:count (count rows),
                         :getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [] 125)})]
    (<>
     ($ centeredContent
        ($ DynamicRowCSS
           (d/h2 (str "Dynamic grid"))
           (d/h4 "Visina redaka i širina stupaca ovisi o potrebnoj visini i širini, 
                  te se određuju prije samog renderanja elementa")
           (d/div {:ref #(reset! parentRef %),
                   :className "scrollElement"}
                  (d/div
                   {:style {:width (.getTotalSize columnVirualizer),
                            :height (.getTotalSize rowVirtualizer)},
                    :className "innerElement"}
                   (map
                    (fn [^js  virtualRow]
                      < {:key (.-index virtualRow)} >
                      (map
                       (fn [^js  virtualColumn]
                         (d/div
                          {:key (.-index virtualColumn),
                           :ref (fn [el] (.measureElement virtualRow el)
                                  (.measureElement virtualColumn el)),
                           :className "visibleItems",
                           :style {:transform (str "translateX(" (.-start virtualColumn)
                                                   "px) translateY(" (.-start virtualRow) "px)"),
                                   :height (nth rows (.-index virtualRow)),
                                   :width (nth columns (.-index virtualColumn))}}
                          (str "Grid " (.-index virtualRow) ", " (.-index virtualColumn))))
                       (.getVirtualItems columnVirualizer)))
                    (.getVirtualItems rowVirtualizer)))))))))


;
; WINDOW VIRTUALIZER
;

; Window CSS

(defstyled WindowRowsCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:margin "auto",
                     :border "3px solid teal",
                     :padding "10px",
                     :overflow "auto"},
   ".innerElement" {:width "100%",
                    :position "relative"},
   ".visibleItems" {:border "1px solid teal",
                    :position "aboslute",
                    :top 0,
                    :left 0,
                    :width "100%"}})

(defstyled WindowColumnsCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:height "200px",
                     :overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal"},
   ".innerElement" {:position "relative",
                    :height "100%"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0,
                    :height "100%"}})

(defstyled WindowGridCSS "div"
  {:height "100%",
   :width "100%",
   ".scrollElement" {:overflow "auto",
                     :margin "auto",
                     :padding "10px",
                     :border "3px solid teal",
                     :height "100%"},
   ".innerElement" {:position "relative"},
   ".visibleItems" {:border "1px solid teal",
                    :position "absolute",
                    :top 0,
                    :left 0}})

; Window rows

(defnc WindowRows []
  (let [parentRef (hooks/use-ref nil)
        windowRef (hooks/use-ref "window")
        windowVirtualizer (use-window-virtualizer
                           {:estimateSize (fn [] 35),
                            :count 10000,
                            :overscan 5,
                            :parentRef @parentRef,
                            :windowRef @windowRef})]

    ($ WindowRowsCSS
       (d/div {:className "scrollElement",
               :ref #(reset! parentRef %)}
              (d/div {:className "innerElement",
                      :style {:height (str (.getTotalSize windowVirtualizer) "px")}}
                     (map
                      (fn [^js virtualItem]
                        (d/div {:ref (.-measureElement virtualItem),
                                :key (.-key virtualItem),
                                :className "visibleItems",
                                :style {:height (str (.-size virtualItem) "px"),
                                        :transform (str "translateY(" (.-start virtualItem) "px)")}}
                               (str "Row " (.-index virtualItem)
                                    " i neki dugacki tekst koji je dugacak i proteze se kroz cijeli
                                    ekran ali nije lorem ipsum, nije posto ovo nije fejk latinski,
                                    i da cini mi se da tekst ipak nije dosta dugacak pa aj evo
                                    nek bude jos malo rijeci")))
                      (.getVirtualItems windowVirtualizer)))))))

; Window columns
; Ne radi dobro ni u Reactu, mozda ima logike zas ne radi (window se scrolla prema dolje ne u stranu)

(defnc WindowColumns []
  (let [parentRef (hooks/use-ref nil)
        windowRef (hooks/use-ref "window")
        windowVirtualizer (use-window-virtualizer
                           {:estimateSize (fn [] 125),
                            :count 10000,
                            :overscan 5,
                            :horizontal true,
                            :parentRef @parentRef,
                            :windowRef @windowRef})]

    ($ WindowColumnsCSS
       (d/div {:className "scrollElement",
               :ref #(reset! parentRef %)}
              (d/div {:className "innerElement",
                      :style {:width (str (.getTotalSize windowVirtualizer) "px")}}
                     (map
                      (fn [^js virtualItem]
                        (d/div {:ref (.-measureElement virtualItem),
                                :key (.-key virtualItem),
                                :className "visibleItems",
                                :style {:width (str (.-size virtualItem) "px"),
                                        :transform (str "translateX(" (.-start virtualItem) "px)")}}
                               (str "Column " (.-index virtualItem))))
                      (.getVirtualItems windowVirtualizer)))))))

; Window grid
; Isti problem kao i prije, ne radi za skrolanje stupaca

(defnc WindowGrid []
  (let [rowVirtualizer (use-window-virtualizer
                        {:estimateSize (fn [] 35),
                         :count 10000,
                         :overscan 5})
        columnVirtualizer (use-window-virtualizer
                           {:estimateSize (fn [] 125),
                            :count 10000,
                            :overscan 5,
                            :horizontal true})]
    ($ WindowGridCSS
       (d/div {:className "scrollElement"}
              (d/div {:className "innerElement",
                      :style {:width (str (.getTotalSize columnVirtualizer) "px"),
                              :height (str (.getTotalSize rowVirtualizer) "px")}}
                     (map
                      (fn [^js virtualRow]
                        < {:key (.-index virtualRow)} >
                        (map
                         (fn [^js virtualColumn]
                           (d/div {:key (.-index virtualColumn),
                                   :className "visibleItems",
                                   :style {:width (str (.-size virtualColumn) "px"),
                                           :height (str (.-size virtualRow) "px"),
                                           :transform (str "translateX(" (.-start virtualColumn)
                                                           "px) translateY(" (.-start virtualRow) "px)")}}
                                  (str "Grid " (.-index virtualRow) ", " (.-index virtualColumn))))
                         (.getVirtualItems columnVirtualizer)))
                      (.getVirtualItems rowVirtualizer)))))))


;
; TABLES
;

; CSS used for both tables

(defstyled tableCSS "div"
  {:height "100%",
   :width "100%",
   ".outerElement" {:margin "auto",
                    :width "50%",
                    :textAlign "center"},

   ".innerElement" {:height "500px",
                    :overflow "auto",
                    :border "1px solid teal"},

   "*, *:before, *:after" {:box-sizing "border-box"},

   "body" {:font-family "Arial, Helvetica, sans-serif",
           :margin 0},

   ".table" {:width "100%",
             :height "100%",
             :font-family "Arial, Helvetica, sans-serif"},

   ".table-header" {:background "teal",
                    :color "#FFFFFFF",
                    :position "relative"},

   ".table-row" {:display "flex",
                 :width "100%"

                 "&:nth-of-type(odd)" {:background "#EEEEEE"}},

   ".table-data, .header__item" {:text-align "center"},

   ".table-data" {:position "absolute",
                  :border "1px solid grey"}

   ".header__item" {:text-transform "uppercase",
                    :color "white",
                    :positon "absolute"}})

; Fixed table

(defnc FixedTable []
  (let [parentRef (hooks/use-ref nil)
        rowVirtualizer (use-virtualizer
                        {:getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [] 20),
                         :count 1000,
                         :overscan 5})
        columnVirtualizer (use-virtualizer
                           {:getScrollElement (fn [] @parentRef),
                            :estimateSize (fn [] 152),
                            :count 6,
                            :horizontal true,
                            :overscan 5})]
    ($ tableCSS
       ($ centeredContent
          (d/h2 "Fixed table")
          (d/h4 "Tablica u kojoj je svaki redak iste visine"))
       (d/div {:className "outerElement"}
              (d/div {:className "innerElement",
                      :ref #(reset! parentRef %)}
                     (d/table {:className "table"
                               :style {:height "100%",
                                       :width (str (.getTotalSize columnVirtualizer)  "px")}}
                              (d/thead {:className "table-header"
                                        :style {:width (str (.getTotalSize columnVirtualizer)  "px")}}
                                       (d/tr
                                        (map
                                         (fn [^js virtualColumn]
                                           (d/th {:key (.-index virtualColumn)
                                                  :className "header__item",
                                                  :style {:height "100%",
                                                          :width (str (.-size virtualColumn) "px")}}
                                                 (str "Header " (.-index virtualColumn))))
                                         (.getVirtualItems columnVirtualizer))))
                              (d/tbody {:className "table-content",
                                        :style {:width (str (.getTotalSize columnVirtualizer) "px"),
                                                :height (str (.getTotalSize rowVirtualizer) "px"),
                                                :position "relative"}}
                                       (map
                                        (fn [^js virtualRow]
                                          (d/tr {:key (.-index virtualRow),
                                                 :className "table-row"}
                                                (map
                                                 (fn [^js virtualColumn]
                                                   (d/td {:key (.-index virtualColumn),
                                                          :className "table-data",
                                                          :style {:width (str (.-size virtualColumn) "px"),
                                                                  :height (str (.-size virtualRow) "px"),
                                                                  :transform (str "translateX(" (.-start virtualColumn)
                                                                                  "px) translateY(" (.-start virtualRow) "px)")}}
                                                         (str "Data " (.-index virtualRow) ", " (.-index virtualColumn))))
                                                 (.getVirtualItems columnVirtualizer))))
                                        (.getVirtualItems rowVirtualizer)))))))))


; Dynamic table

(defnc DynamicTable []
  (let [parentRef (hooks/use-ref nil)
        [rows] (hooks/use-state (repeatedly 10000  #(+ 25 (rand-int 75))))
        #_[columns] #_(hooks/use-state (repeatedly 10 #(+ 100 (rand-int 50))))
        ; for creating randomly sized columns (if needed)
        rowVirtualizer (use-virtualizer
                        {:getScrollElement (fn [] @parentRef),
                         :estimateSize (fn [] 50),
                         :count (count rows),
                         :overscan 5})
        columnVirtualizer (use-virtualizer
                           {:getScrollElement (fn [] @parentRef),
                            :estimateSize (fn [] 152),
                            :count 6,
                            :horizontal true,
                            :overscan 5})]
    ($ tableCSS
       ($ centeredContent
          (d/h2 "Dynamic table")
          (d/h4 "Tablica u kojoj je svaki redak određene visine, 
                 visina se računa tijekom renderanja specifičnog elementa"))
       (d/div {:className "outerElement"}
              (d/div {:className "innerElement",
                      :ref #(reset! parentRef %)}
                     (d/table {:className "table"
                               :style {:width (str (.getTotalSize columnVirtualizer)  "px"),
                                       :height (str (.getTotalSize rowVirtualizer) "px")}}
                              (d/thead {:className "table-header"
                                        :style {:width (str (.getTotalSize columnVirtualizer "px"))}}
                                       (d/tr
                                        (map
                                         (fn [virtualColumn]
                                           (d/th {:key (.-index virtualColumn),
                                                  :className "header__item",
                                                  :style {:height "100%",
                                                          :width (str (.-size virtualColumn) "px")}}
                                                 (str "Header " (.-index virtualColumn))))
                                         (.getVirtualItems columnVirtualizer))))
                              (d/tbody {:className "table-content"
                                        :style {:width (str (.getTotalSize columnVirtualizer) "px"),
                                                :height (str (.getTotalSize rowVirtualizer) "px"),
                                                :position "relative"}}
                                       (map
                                        (fn [^js virtualRow]
                                          (d/tr {:className "table-row",
                                                 :key (.-index virtualRow)}
                                                (map
                                                 (fn [^js virtualColumn]
                                                   (d/td
                                                    {:key (.-index virtualColumn),
                                                     :className "table-data",
                                                     :ref (fn [el] (.measureElement virtualRow el)
                                                            (.measureElement virtualColumn el))
                                                     :style {:width (str (.-size virtualColumn) "px"),
                                                             :height (str (nth rows (.-index virtualRow)) "px"),
                                                             :transform (str "translateX(" (.-start virtualColumn)
                                                                             "px) translateY(" (.-start virtualRow) "px)")}}
                                                    (str "Data " (.-index virtualRow) ", " (.-index virtualColumn))))
                                                 (.getVirtualItems columnVirtualizer))))
                                        (.getVirtualItems rowVirtualizer)))))))))

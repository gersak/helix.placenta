(ns helix.spring.Accordion
  (:require
   ["react-dom" :as rdom]
   ["react-spring" :refer [useSpring useTransition config useSprings useTrail animated interpolate]]
   [clojure.core.async :as async]
   [cljs-bean.core :refer [->js ->clj]] ;;https://github.com/mfikes/cljs-bean/blob/master/src/cljs_bean/core.cljs - ugrađeno nešto - mi to tu koristimo
   [helix.core :refer [defnc $ <>]]
   [helix.dom :as d]
   [helix.spring :as spring]
   [helix.hooks :as hooks]
   ;["@mui/icons-material/ExpandMore" :refer ExpandButton]
   ))

(defnc Accordion [{:keys [props]}]
  (let [[open setOpen] (hooks/use-state false)
        toggleHandler (fn [e]
                        (setOpen not))
        styles {:accordionTitle {:color (if open "#10d6f5" "#fff")}}
        openAnimation (spring/use-spring
                       {:from {:opacity "0"
                               :maxHeight "25px"}
                        :to {:opacity "1"
                             :maxHeight (if open "200px" "25px")}
                        :config {:duration "300"}})
        iconAnimation (spring/use-spring
                       {:from {:transform "rotate(0def)"
                               :color "#ffff"}
                        :to {:transform (if open "rotate(180deg)" "rotate(0deg)")
                             :color (if open "#10d6f5" "#fff")}
                        :config {:duration "120"}})]
    (spring/div
     {:style openAnimation}
     (d/div-a
      :onClick toggleHandler
      (d/h4
       {:style (.-accordionTitle styles)}
       ;"TU BI TREBAO ICI PROPS TITLE - 44. LINIJA") 
       (.-title props)
      )
      (spring/i
       {:style iconAnimation}
       (d/ExpandButton)))
     (d/p
      ;"Ovo je neki tekst lalalla")
      (.-text props))
     )))
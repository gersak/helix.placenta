(ns helix.styled-components
  (:require-macros [helix.styled-components])
  (:require
    [helix.core :refer [defhook]]
    [helix.hooks :as hooks]
    ["styled-components"
     :refer [default createGlobalStyle ThemeProvider useTheme isStyledComponent]]))


(def styled default)
;; get-styled-constructor is required for advanced compilation
;; Don't remove if you aren't sure what you are doing
(defn get-styled-constructor [type] (default type))


(defn styled? [x] (isStyledComponent x))
(def global-style createGlobalStyle)
(def theme-provider ThemeProvider)
(declare defstyled)


(defmulti --themed
  (fn [{:keys [theme ::component]}]
    [theme component]))


(defmulti color (fn [theme name] [theme name]))


(defmethod color :default
  [theme name]
  (.warn js/console (str "Color " name " wasn't found for theme: " theme))
  "transparent")

(defhook use-color
  [color-name]
  (let [theme-name (useTheme)]
    (hooks/use-memo
      [theme-name color-name]
      (color theme-name color-name))))


(defmethod --themed :default
  [{:keys [theme ::component]}]
  (.warn js/console (str "Couldn't generate --themed mixin for [theme, component] " [theme  component])))

(ns helix.styled-components
  (:require-macros [helix.styled-components])
  (:require
    [helix.core :refer [defhook]]
    [helix.hooks :as hooks]
    helix.placenta.util
    ["styled-components"
     :refer [default createGlobalStyle ThemeProvider useTheme]
     :rename {default sc}]))


(def styled sc)
(def global-style createGlobalStyle)
(def theme-provider ThemeProvider)
(declare defstyled)


(defmulti --themed
  (fn [{:keys [theme helix.styled-components/component]}]
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
  [{:keys [theme helix.styled-components/component]}]
  (.warn js/console (str "Couldn't generate --themed mixin for [theme, component] " [theme  component])))

(ns helix.styled-components
  (:require-macros [helix.styled-components])
  (:require
    ["styled-components"
     :refer [default createGlobalStyle ThemeProvider]
     :rename {default sc}]))


(def styled sc)
(def global-style createGlobalStyle)
(def theme-provider ThemeProvider)
(declare defstyled)


(defmulti --themed
  (fn [{:keys [theme helix.styled-components/component]}]
    [theme component]))


(defmethod --themed :default
  [{:keys [theme helix.styled-components/component]}]
  (.warn js/console (str "Couldn't generate --themed mixin for [theme, component] " [theme  component])))

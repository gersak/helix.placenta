(ns helix.virtual
  (:refer-clojure :exclude [map meta time])
  (:require
   [helix.core :as hx]
   [helix.impl.props :as impl.props]
   [helix.hooks :as hooks]
   [cljs-bean.core :as b]
   ["@tanstack/react-virtual" :as virtual]))

; wrapper for @tanstack/react-virtual/useVirtualizer

(hx/defhook use-virtualizer
  "Like react-virtual/useVirtualizer, used to virtualize big amounts of data with an HTML element as the scrollElement."
  ([options]
   (virtual/useVirtualizer (b/->js options))))

; wrapper for @tanstack/react-virtual/useWindowVirtualizer

(hx/defhook use-window-virtualizer
  "Like react-virtual/useWindowVirtualizer, same as use-virtualizer but with the window as the scrollElement. "
  ([options]
   (virtual/useWindowVirtualizer (b/->js options))))

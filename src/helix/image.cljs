(ns helix.image
  (:refer-clojure :exclude [map meta time])
  (:require
   [helix.core :as hx]
   [helix.impl.props :as impl.props]
   [helix.hooks :as hooks]
   [cljs-bean.core :as b]
   ["use-image" :as image]))

(hx/defhook use-image
  "Like React useImage, except both of the arguments are required (url and crossOrigin)."
  ([url crossOrigin]
   (image url crossOrigin)))
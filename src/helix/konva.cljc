(ns helix.konva
  (:refer-clojure :exclude [map meta time])
  (:require
    [helix.core :as hx]
    [helix.impl.props :as impl.props]
    [helix.konva.js :as konva.js])
  #?(:cljs (:require-macros [helix.konva])))


(declare
  Animation
  Arc
  Arrow
  Canvas
  Circle
  Container
  Context
  Ellipse
  FastLayer
  Group
  Image
  Label
  Layer
  Line
  Node
  Path
  Rect
  RegularPolygon
  Ring
  Shape
  Sprite
  Stage
  Star
  Tag
  Text
  TextPath
  Transform
  Transformer
  Tween
  Wedge)

(def tags
  '[Animation
    Arc
    Arrow
    Canvas
    Circle
    Container
    Context
    Ellipse
    FastLayer
    Group
    Image
    Label
    Layer
    Line
    Node
    Path
    Rect
    RegularPolygon
    Ring
    Shape
    Sprite
    Stage
    Star
    Tag
    Text
    TextPath
    Transform
    Transformer
    Tween
    Wedge])

(defmacro $d
  [type & args]
  (if (map? (first args))
    `^js/React.Element (.createElement
                        (hx/get-react)
                        (konva.js/get-konva ~type)
                        (impl.props/dom-props ~(first args))
                        ~@(rest args))
    `^js/React.Element (.createElement
                        (hx/get-react)
                        (konva.js/get-konva ~type)
                        nil
                        ~@args)))


#?(:clj (defn gen-tag
          [tag]
          `(defmacro ~tag [& args#]
             `($d ~(str '~tag) ~@args#))))


#?(:clj (defmacro gen-tags
          []
          `(do
             ~@(for [tag tags] (gen-tag tag)))))


#?(:clj (gen-tags))

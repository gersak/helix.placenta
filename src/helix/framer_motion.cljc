(ns helix.framer-motion
  (:refer-clojure :exclude [map meta time])
  (:require
    #?(:cljs ["framer-motion" :as motion])
    [helix.core :as hx]
    [helix.impl.props :as impl.props]
    [helix.framer-motion.core :as fm])
  #?(:cljs (:require-macros [helix.framer-motion])))

(declare
 input textarea option select a abbr address area article aside audio b base bdi
 bdo big blockquote body br button canvas caption cite code col colgroup data datalist
 dd del details dfn dialog div dl dt em embed fieldset figcaption figure footer form
 h1 h2 h3 h4 h5 h6 head header hr html i iframe img ins kbd keygen label legend li link
 main map mark menu menuitem meta meter nav noscript object ol optgroup output p param
 picture pre progress q rp rt ruby s samp script section small source span strong style
 sub summary sup table tbody td tfoot th thead time title tr track u ul var video wbr
 circle clipPath ellipse g line mask path pattern polyline rect svg text defs
 linearGradient polygon radialGradient stop tspan)

(def tags
  '[input textarea option select
    a
    abbr
    address
    area
    article
    aside
    audio
    b
    base
    bdi
    bdo
    big
    blockquote
    body
    br
    button
    canvas
    caption
    cite
    code
    col
    colgroup
    data
    datalist
    dd
    del
    details
    dfn
    dialog
    div
    dl
    dt
    em
    embed
    fieldset
    figcaption
    figure
    footer
    form
    h1
    h2
    h3
    h4
    h5
    h6
    head
    header
    hr
    html
    i
    iframe
    img
    ins
    kbd
    keygen
    label
    legend
    li
    link
    main
    map
    mark
    menu
    menuitem
    meta
    meter
    nav
    noscript
    object
    ol
    optgroup
    output
    p
    param
    picture
    pre
    progress
    q
    rp
    rt
    ruby
    s
    samp
    script
    section
    small
    source
    span
    strong
    style
    sub
    summary
    sup
    table
    tbody
    td
    tfoot
    th
    thead
    time
    title
    tr
    track
    u
    ul
    var
    video
    wbr

    ;; svg
    circle
    clipPath
    ellipse
    g
    line
    mask
    path
    pattern
    polyline
    rect
    svg
    text
    defs
    linearGradient
    polygon
    radialGradient
    stop
    tspan])

(defn -dom-props
  ([m] #?(:clj (if-let [spread-sym (cond
                                     (contains? m '&) '&
                                     (contains? m :&) :&)]
                 `(helix.impl.props/merge-obj
                    ~(framer.motion/-dom-props (dissoc m spread-sym) (helix.impl.props/primitive-obj))
                    (-dom-props ~(get m spread-sym)))
                 (-dom-props m (helix.impl.props/primitive-obj)))
          :cljs (if (map? m)
                  (-dom-props m (helix.impl.props/primitive-obj))
                  ;; assume JS obj
                  m)))
  ([m o]
   (if (seq m)
     (recur (rest m)
            (let [entry (first m)
                  k (key entry)
                  v (val entry)]
              (case k
                :class (helix.impl.props/set-obj o "className" (helix.impl.props/normalize-class v))
                :for (helix.impl.props/set-obj o "htmlFor" v)
                :style (helix.impl.props/set-obj o "style" (helix.impl.props/dom-style v))
                :animate (helix.impl.props/set-obj o "animate" (helix.impl.props/dom-style v))
                :tranistion (helix.impl.props/set-obj o "transition" (helix.impl.props/dom-style v))
                :initial (helix.impl.props/set-obj o "initial" (helix.impl.props/dom-style v))
                :variants (helix.impl.props/set-obj o "variants" (helix.impl.props/dom-style v))
                :value (helix.impl.props/set-obj o "value" #?(:clj `(helix.impl.props/or-undefined ~v)
                                                              :cljs (helix.impl.props/or-undefined v)))
                (helix.impl.props/set-obj o (helix.impl.props/camel-case (helix.impl.props/kw->str k)) v))))
     #?(:clj (list* o)
        :cljs o))))

(defmacro dom-props [m]
  (-dom-props m))

(defmacro $d
  [type & args]
  (if (map? (first args))
    `^js/React.Element (.createElement
                        (hx/get-react)
                        (fm/get-motion ~type)
                        (framer.motion/dom-props ~(first args))
                        ~@(rest args))
    `^js/React.Element (.createElement
                        (hx/get-react)
                        (fm/get-motion ~type)
                        nil
                        ~@args)))


#?(:cljs (defonce AnimatePresence motion/AnimatePresence))
(comment
  (.log js/console AnimatePresence))


#?(:clj (defn gen-tag
          [tag]
          `(defmacro ~tag [& args#]
             `($d ~(str '~tag) ~@args#))))

#?(:clj (defmacro gen-tags
          []
          `(do
             ~@(for [tag tags]
                 (gen-tag tag)))))

#?(:clj (gen-tags))

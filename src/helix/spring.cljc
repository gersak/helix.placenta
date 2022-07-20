(ns helix.spring
  (:refer-clojure :exclude [map meta time])
  (:require
   [helix.core :as hx]
   [helix.impl.props :as impl.props]
   [helix.spring.js :as spring.js]
   #?(:cljs [helix.hooks :as hooks])
   #?(:cljs [cljs-bean.core :as b])
   #?(:cljs ["react-spring" :as spring])
   #?(:cljs ["react" :as react]))
  #?(:cljs (:require-macros [helix.spring])))

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

(defmacro $d
  [type & args]
  (if (map? (first args))
    `^js/React.Element (.createElement
                        (hx/get-react)
                        (spring.js/get-animated ~type)
                        (impl.props/dom-props ~(first args))
                        ~@(rest args))
    `^js/React.Element (.createElement
                        (hx/get-react)
                        (spring.js/get-animated ~type)
                        nil
                        ~@args)))

#?(:cljs (def animated spring/animated))


#?(:cljs
   (defn use-spring
     ([initial] (use-spring [] initial))
     ([deps initial]
      (let [[^js v ^js u] (cond
                            (fn? initial) (spring/useSpring (fn [] (b/->js (initial))))
                            (not-empty deps) (spring/useSpring (b/->js initial) (b/->js deps))
                            :else [(spring/useSpring (b/->js initial))])
            handles (hooks/use-memo
                     [u]
                     (when u
                       {:start (fn [props] (.start u (b/->js props)))
                        :stop (fn stop
                                ([] (.stop u))
                                ([ks] (.stop u (b/->js ks))))
                        :update #(.update u %)
                        :set (fn [x] (.set u (b/->js x)))
                        :pause (fn pause
                                 ([] (.pause u))
                                 ([ks] (.pause u (b/->js ks))))
                        :rasume (fn rasume
                                  ([] (.rasume u))
                                  ([ks] (.rasume u (b/->js ks))))}))
            updater (react/useCallback
                     (fn updater
                       ([x] (if (keyword? x)
                              (when-let [f (get handles x)] (f))
                              (u (b/->js x))))
                       ([f x1] ((get handles f (:set handles)) (b/->js x1)))
                       ([f x1 x2] ((get handles f (:set handles)) (b/->js x1) (b/->js x2))))
                     ;; `u` is guaranteed to be stable so we elide it
                     #js [u])]
        (if u [v updater] [v])))))


#?(:clj (defn gen-tag
          [tag]
          ; (println "GENERATING TAG: " tag)
          `(defmacro ~tag [& args#]
             `($d ~(str '~tag) ~@args#))))

#?(:clj (defmacro gen-tags
          []
          `(do
             ~@(for [tag tags]
                 (gen-tag tag)))))

#?(:clj (gen-tags))

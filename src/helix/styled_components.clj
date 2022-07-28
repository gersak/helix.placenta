(ns helix.styled-components
  (:refer-clojure :exclude [import])
  (:require 
    helix.placenta.util
    clojure.java.io))

(declare defstyled)

(defmacro defstyled
  "Macro takes style name component type and default style
  as input. In addition mixins can be used to compute additional
  style attributes where mixins are pure fn that will receive
  props converted by cljs-bean.core/->clj function."
  [cname ctype cstyle & mixins]
  (let [props-sym (gensym "props")
        _ns (str *ns*)]
    (if (not-empty mixins)
      `(def ~cname
         ((helix.styled-components/styled ~ctype)
          (fn [~props-sym]
            (let [clj-props# (assoc 
                               (cljs-bean.core/->clj ~props-sym)
                               :helix.styled-components/component (symbol ~_ns '~cname))] 
              (cljs-bean.core/->js
                (reduce 
                  helix.placenta.util/deep-merge
                  ~cstyle 
                  ((juxt ~@mixins) clj-props#)))))))
      `(def ~cname
         ((helix.styled-components/styled ~ctype) (fn [~props-sym] (cljs-bean.core/->js ~cstyle)))))))


(defmacro import-resource
  "Macro imports resource as string. Slurps file at
  compile time that bound to input 'sym'"
  [location]
  (if-let [resource (clojure.java.io/resource location)]
    (let [data (clojure.core/slurp resource)]
      `(identity ~data))
    (throw
      (ex-info
        "Couldn't find resource to import"
        {:location location}))))

(defmacro import-file
  "Macro imports resource as string. Slurps file at
  compile time that bound to input 'sym'"
  [location]
  (if-let [resource (clojure.java.io/file location)]
    (let [data (clojure.core/slurp resource)]
      `(identity ~data))
    (throw
      (ex-info
        "Couldn't find resource to import"
        {:location location}))))

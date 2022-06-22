(ns clj-kondo.helix.placenta
  (:require
    [clj-kondo.hooks-api :as api]))


; (defn defstyled [{:keys [:node]}]
;   (let [[cname ctype style & mixins] (rest (:children node))]
;     (when-not (and cname ctype)
;       (throw
;         (ex-info "Define both new component name and extended component"
;                  {:component cname
;                   :extending ctype})))
;     ; (when (and (not-empty style) (not (api/map-node? style)))
;     ;   (throw
;     ;     (ex-info
;     ;       "Style not defined for styled component"
;     ;       {:component cname
;     ;        :style style})))
;     (let [new-node (if (not-empty mixins)
;                      (api/list-node
;                        (list
;                          (api/token-node 'def)
;                          cname
;                          (api/list-node
;                            (into
;                              [(api/list-node [(api/token-node 'helix.styled-components/styled) ctype])
;                               style]
;                              mixins))))
;                      (api/list-node
;                        (list
;                          (api/token-node 'def)
;                          cname
;                          (api/list-node
;                            [(api/list-node [(api/token-node 'helix.styled-components/styled)])
;                             ctype
;                             style]))))]
;       {:node new-node})))

(defn defstyled [{:keys [:node]}]
  (let [[component-name component-parent style & hooks] (rest (:children node))]
    (when-not (and component-name component-parent)
      (throw
        (ex-info "Define both new component name and extended component"
                 {:component component-name
                  :extending component-parent})))
    (when (and (not-empty style) (not (api/map-node? style)))
      (throw
        (ex-info
          "Style not defined for styled compoent"
          {:component component-name
           :style style})))
    (let [new-node (api/list-node
                     (list
                       (api/token-node 'def)
                       (api/token-node component-name)
                       (api/list-node
                         (list
                           (api/list-node
                             (concat
                               (list (api/token-node 'comp))
                               hooks))
                           style))))]
      {:node new-node})))


(ns clj-kondo.helix.placenta
  (:require
    [clj-kondo.hooks-api :as api]))


(defn defstyled [{:keys [:node]}]
  (let [[component-name component-parent & body] (rest (:children node))]
    (when-not (and component-name component-parent)
      (throw
        (ex-info "Define both new component name and extended component"
                 {:component component-name
                  :extending component-parent})))
    (let [new-node (api/list-node
                     (list*
                       (api/token-node 'def)
                       (api/token-node component-name)
                       body))]
      {:node new-node})))


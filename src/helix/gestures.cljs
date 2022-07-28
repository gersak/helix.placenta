(ns helix.gestures
  (:require
    ["react" :as react]
    [cljs-bean.core :as b]
    [helix.core :refer [defhook]]
    ["@use-gesture/react" :as g]))


(defhook use-drag
  ([f] (use-drag f nil))
  ([f config]
    (let [updater (react/useCallback
                    (fn updater
                      ([state] (b/->js (f (b/->clj state)))))
                    #js [])
          bind (g/useDrag updater config)]
      bind)))


(defhook use-hover
  ([f] (use-hover f nil))
  ([f config]
    (let [updater (react/useCallback
                    (fn updater
                      ([state] (b/->js (f (b/->clj state)))))
                    #js [])
          bind (g/useHover updater config)]
      bind)))


(defhook use-scroll
  ([f] (use-scroll f nil))
  ([f config]
    (let [updater (react/useCallback
                    (fn updater
                      ([state] (b/->js (f (b/->clj state)))))
                    #js [])
          bind (g/useScroll updater config)]
      bind)))

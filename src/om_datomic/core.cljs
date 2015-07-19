(ns om-datomic.core
  (:require [om.next :as om :refer-macros [defui]]
            [om.next.protocols :as p]
            [om.dom :as dom]
            [goog.object :as gobj]
            [goog.dom :as gdom]))

(defn increment! [c]
  (om/assert! c (update-in (om/props c) [:counter/count] inc)))

(defui Counter
  static om/IQuery
  (query [this]
    '[:id :counter/count])
  Object
  (render [this]
    (let [{:keys [:counter/count] :as props} (om/props this)]
      (dom/div nil
        (dom/p nil (str "Count: " count))
        (dom/button
          #js {:onClick (fn [_] (increment! this))}
          "Click Me!")))))

(def counter (om/create-factory Counter))

(def current-id (atom 2))

(defn add-counter! [c]
  (let [id (swap! current-id inc)]
    (om/assert! c
      (update-in (om/props c) [:app/counters]
        conj {:id id :counter/count 0}))))

(defui HelloWorld
  static om/IQueryParams
  (params [this]
    {:counter (om/get-query Counter)})
  static om/IQuery
  (query [this]
    '[:app/title {:app/counters ?counter}])
  Object
  (render [this]
    (let [{:keys [:app/title :app/counters] :as props} (om/props this)]
      (apply dom/div nil
        (dom/h2 nil title)
        (dom/div nil
          (dom/button
            #js {:onClick (fn [e] (add-counter! this))}
            "Add Counter!"))
        (om/map-keys counter :id counters)))))

(def reconciler
  (om/tree-reconciler
    {:app/title "Hello World!"
     :app/counters [{:id 0 :counter/count 0}
                    {:id 1 :counter/count 5}
                    {:id 2 :counter/count 10}]}))

(om/add-root! reconciler
  (gdom/getElement "app") HelloWorld)

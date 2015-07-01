(ns ^:figwheel-always rcjs.core
    (:require [cljsjs.react]
              [rcjs.dom :as d])
    (:require-macros [rcjs.reactjure :refer [defcomp deftags]]))

(enable-console-print!)

(println "Edits to this text should show up in your developer console.")

;; define your app data so that it doesn't get over-written on reload

(defonce app-state (atom {:text "blah world!"}))


(defn on-js-reload []
  ;; optionally touch your app-state to force rerendering depending on
  ;; your application
  ;; (swap! app-state update-in [:__figwheel_counter] inc)
)

;; ==================
;; Thinking through some possible syntax
;; ==================
; (div {}
;   (Foo {:name "blah"}
;     (h1 )))

; (defcomponent Foo
;   (componentWillMount ())
;   (render 
;     (div {} 
;       (Foo {:name "blah"}
;         (h1 nil "blah")))))

; (deftype Foo-comp []
;   Object
;   (render [this] (.createElement js/React "h1" nil "hello react")))
; 
; (defn Foo 
;   ([] (.createElement js/React Foo-comp nil nil))
;   ([props] (.createElement js/React Foo-comp props nil))
;   ([props children] (.createElement js/React Foo-comp props children)))
; 
;   

; (do 
;   (clojure.core/deftype Foo-comp [] 
;     Object 
;     (render [this] 
;       (clojure.core/let [{:keys [name]} (js->clj (.-props this) :keywordize-keys true)] 
;       (.createElement js/React "h1" {} (str "hello " name))))) 
;       
;   (clojure.core/defn Foo [name] 
;     (.createElement js/React Foo-comp (clj->js {:name name}) nil)))
    
  
  
(defcomp List [args] 
  (d/ul (map d/li args)))
    
(.render js/React
  (List ["sam" "terry" "dana"])
  (.getElementById js/document "react"))

(.render js/React
  (d/h1 {:style {:color "blue"}} (d/span "qwe"))
  (.getElementById js/document "react2"))


    
    

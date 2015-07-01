(ns rcjs.reactjure)

(defn- args->props [args]
  (reduce #(assoc %1 (keyword %2) %2) {} args))

(defn- pure-render [comp-name args func]
 `(do
    (deftype ~(symbol (str comp-name "-comp")) []
      ~'Object
      (~'render [~'this] 
        (let [{:keys [~@args]} (~'js->clj (.-props ~'this)  :keywordize-keys true)]
          ~func)))
    
    (defn ~comp-name [~@args] 
      (.createElement js/React 
        ~(symbol (str comp-name "-comp")) 
        (~'clj->js ~(args->props args)) 
        nil))))

(defn- full-comp [name args funcs] "qwe")

(defn- defcomponent
  [name args & funcs]
  (cond
    (list? args) (full-comp name args funcs)
    (vector? args) (pure-render name args (first funcs))))

(defn createTagElement [tag props children] 
  `(.createElement js/React ~(str tag) (~'clj->js ~props) ~children))

(defn deftag [tag]
  `(defn ~tag 
    (~'[props children] ~(createTagElement tag 'props 'children))
    (~'[arg] (if (map? ~'arg) 
              ~(createTagElement tag 'arg nil)
              ~(createTagElement tag {} 'arg)))))

(defmacro defcomp [& args] `(do ~(apply defcomponent args)))
(defmacro deftags [& taglist] `(do ~@(map deftag taglist)))

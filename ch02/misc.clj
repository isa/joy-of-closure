(ns joy.ch02)                                 ; this is how you define a new namespace
; joy.ch02 =>                                 ; and REPL changes like this

(str *ns*)                                    ; *ns* always holds the value of current namespace
; => joy.ch02

(ns joy.ch02                                  ; this is how you import other namespaces
   (:require clojure.set :as s))              ; this allows us to use (clojure.set/intersection) etc..
                                              ; when :as specified, we can use like (s/intersection)
clojure.set
; => java.lang.ClassNotFoundException: clojure.set
java.lang.Object
; => java.lang.Object                         ; namespace identifier can only be used as qualifier

(ns joy.ch02
   (:use [clojure.string]))                   ; we can use all mappings from another namespace with :use
(capitalize "isa")                            ; now we can directly use them
; => "Isa"

(ns joy.ch02
   (:use [clojure.string :only [reverse]]))   ; or you can just import the ones you like
(reverse "isa")
; => "asi"

(ns joy.ch02
   (:use [clojure.string :exclude [replace]])); or you can exclude the ones you don't like

(use '[clojure.string])                       ; you can always use like this
(use '[clojure.string :only [reverse]])       ; or like this

(throw (Exception. "a message"))              ; this is how you throw exceptions

(try
   (throw (Exception. "a message"))           ; this is try/catch/finally
   (catch Exception e (str "caught"))         ; one gotcha I noticed is catch and finally are not
   (finally (println "finalizing")))          ; necessarily ordered, finally might happen before catch

(do 6                                         ; series of expressions can be evaluated, only the last one
   (+ 5 4)                                    ; will be returned
   3)
; => 3

(let [fname "isa",                            ; let allows us to define local bindings which will last
      sname "goksu"]                          ; only in the let context
   (println "first name is: " fname)          ; (let [definitions] (expression)*)
   (count sname))                             ; as usual only the last line will be returned

(defn print-down [x]                          ; clojure doesn't have loops like in imperative languages
   (when (pos? x)                             ; instead we create tail-recursion with recur to do loops
      (println x)                             ; the only gothca here is you have to define a function
      (recur (dec x))))                       ; recur should have exact number of arguments as the func

(defn sum-all [sum x]                         ; here we are using if instead of when
   (if (pos? x)                               ; the difference will be:
      (do                                     ; - we have an else condition
         (println x)                          ; - and we don't have an implicit do
         (recur (+ sum x)))
      sum))

(defn sum-all [from]                          ; in this example we didn't want to go back to the func
   (loop [total 0,                            ; definition, instead loop special form allows us to
          x from]                             ; recurse up to there
      (if (pos? x)                            ; by using this, we could get rid of the initial sum value
         (recur (+ total x) (dec x))          ; from the previous example
         total)))                             ; recur always evals to the closest loop form

(quote (1 2))                                 ; quoting will prevent evaluation of the expression
'(1 2)                                        ; same as above, just a short form

'(1 (+ 1 1))                                  ; quoting will affect all of its arguments
; => '(1 (+ 1 1))                             ; be careful, it won't be (1 2)

`Integer                                      ; back-tick is for qualifying
; => java.lang.Integer
`map
; => clojure.core/map

`(+ 10 ~(* 3 2))                              ; ~ will allow us to unquote the expression and eval it
; => (clojure.core/+ 10 6)
'(+ 10 ~(* 3 2))                              ; ' will still force quoting
; => (+ 10 (clojure.core/unquote (* 2 3)))

`autogenerate#                                ; will auto generate an unqualified symbol
; => autogenerate__211__auto__

(let [x '(2 3)] `(1 ~@x))                     ; @ helps dereferencing
; => (1 2 3)                                  ; not (1 (2 3))

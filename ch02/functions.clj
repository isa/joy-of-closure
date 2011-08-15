(fn make-set [x y] #{x y})                      ; this is an anonymous function
; => #<user$eval__1$make_set__2 user$eval__1$make_set__2@d3576a2>

((fn make-set [x y] #{x y}) 3 5)                ; this is how we call it
; => #{3 5}

; make-set symbol is optional and doesn’t correspond to a globally accessible name for the function
; but instead to a name internal to the function itself used for self-calls

(fn                                             ; this is method-overloading kinda..
   ([x] #{x})
   ([x y] #{x y}))

(fn [x y & z] #{x y z})                         ; this is varargs in java

((fn [x y & z]                                  ; so if we call like this
   #{x y z}) 1 2 3 4)
; => #{1 2 (3 4)}                               ; we get this result

; to define a function that is globally accessible
; simply:

(def make-set fn [x] #{x})                      ; now we can globally access like:
(make-set 1)
; => #{1}

(defn make-set [x] #{x})                        ; this a short form of it which removes fn part

(defn make-set                                  ; this way you can do documentation
   "Takes 1/2 parameters and make them a set"
   ([x] #{x})
   ([x y] #{x y}))

; there is even a shorter form for anonymous functions

(def make-a-list   #(list %))                   ; makes a list with given parameter
(def make-a-list1  #(list %1))                  ; same as above
(def make-a-list2  #(list %1 %2))               ; makes a list with given parameters (1 and 2)
(def make-a-list2+ #(list %1 %2 %&))            ; makes a list with given parameters and possible more
                                                ; %& is a special syntax for varargs parameters in this case

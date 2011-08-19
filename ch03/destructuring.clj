; destructuring is like pattern matching but it's in a very limited scope. for full pattern matching
; implementation, please check http://github.com/dcolthorp/matchure which will probably be included 
; in the clojure.core soon.

(let [[fname mname lname] ["J" "R" "Tolkien"]]          ; this is how you destructure a vector
   (str lname ", " fname mname))i                       ; it is also called positional destructuring
; => "Tolkien, J R"                                     ; it won't work with maps and sets since they
                                                        ; hash based

(let [[one two & more :as all] (range 5)]               ; we can assign the rest to a value by using
   (println one two)                                    ; an ampersand sign
   (println "more ->" more)                             ; we can use :as to bind all vector to the
   (println "all ->" all))                              ; varibled specified after :as
; => 0 1
; => (2 3 4)
; => [0 1 2 3 4]                                        ; watch out this one, it's a vector, not a seq

(def full-name {:fname "isa", :sname "goksu"})          ; if we have a map
(let [{fname :fname, sname :sname} full-name]           ; we can destructure the map like this
   (str sname ", " fname))

(let [{:keys [fname sname]} full-name]                  ; same thing above, by using :keys form
   (str sname ", " fname))                              ; this basically binds the values of the given
                                                        ; symbols keyword equivalent from the map
                                                        ; as in sname gets the value of keyword :sname

(let [{:strs [fname sname]} full-name]                  ; same thing above, by using :strs form
      (str sname ", " fname))                           ; this basically binds the values of the given
                                                        ; symbols keyword equivalent from the map
                                                        ; in string form, as in sname gets the value of
                                                        ; "sname" key from the map

(let [{fname :fname, :as all} full-name] all)           ; :as keyword does the same thing as in vector

(let [{:keys [t fname], :or {t "Mr."}} full-name]       ; if key is not there, you can assign default
   (str t fname))                                       ; values with :or form

(let [{one 0, three 2} [1 2 3]]                         ; we can also destructure by giving indexes
   [one three])
; => [1 3]

(defn print-last-name [{:keys [sname]}]                 ; all of the above can also be done in functions
   (println sname))
(print-last-name {:fname "isa", :sname "goksu"})
; => "goksu"

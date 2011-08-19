(if nil :true :false)                     ; everything is true unless it is nil or explicitly specified
; => :false
(when-not false :false)
; => :false
(when [] :true)
; => :true

(seq [1 2 3])                             ; seq will return a sequence view of the given collection
; => (1 2 3)
(seq [])
; => nil
(seq '(1 2 3))
; => (1 2 3)
(seq nil)
; => nil

(defn print-seq [s]                       ; using seq as a loop termination is a better solution than
   (when (seq s)                          ; using (empty? s) since it's more idiomatic
      (prn (first s))                     ; using an empty collection to test falsity is wrong
      (recur (rest s))))                  ; it might lead to infinite loop

(next [])                                 ; next would return nil if seq is empty
; => nil
(rest [])                                 ; rest would return an empty seq
; => ()



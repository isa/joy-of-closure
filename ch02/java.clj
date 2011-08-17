java.util.Locale/JAPAN                                    ; accessing static class members
; => #<Locale ja_JP>

Math/sqrt 9                                               ; namespace qualified var, idiomatic clojure
; => 3.0

(ns joy.ch02
   (:import [java.util HashMap]                           ; this is how to import java classes
            [java.util List]))

(new java.util.HashMap {1 "isa", 2 "goksu"})              ; create a new java hashmap
; => #<HashMap {1=isa, 2=goksu}>

(java.util.HashMap. {1 "isa"})                            ; same thing, idiomatic clojure
; => #<HashMap {1=isa}>

(.x (java.awt.Point. 10 20))                              ; accessing instance variables
; => 10

(let [origin (java.awt.Point. 0 0)]                       ; set! helps us to set java fields
   (set! (.x origin) 10)
   (str origin))
; => "java.awt.Point[x=10,y=0]"

(.. (java.util.Date.) toString (endsWith "2011"))         ; .. macro helps you break law of demeter
; => true

(doto (java.util.HashMap.)                                ; doto creates double brace initialization
   (.put 0 "isa")                                         ; for you instances
   (.put 1 "goksu"))
; => #<HashMap {0=isa,1=goksu}>

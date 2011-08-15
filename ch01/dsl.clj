(ns joy.sql
  (:use [clojure.string :only []]))

(defn expand-expr [expr]
  (if (coll? expr)
    (if (= (first expr) `unquote)                             ; Handle unsafe literals
      "?"
      (let [[op & args] expr]
        (str "(" (str/join (str " " op " ")
                           (map expand-expr args)) ")")))     ; Convert prefix to infix
    expr))

(declare expand-clause)

(def clause-map                                               ; Support each kind of clause
  {'SELECT    (fn [fields & clauses]
                (apply str "SELECT " (str/join ", " fields)
                       (map expand-clause clauses)))
   'FROM      (fn [table & joins]
                (apply str " FROM " table
                       (map expand-clause joins)))
   'LEFT-JOIN (fn [table on expr]
                (str " LEFT JOIN " table
                     " ON " (expand-expr expr)))
   'WHERE     (fn [expr]
                (str " WHERE " (expand-expr expr)))})

(defn expand-clause [[op & args]]                             ; Call appropriate converter
  (apply (clause-map op) args))

(defmacro SELECT [& args]                                     ; Provide main entrypoint macro
  [(expand-clause (cons 'SELECT args))
   (vec (for [n (tree-seq coll? seq args)
              :when (and (coll? n) (= (first n) `unquote))]
          (second n)))])


; Allows you to write following DSL:
;
; (defn query [max]
;   (SELECT [a b c]
;     (FROM X
;       (LEFT-JOIN Y :ON (= X.a Y.b)))
;     (WHERE (AND (< a 5) (< b ~max)))))

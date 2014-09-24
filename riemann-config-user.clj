(in-ns 'riemann.config)

(defn seqify [s] (if (sequential? s) s [s]))

(defn print-description-and-metric [e]
  (doseq [e (seqify e)]
    (println (:description e) (if-let [m (:metric e)] (str " now: " m) ""))))

(defn summary-printer [text-prefix]
  (fn print-summary [e]
    (let [events (seqify e)
          total-metric (apply + (map :metric events))
          c (count events)
          avg-metric (when c (/ total-metric c))]
      (println text-prefix "received " c " messages with an average metric of " avg-metric))))

(defn metric-series?   [e] (= "debug-metric-series" (:service e)))
(defn strange-message? [e] (re-find #".*strange.*" (:description e)))
(defn cycle-stream?    [e] (re-find #".*cycling.*" (:description e)))
(defn spike-stream?    [e] (re-find #".*spike.*" (:description e)))

(defmacro where' [pred & streams]
  `(where* (fn [e#] (and (metric-series? e#) (~pred e#))) ~@streams))

(defmacro any [& streams] `(where' (fn [e#] true) ~@streams))



#_(streams print-description-and-metric) ;;prints _all_ events

(streams (any print-description-and-metric)) ;;prints all 'debug-metric-series' events

#_(streams ;;finds spikes
 (where' (fn [e] (> (:metric e) 8)) print-description-and-metric))

#_(streams ;;finds strange messages
 (where' strange-message? print-description-and-metric))

#_(streams ;;rollup messages with metric > 1
 (where' (fn [e] (> (:metric e) 1))
         (rollup 0 5 print-description-and-metric)))

#_(streams ;;rollup messages with metric > 1, with summary
 (where' (fn [e] (> (:metric e) 1))
         (rollup 0 2 (summary-printer "summary: "))))

#_(streams
 (where' cycle-stream? (rollup 0 2 (summary-printer "cycle-summary: ")))
 (where' spike-stream? (rollup 0 5 (summary-printer "spike-summary: "))))


;;riemann requires a specific call to pick up the newly loaded config changes
;;in emacs with CIDER, place cursor right after the right paren below and press C-x C-e

#_(riemann.bin/reload!)
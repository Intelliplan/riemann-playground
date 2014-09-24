(in-ns 'riemann.config)

(defn seqify [s] (if (sequential? s) s [s]))

(defn event-printer [& [text-prefix]]
  (fn [e]
    (doseq [e (seqify e)]
      (println (str text-prefix (:service e) (if-let [m (:metric e)] (str " metric: " m) (str " --- " (:description e))))))))

(defn summary-printer [text-prefix]
  (fn print-summary [e]
    (let [events (seqify e)
          total-metric (apply + (map :metric events))
          c (count events)
          avg-metric (when c (/ total-metric c))]
      (println text-prefix "received " c " messages with an average metric of " avg-metric))))

(defn test-service?    [e] (re-find #"^test" (:service e)))
(defn strange-desc?    [e] (re-find #".*strange.*" (:description e)))
(defn cycle-stream?    [e] (re-find #".*cycle.*" (:service e)))
(defn spike-stream?    [e] (re-find #".*spike.*" (:description e)))

(defmacro where' [pred & streams]
  `(where* (fn [e#] (and (test-service? e#) (~pred e#))) ~@streams))

(defmacro any [& streams] `(where' (fn [e#] true) ~@streams))



#_(streams (event-printer)) ;;prints _all_ events

(streams (any (event-printer))) ;;prints all 'test-service' events

#_(streams ;;finds spikes
 (where' (fn [e] (> (:metric e) 8)) (event-printer "spike found! ")))

#_(streams ;;finds strange messages in description
 (where' strange-desc? (event-printer "strange message found! ")))

#_(streams ;;rollup messages with metric > 1
 (where' (fn [e] (> (:metric e) 1))
         (rollup 0 5 (event-printer "metric > 1 found! "))))

#_(streams ;;rollup messages with metric > 1, with summary
 (where' (fn [e] (> (:metric e) 1))
         (rollup 0 2 (summary-printer "summary: "))))

#_(streams
 (where' cycle-stream? (rollup 0 5 (summary-printer "cycle-summary: ")))
 (where' spike-stream? (rollup 0 5 (summary-printer "spike-summary: "))))


;;riemann requires a specific call to pick up the newly loaded config changes
;;in emacs with CIDER, place cursor right after the right paren below and press C-x C-e
#_(riemann.bin/reload!)

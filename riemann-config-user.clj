(in-ns 'riemann.config)

(defn seqify [s] (if (sequential? s) s [s]))

(defn print-description-and-metric [e]
  (doseq [e (seqify e)]
    (println (:description e) (if-let [m (:metric e)] (str " now: " m) ""))))

(defn metric-series?   [e] (= "debug-metric-series" (:service e)))
(defn strange-message? [e] (re-find #".*strange.*" (:description e)))

(defmacro where' [pred & streams] `(where* (fn [e#] (and (metric-series? e#) (~pred e#))) ~@streams))



#_(streams print-description-and-metric) ;;prints _all_ events

#_(streams ;;prints all 'debug-metric-series' events
 (where' (fn [e] true) print-description-and-metric))

#_(streams ;;finds spikes
 (where' (fn [e] (> (:metric e) 8)) print-description-and-metric))

#_(streams ;;finds strange messages
 (where' strange-message? print-description-and-metric))

#_(streams
 (where' strange-message?
   (rollup 0 5 print-description-and-metric)))


;;riemann requires a specific call to pick up the newly loaded config changes
;;in emacs with CIDER, place cursor right after the right paren below and press C-x C-e
#_(riemann.bin/reload!)
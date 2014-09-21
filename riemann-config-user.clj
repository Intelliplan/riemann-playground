(in-ns 'riemann.config)

(defn print-description-and-metric [e]
  (println (:description e) " now: " (:metric e)))

(defn when-metric-series-and "Creates a function that returns true when 'e-pred' evaluates to true for 'e', and has service 'debug-metric-series'."
  [e-pred] (fn [e] (and (= (:service e) "debug-metric-series") (e-pred e))))

(defn print-when "For 'debug-metric-series' events, print metric when 'e-pred' is true for any event."
  [e-pred] (where* (when-metric-series-and e-pred)
                   print-description-and-metric))

(streams
 (print-when
  (fn [{:keys [metric]}]
    (or (= metric 1)
        (= metric 2)))))

;;riemann requires a specific call to pick up the newly loaded config changes
;;in emacs with CIDER, place cursor right after the right paren below and press C-x C-e
#_(riemann.bin/reload!)

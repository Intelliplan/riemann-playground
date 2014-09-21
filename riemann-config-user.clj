(in-ns 'riemann.config)

(streams (where (service "debug-metric-series")
                (fn [{:keys [description metric]}] ;;log all debug-metric-series events
                  (println description " now: " metric))))

;;riemann requires a specific call to pick up the newly loaded config changes
;;in emacs with CIDER, place cursor right after the right paren below and press C-x C-e
#_(riemann.bin/reload!)

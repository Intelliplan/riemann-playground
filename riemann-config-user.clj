(in-ns 'riemann.config)

(streams #(info "received event " %)) ;;log all received events

;;riemann requires a specific call to pick up the newly loaded config changes
;;in emacs with CIDER, place cursor right after the right paren below and press C-x C-e
#_(riemann.bin/reload!)

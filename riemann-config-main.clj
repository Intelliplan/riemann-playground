; vim: filetype=clojure
; -*- mode: Clojure; fill-column: 75; comment-column: 50; -*-
(logging/init :file "/vagrant/riemann/riemann.log")
; brief information http://aphyr.github.com/riemann/configuring.html
; more information http://aphyr.github.com/riemann/api.html


; listen on :host
; port 5555 for tcp and udp listeners
; port 5556 for websockets for the dashboard and node fans
; port 5557 for repl server - very handy and very dangerous
; it's not possible to bind the repl to localhost only at present
; accept large UDP packets - TCP is preferred though
(let [host "0.0.0.0"]
  (graphite-server :host host
                   :port 5559)
  (tcp-server      :host host)
  (udp-server      :host host
              :max-size 65000)
  (repl-server     :host host)
  (ws-server       :host host))

; Expire old events from the index every 1 minute
; NB changing this value dramatically alters Riemann's memory requirements
; use :keep-keys to pass tags onto expired events as well
(periodically-expire 60 {:keep-keys [:host :service :tags]})

(include "/vagrant/riemann-config-user.clj")
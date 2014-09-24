Vagrant to set up a Riemann instance ready to accept repl connections.

# Usage


### Bring it up
```bash
vagrant up
vagrant ssh
rstart.sh # the first time you do this, all leiningen dependencies on java and clojure libraries will be downloaded to the vagrant machine's maven repo
 # when (after 30 seconds or so) you see the info message "main - riemann.core - Hyperspace core online", Riemann is ready to accept events
```

### Burst some test events
Use [this tool](https://github.com/Intelliplan/riemann-burst) to burst some events that will show up in the Riemann log. If you have done ´vagrant up´, you should have it in a dir named 'riemann-burst' in your 'riemann-playground' root dir.
It should be possible to run this from within the vagrant guest machine, but unfortunately this does not work a the moment, so do this from the host instead (you'll need to have [leiningen](http://leiningen.org/) installed).
```bash
 # vagrant ssh # Skip this, riemann fails to connect from the vagrant machine
cd riemann-burst
lein repl
```
```clj
(use 'riemann-burst.core)
(burst! 100 (apply-metric-series {:service "debug-metric-series" :description "hello!"} [1 2 3 4 5]))
```

### Work without repl
If you don't want to hack the Riemann config stragiht into the runtime through the repl, you can work the traditional way:
```bash
vagrant ssh
emacs riemann-config-user.clj
rreload.sh # don't worry about the "no such process" output
```

### Vanilla repl
You can do this either from within the vagrant machine or from the host depending on if you have leiningen installed on the host or not.
```lein repl :connect localhost:5557```
```clj
(in-ns 'riemann.config) ;; tell the repl that we want to work with the riemann.config namespace
(streams #(info "received event " %)) ;; load code that outputs all received events
(riemann.bin/reload!) ;; Riemann requires us to explicitly tell it when to actually use the new config
```

### Repl through Emacs
If you use emacs and CIDER you could, instead of connecting directly with leiningen, connect via CIDER:
```bash
emacs riemann-config-user.clj
M-x cider
#enter 127.0.0.1 and then 5557
C-c C-k #reload entire file
#reload the last (remarked) form (riemann.bin/reload!) by placing the cursor right after the last paren and pressing C-x C-e
```

### Basic process operations
If you for some reason want to restart Riemann, you may also use these (don't worry about the "no such process" output):
```bash
rstart.sh
rstop.sh
rrestart.sh
rreload.sh
```

### Riemann log
```bash
tail -f riemann/riemann.log
```

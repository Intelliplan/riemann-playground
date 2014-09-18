Vagrant to set up a Riemann instance ready to accept repl connections.

# Usage


### Bring it up
```bash
vagrant up
vagrant ssh
nohup rstart.sh & # the first time you do this, all leiningen dependencies on java and clojure libraries will be downloaded to the vagrant machine's maven repo
tail -f riemann/riemann.log # the log will show up after a while
```

### Work without repl
If you don't want to hack Clojure stragiht into the runtime through the repl, you can work the traditional way:
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
(streams #(info "received event XXXXX " %)) ;; load code that outputs all received events
(riemann.bin/reload!) ;; Riemann requires us to explicitly tell it when to actually use the new config
```

### Repl through Emacs
If you use emacs and CIDER you could, instead of connecting directly with leiningen, connect via CIDER:
```bash
emacs riemann-config-user.clj
M-x cider
#enter 127.0.0.1 and then 5557
C-u C-c C-z
(in-ns 'riemann.config)
#go back to riemann-config-user.clj
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

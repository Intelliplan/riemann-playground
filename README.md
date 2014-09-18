# Riemann playground

Vagrant to set up a Riemann instance ready to accept repl connections.

# Usage

```bash
vagrant up
vagrant ssh
nohup rstart.sh & # the first time you do this, all leiningen dependencies on java and clojure libraries will be downloaded to the vagrant machine's maven repo
tail -f riemann/riemann.log # the log will show up after a while
```

In case you've got leiningen on your host machine you may now connect to Riemann with your favourite development environment. This line should work both from within the vagrant machine (in case you do `vagrant ssh` first) and from your host machine.
```bash
lein repl :connect localhost:5557
```

Or if you don't want to hack Clojure stragiht into the runtime through the repl, you can do it the traditional way:
```bash
vagrant ssh
emacs riemann-config-user.clj
rreload.sh # don't worry about the "no such process"
```

If you for some reason want to restart, you may also use these (don't worry about the "no such process" output):
```bash
rstart.sh
rstop.sh
rrestart.sh
```

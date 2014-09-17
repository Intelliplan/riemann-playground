# Riemann playground

Vagrant to set up a Riemann instance ready to accept repl connections.

# Usage

```bash
vagrant up
vagrant ssh
lein run -- riemann.config & #The first time you do this, all leiningen dependencies on java and clojure libraries will be downloaded to the vagrant machine's maven repo
```

In case you've got leiningen on your host machine you may now connect to Riemann with your favourite development environment. This line should work both from within the vagrant machine and from your host machine.
```bash
lein repl :connect localhost:5557
```

Or if you don't want to do any Clojurish hacks through the repl, you may just:
```bash
vagrant ssh
emacs riemann.config
sudo service riemann reload #reload after changes to config
```

Perhaps want to try a Riemann restart?
```bash
service riemann stop
service riemann start
service riemann restart #alternatively...
```

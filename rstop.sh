ps -ef | grep riemann/src | awk {'print $2'} | xargs kill -KILL

This code was written to be deployed against a JBoss EAP 5.1.2 server.

As such, you will need to modify the jmx-console-users.properties file to 
uncomment out the admin user.

Integration tests can be run from the root project using the localhost-remote
profile when the server is running and reachable via localhost on port 8080.

e.g. - mvn clean install -P localhost-remote

The server needs to be running the all or production profile.

e.g. - ./run.sh -c all
Install JBoss EAP 5.1.2 and enable the 'admin' user in the 'jmx-console' 
domain by uncommenting it out in the jmx-console-user.properties file.

Start the server in the all or production profile. The server should be 
reachable at localhost on port 8080.

run.sh -c all

Execute the performance tests by running Maven with the 'localhost-remote' 
profile.

mvn clean install -Plocalhost-remote
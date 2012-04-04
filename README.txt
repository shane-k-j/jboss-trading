A mirror has to be configured in the settings.xml file so that Maven 
mirrors all requests to the old JBoss Maven repository to the new JBoss Nexus 
repository.

<settings xmlns="http://maven.apache.org/SETTINGS/1.1.0" 
          xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" 
          xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.1.0 http://maven.apache.org/xsd/settings-1.1.0.xsd">
   <mirrors>
      <mirror>
         <mirrorOfLayouts/>
         <id>jboss-deprecated</id>
         <name>JBoss Deprecated Repository</name>
         <url>https://repository.jboss.org/nexus/content/repositories/deprecated/</url>
         <mirrorOf>repository.jboss.org</mirrorOf>
      </mirror>
   </mirrors>
</settings>

Install JBoss EAP 5.1.2 and enable the 'admin' user in the 'jmx-console' 
domain by uncommenting it out in the jmx-console-user.properties file.

Start the server in the all or production profile. The server should be 
reachable at localhost on port 8080.

run.sh -c all

Execute the performance tests by running Maven with the 'localhost-remote' 
profile.

mvn clean install -Plocalhost-remote
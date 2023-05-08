SET tomcatDir=C:\Program Files\Apache Software Foundation\Tomcat 10.0\webapps

cd build/web/WEB-INF/classes 
jar cf Framework.jar ./*
move Framework.jar ../../testFramework/WEB-INF/lib/
cd ../../../
cd testFramework/build/web
jar cvf testFramework.war ./*

pause

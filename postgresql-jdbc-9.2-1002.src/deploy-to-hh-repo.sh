echo ====================================================================
echo Before you can use this script, you should copy the following jars
echo to M2_HOME/lib
echo - wagon-webdav-1.0-beta-2.jar
echo - slide-webdavlib-2.1.jar
echo - commons-httpclient-2.0.2.jar
echo - commons-logging-1.0.4.jar
echo ====================================================================
mvn-hh deploy:deploy-file -DgroupId=postgresql -DartifactId=postgresql -Dpackaging=jar -Dversion=9.2-1002.jdbc4-hh1 -Dsources=src.jar -Dfile=jars/postgresql.jar -Durl=dav:http://m2.hh.ru/content/repositories/releases -DrepositoryId=hh

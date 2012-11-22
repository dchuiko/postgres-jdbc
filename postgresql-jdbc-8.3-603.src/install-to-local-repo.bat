@echo off
call mvn install:install-file -DgroupId=postgresql -DartifactId=postgresql -Dpackaging=jar -Dversion=8.3-603.jdbc4-hh1 -Dsources=src.jar -Dfile=jars\postgresql.jar

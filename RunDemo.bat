@echo off

rem Compile demo application.
rem Comment off mvn command for running application.
call mvn clean install -DskipTests

rem Run demo application.
cd target
java -jar CodingTest-1.0.0.war

rem Keep command prompt screen opening.
pause
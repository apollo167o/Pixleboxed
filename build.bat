@echo off
javac -cp "lib\juanparser.jar" grabGame.java
if errorlevel 1 (
    echo Compile failed.
    exit /b 1
)
java -cp "lib\juanparser.jar;." grabGame
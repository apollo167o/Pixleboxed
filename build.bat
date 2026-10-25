@echo off
javac -d out -cp "lib\juanparser.jar" *.java
if errorlevel 1 (
    echo Compile failed.
    exit /b 1
)
java -cp "lib\juanparser.jar;out" program
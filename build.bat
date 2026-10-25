@echo off
javac -cp "juanparser.jar" *.java
if errorlevel 1 (
    echo Compile failed.
    pause
    exit /b 1
)
java -cp "juanparser.jar;." program
pause
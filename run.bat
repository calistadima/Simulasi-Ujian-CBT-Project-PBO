@echo off
REM run.bat - compile (optional) and run the CBT application
REM Usage: double-click or run from cmd: run.bat

REM Change to script directory
cd /d %~dp0

REM Check lib folder
if not exist "lib" (
  echo ERROR: lib folder not found. Place JDBC driver jar(s) in the lib\ folder.
  pause
  exit /b 1
)

REM Compile Java sources (safe to run even if already compiled)
echo Compiling sources (if needed)...
javac -cp ".;lib/*" *.java
if errorlevel 1 (
  echo Compilation failed. Fix errors and re-run.
  pause
  exit /b 1
)

REM Run the application with all jars in lib on the classpath
set CP=.;lib\*

echo Running application...
java -cp "%CP%" Main

echo Application finished with exit code %errorlevel%.
pause

@echo OFF
echo building example...
javac  src\main\java\Demo.java -cp ..\..\Binaries\yoctoAPI.jar   || exit /b 1
IF "%1" == "" goto end
del /Q /F src\main\java\*.class
:end
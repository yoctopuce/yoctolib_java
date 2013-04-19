@echo OFF
echo building example...
javac  src\Demo.java -cp ..\..\Binaries\yoctoAPI.jar   || exit /b 1
IF "%1" == "" goto end
del /Q /F src\*.class
:end
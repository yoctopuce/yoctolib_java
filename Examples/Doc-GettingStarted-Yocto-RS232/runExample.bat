@echo OFF
call .\buildExample.bat
echo starting the example...
java -classpath ..\..\Binaries\yoctoAPI.jar;.\src\main\java\ Demo %*
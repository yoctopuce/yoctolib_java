@echo OFF
mkdir classes
echo compiling all classes...
javac -d classes/ ../../JsonParser/org/json/*.java
javac -d classes/ ../../yoctolib/src/main/java/com/yoctopuce/YoctoAPI/*.java -cp classes/
javac -d classes/ src/main/java/Demo.java -cp classes/
echo starting the example...
java -cp classes Demo $*java -classpath ..\..\Binaries\yoctoAPI.jar;.\src\main\java\ Demo %*
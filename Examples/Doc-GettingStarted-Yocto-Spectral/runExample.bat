@echo OFF
mkdir classes
echo compiling all classes...
javac -d classes/ ../../yoctolib/src/main/java/com/yoctopuce/YoctoWSHandler/*.java  ../../yoctolib/src/main/java/com/yoctopuce/YoctoAPI/*.java  -cp classes/
javac -d classes/ src/main/java/Demo.java -cp classes/
echo starting the example...
java -cp classes Demo %*
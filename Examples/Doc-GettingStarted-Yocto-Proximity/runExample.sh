#!/bin/bash
mkdir -p classes
echo -e "compiling all classes..."
javac -d classes/ ../../JsonParser/org/json/*.java
javac -d classes/ ../../yoctolib/src/main/java/com/yoctopuce/YoctoAPI/*.java -cp classes/
javac -d classes/ src/main/java/Demo.java -cp classes/
echo -e "starting the example..."
java -cp classes Demo $*
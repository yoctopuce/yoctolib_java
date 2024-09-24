#!/bin/bash
mkdir -p classes
echo -e "compiling all classes..."
javac -d classes/ ../../yoctolib/src/main/java/com/yoctopuce/YoctoWSHandler/*.java  ../../yoctolib/src/main/java/com/yoctopuce/YoctoAPI/*.java  -cp classes/
javac -d classes/ src/main/java/Demo.java -cp classes/
echo -e "starting the example..."
java -Djava.library.path=../../Binaries -cp classes Demo $*
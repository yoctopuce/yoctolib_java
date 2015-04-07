#!/bin/bash
./buildExample.sh
echo -e "starting the example..."
java -classpath ../../Binaries/yoctoAPI.jar:./src/main/java/ Demo $*
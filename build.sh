#!/bin/bash
echo "Build YoctoAPI package"
echo "========================================"
if [ "$1" == 'clean' ]; then
	rm -rf Binaries/*
	exit 0
else
	javac -g -d Binaries -sourcepath yoctolib/src/main/java yoctolib/src/main/java/com/yoctopuce/YoctoAPI/*.java
	if [ "$?" -ne "0" ]; then
	  exit 1
	fi
	cd Binaries
	jar -cvf yoctoAPI.jar com/yoctopuce/YoctoAPI/*.class
	if [ "$?" -ne "0" ]; then
	  exit 1
	fi
	cd ..
	echo -e "done"
	if [ "$1" == 'release' ]; then
		rm -rf Binaries/com Binaries/org
	fi
fi

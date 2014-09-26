#!/bin/bash
echo "Build YoctoAPI package"
echo "========================================"
if [ "$1" == 'clean' ]; then
	rm -rf Binaries/*
	exit 0
else
	javac -g -d Binaries -sourcepath yoctolib/src/main/java yoctolib/src/main/java/com/yoctopuce/YoctoAPI/*.java JsonParser/org/json/*.java
	if [ "$?" -ne "0" ]; then
	  exit 1
	fi
	cd Binaries
	jar -cvf yoctoAPI.jar com/yoctopuce/YoctoAPI/*.class org/json/*.class 
	if [ "$?" -ne "0" ]; then
	  exit 1
	fi
	cd ..
	echo -e "done"
	if [ "$1" == 'release' ]; then
		rm -rf Binaries/com Binaries/org
	fi
fi
echo "Build JAVA Examples"
echo "=================="
for d in Examples/*
do
echo "Build "$d
cd $d
chmod +x *.sh
./buildExample.sh $1 
if [ "$?" -ne "0" ]; then
  exit 1
fi
cd ../../
done
echo -e "All examples successfully built"

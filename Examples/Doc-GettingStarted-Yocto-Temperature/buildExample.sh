#!/bin/bash
echo -e "building example..."
javac  src/Demo.java -cp ../../Binaries/yoctoAPI.jar 
if [ "$?" -ne "0" ]; then
  exit 1
fi	
if [ ! -z $1 ]; then
	rm -rf src/*.class
fi
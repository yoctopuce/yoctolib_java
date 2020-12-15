#!/bin/bash
echo "set build number to 1.10.42982-SNAPSHOT"
mvn versions:set -DnewVersion=1.10.42982-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=1.10.42982-SNAPSHOT -DgenerateBackupPoms=false
cd ../yoctolib-jEE
mvn versions:set -DnewVersion=1.10.42982-SNAPSHOT -DgenerateBackupPoms=false

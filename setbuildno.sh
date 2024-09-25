#!/bin/bash
echo "set build number to 2.0.62733-SNAPSHOT"
mvn versions:set -DnewVersion=2.0.62733-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=2.0.62733-SNAPSHOT -DgenerateBackupPoms=false
cd ../yoctolib-jEE
mvn versions:set -DnewVersion=2.0.62733-SNAPSHOT -DgenerateBackupPoms=false

#!/bin/bash
echo "set build number to 2.0.62710-SNAPSHOT"
mvn versions:set -DnewVersion=2.0.62710-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=2.0.62710-SNAPSHOT -DgenerateBackupPoms=false
cd ../yoctolib-jEE
mvn versions:set -DnewVersion=2.0.62710-SNAPSHOT -DgenerateBackupPoms=false

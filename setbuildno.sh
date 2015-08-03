#!/bin/bash
echo "set build number to 1.10.20975-SNAPSHOT"
mvn versions:set -DnewVersion=1.10.20975-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=1.10.20975-SNAPSHOT -DgenerateBackupPoms=false

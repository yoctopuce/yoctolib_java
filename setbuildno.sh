#!/bin/bash
echo "set build number to 2.0.62334-SNAPSHOT"
mvn versions:set -DnewVersion=2.0.62334-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=2.0.62334-SNAPSHOT -DgenerateBackupPoms=false
cd ../yoctolib-jEE
mvn versions:set -DnewVersion=2.0.62334-SNAPSHOT -DgenerateBackupPoms=false

#!/bin/bash
echo "set build number to 2.1.16635-SNAPSHOT"
mvn versions:set -DnewVersion=2.1.16635-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=2.1.16635-SNAPSHOT -DgenerateBackupPoms=false
cd ../yoctolib-jEE
mvn versions:set -DnewVersion=2.1.16635-SNAPSHOT -DgenerateBackupPoms=false

#!/bin/bash
echo "set build number to 1.10.28878-SNAPSHOT"
mvn versions:set -DnewVersion=1.10.28878-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=1.10.28878-SNAPSHOT -DgenerateBackupPoms=false

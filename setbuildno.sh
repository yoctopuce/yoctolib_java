#!/bin/bash
echo "set build number to 1.10.27289-SNAPSHOT"
mvn versions:set -DnewVersion=1.10.27289-SNAPSHOT -DgenerateBackupPoms=false
cd yoctolib
mvn versions:set -DnewVersion=1.10.27289-SNAPSHOT -DgenerateBackupPoms=false

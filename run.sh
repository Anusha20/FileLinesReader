#!/bin/sh

mvn clean compile exec:java -Dexec.args="$1 $2"

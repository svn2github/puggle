#!/bin/bash

export JAVA_BIN=java

$JAVA_BIN -Xms2M -Xmx512M -cp ../dist/Puggle.jar -jar "../dist/Puggle.jar" $*


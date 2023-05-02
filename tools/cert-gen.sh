#!/bin/bash

workdir=~/work/qrekru

keytool -genkeypair -alias qrekru -keyalg RSA -keysize 2048 -validity 365 -keystore qrekru.jks
mv qrekru.jks $workdir/src/main/resources/

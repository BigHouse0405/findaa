#!/bin/bash

workdir=~/work/bnauth
source $workdir/tools/log.sh

makeCertDirs() {
  inf "certificates" "Creating certificates directories"
  mkdir -p $workdir/src/main/resources/certs/
  mkdir -p $workdir/ws-client/keys/
}

startPostgres() {
  inf "environment" "Done installing dependencies and libraries"
  inf "environment" "Starting Postgres"
  ./gradlew pgRun &>/dev/null
  inf "environment" "Configuring database users and data"
  ./gradlew pgData &>/dev/null
}

install() {
  makeCertDirs
  sh $workdir/tools/cert-gen.sh "$keystorePassword"
  installGradle
  startPostgres
}

installGradle() {
  inf "environment" "Installing required dependencies and libraries"
  inf "environment" "Installing Gradle"
  ./gradlew clean build &>/dev/null
}

installNpmLocal() {
  inf "environment" "Installing node modules in ws-client application"
  npm i --prefix $workdir/ws-client/ &>/dev/null
}

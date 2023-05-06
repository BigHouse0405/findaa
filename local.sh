#!/bin/bash

# Arguments:
#     -p:   This is the export password for p12

workdir=~/work/bnauth
source $workdir/tools/log.sh
source $workdir/tools/setup-tools.sh

export keystorePassword=""

main() {
  infMain "environment" "Starting Auth Server + Websocket Client LOCAL stack"
  install
  installNpmLocal
  infMain "environment" "Setup done. Running auth service"
  runLocal
}

runLocal() {
  osascript -e "tell application \"Terminal\" to do script \"cd $workdir && ./gradlew bootRun\""
  sleep 5
  inf "environment" "Running websocket client"
  osascript -e "tell application \"Terminal\" to do script \"cd $workdir/ws-client && npm start\""
  inf "environment" "App working"
}

while getopts ":p:" opt; do
  case $opt in
  p)
    keystorePassword="$OPTARG"
    ;;
  \?)
    echo "Invalid option -$OPTARG" >&2
    exit 1
    ;;
  esac
  case $OPTARG in
  -*)
    echo "Option $opt needs a valid argument"
    exit 1
    ;;
  esac
done
main

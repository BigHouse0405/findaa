#!/bin/bash

# Arguments:
#     -p:   This is the export password for p12

workdir=~/work/bnauth
source $workdir/tools/log.sh
source $workdir/tools/setup-tools.sh

export keystorePassword=""

main() {
  infMain "environment" "Starting Auth Server + Websocket Client Docker stack"
  install
  infMain "environment" "Setup done. Running auth service"
  runContainers
  infMain "environment" "Docker containers working. Wait for client to load and open https://localhost:3000"
}

runContainers() {
  inf "docker" "Deploying docker stack"
  docker compose -f $workdir/docker-compose.yml up --force-recreate -d
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

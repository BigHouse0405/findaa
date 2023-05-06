#!/bin/sh

docker buildx build --platform=linux/amd64 --tag=finda/bntech-bnauth:latest.amd ../
docker buildx build --platform=linux/arm64 --tag=finda/bntech-bnauth:latest.arm ../

docker buildx build --platform=linux/amd64 --tag=finda/bntech-bnauth-client:latest.amd ../ws-client/
docker buildx build --platform=linux/arm64 --tag=finda/bntech-bnauth-client:latest.arm ../ws-client/

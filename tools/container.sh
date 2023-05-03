#!/bin/sh

docker buildx build --platform=linux/amd64 --tag=finda/bntech-qrekru:latest.amd ../
docker buildx build --platform=linux/arm64 --tag=finda/bntech-qrekru:latest.arm ../

#!/bin/bash

inf() {
  echo "\033[32;32m[$(date) \t INFO]\033[0m \t \033[32;32m[$1]:\033[0m \t$2"
}

err() {
  echo "\033[31;40m[$(date) \t ERROR]\033[0m \t \033[1;35m[$1]:\033[0m \t$2"
}


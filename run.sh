#!/bin/bash
set -e
./build.sh
java -cp out com.carousell.marketplace.cli.Application

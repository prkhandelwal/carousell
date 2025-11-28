#!/bin/bash
set -e
OUT_DIR=out
mkdir -p "$OUT_DIR"
javac -d "$OUT_DIR" $(find src -name "*.java")
echo "Build complete."

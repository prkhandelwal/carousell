SRC_DIR=src
OUT_DIR=out

SOURCES := $(shell find $(SRC_DIR) -name "*.java")

build:
	mkdir -p $(OUT_DIR)
	javac -d $(OUT_DIR) $(SOURCES)

run: build
	java -cp $(OUT_DIR) com.carousell.marketplace.cli.Application

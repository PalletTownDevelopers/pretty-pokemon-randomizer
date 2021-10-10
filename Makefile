OUTPUT_DIR := $(HOME)/wd/pokemon/randomizer-output/
CACHE_DIR := $(HOME)/wd/pokemon/randomizer-cache/
CODE_DISASSEMBLY := $(HOME)/wd/pokemon/pokered/

#SEED := $$RANDOM
SEED := 14171

SCRIPT_DIR := .
MAVEN_BUILD := mvn clean compile assembly:single

.PHONY: all dev build initialize shake

all: initialize build shake

#Build randomizer
build:
	@echo "Build randomizer"
	$(MAVEN_BUILD)
	cp -f target/randomizer* ui/randomizer.jar
	@echo "randomizer's build finished"

#Initialize randomizer's directories
initialize:
	@echo $(OUTPUT_DIR)
	@echo Developpement mode
	@echo output dir = $(OUTPUT_DIR)
	@echo cache dir = $(CACHE_DIR)
	@echo seed = $(SEED)
	@echo directory of the script = $(SCRIPT_DIR)
	@echo ""

#Initialize Cache directory
	@echo "Initialize cache directory"
	@rm -Rf $(CACHE_DIR)
	@cp -r $(CODE_DISASSEMBLY) $(CACHE_DIR)
	@echo ""

#Launch shaker
shake:
	@echo ""
	@echo "Launch shaker"
	@java -jar "$(SCRIPT_DIR)/"target/randomizer-1.0-SNAPSHOT-jar-with-dependencies.jar \
      -shake \
      -seed "$(SEED)" \
      -pokemon_dir "$(CACHE_DIR)" \
      -output_dir "$(OUTPUT_DIR)"


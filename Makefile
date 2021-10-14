OUTPUT_DIR := $(HOME)/wd/pokemon/randomizer-output/
CODE_DISASSEMBLY := $(HOME)/wd/pokemon/randomizer-pokered/

#SEED := $$RANDOM
SEED := 14171

SCRIPT_DIR := .
MAVEN_BUILD := mvn clean compile assembly:single

.PHONY: all dev build displayParams shake

all: displayParams build shake

#Build randomizer
build:
	@echo "Build randomizer"
	$(MAVEN_BUILD)
	cp -f target/randomizer* ui/randomizer.jar
	@echo "randomizer's build finished"

#Display parameters
displayParams:
	@echo $(OUTPUT_DIR)
	@echo Developpement mode
	@echo output dir = $(OUTPUT_DIR)
	@echo pokemon dir = $(CODE_DISASSEMBLY)
	@echo seed = $(SEED)
	@echo directory of the script = $(SCRIPT_DIR)
	@echo ""

#Launch shaker
shake:
	@echo ""
	@echo "Launch shaker"
	@java -jar "$(SCRIPT_DIR)/"target/randomizer-1.0-SNAPSHOT-jar-with-dependencies.jar \
      -shake \
      -seed "$(SEED)" \
      -pokemon_dir "$(CODE_DISASSEMBLY)" \
      -output_dir "$(OUTPUT_DIR)"


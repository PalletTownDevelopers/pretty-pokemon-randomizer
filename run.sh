DEV_MODE=1

#function to launch shaker
launchShaker()
{
  #In DEV mode we use default dir to compile ASM Code and generate code seed with random function
  if [[ $DEV_MODE == 1 ]]; then
    OUTPUT_DIR="$HOME/wd/pokemon/randomizer-output/"
    CACHE_DIR="$HOME/wd/pokemon/randomizer-cache/"
    CODE_DISASSEMBLY="$HOME/wd/pokemon/pokered/"
    SEED=14171
    #SEED=$RANDOM
    SCRIPT_DIR=$(dirname $0)
    echo "Developpement mode"
    echo "output dir = $OUTPUT_DIR"
    echo "cache dir = $CACHE_DIR"
    echo "seed = $SEED"
    echo "directory of the script = $SCRIPT_DIR"
  fi

  #we make output dir if it's not exist
  if [[ ! -d "$OUTPUT_DIR" ]]; then
    mkdir -pv "$OUTPUT_DIR"
  fi

  #we make cache dir if it's not exist
  if [[ ! -d "$CACHE_DIR" ]]; then
    cp -r $CODE_DISASSEMBLY $CACHE_DIR
  fi



  #Launch shaker
  java -jar "$SCRIPT_DIR/"target/randomizer-1.0-SNAPSHOT-jar-with-dependencies.jar \
  -shake \
  -seed "$SEED" \
  -pokemon_dir "$CACHE_DIR" \
  -output_dir "$OUTPUT_DIR"

  #Go to output directory and delete old gbc roms
  cd "$OUTPUT_DIR"
  rm *.gbc

  #We add permissions to use tools to help compilation
  chmod -Rf 777 "$OUTPUT_DIR"

  #We compile ASM code to build GBC roms
  make
}

#We check argument of the script for choice 3 differents ways:
#If the argument equal to build, we will just build project
#If the argument equal to full, we will build project and launch shaker
#If no argument, we will just launch shaker
if [[ $1 == 'build' ]]; then
  mvn clean compile assembly:single
elif [[ $1 == 'full' ]]; then
  echo "Compile randomizer"
  mvn clean compile assembly:single
  echo "Compilation finished"
  echo ""
  echo "Launch shaker"
  launchShaker
else
  launchShaker
fi

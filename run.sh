DEV_MODE=1

#In DEV mode we use default dir to compile ASM Code and generate code seed with random function
if [[ $DEV_MODE == 1 ]]; then
  OUTPUT_DIR="$HOME/wd/pokemon/randomizer-output/"
  CACHE_DIR="$HOME/wd/pokemon/randomizer-cache/"
  SEED=$RANDOM
  echo "Developpement mode"
  echo "output dir = $OUTPUT_DIR"
  echo "cache dir = $CACHE_DIR"
  echo "seed = $SEED"
fi

#Launch shaker
java -jar target/randomizer-1.0-SNAPSHOT-jar-with-dependencies.jar \
-shake \
-seed "$SEED" \
-pokemon_dir "$CACHE_DIR" \
-output_dir "$OUTPUT_DIR"

#Go to output directory and delete old gbc roms
cd "$OUTPUT_DIR"
rm ./*.gbc

#We add permissions to use tools to help compilation
chmod -Rf 777 ./*

#We compile ASM code to build GBC roms
make

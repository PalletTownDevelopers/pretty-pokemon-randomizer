#Step 1: CLean et compile java project for the randomizer
echo "Step 1 : Compile java project"
echo "============================="
mvn clean compile assembly:single
echo "Compilation done"

#Setp 2: Install UI for website
cd ui

echo ""
echo "Install node modules"
npm install
echo "Install finished"
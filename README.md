# randomizer

### Prerequisites
1. Install the last version of java
2. Install node and npm
3. Install Maven` for packages management
4. Clone pokered and copy this into ui/tmp
5. Rename the ui/config/config.example.json to ui/config/config.json
6. In config.json, put the correct configuration for postgres database
```
git clone https://github.com/pret/pokered.git ~/wd/pokemon/pokered
cp -r ~/wd/pokemon/pokered ui/tmp/
```

### Installation
Into ui direction to install web dependencies
```
make
```
Build and run the docker of db with correct configuration


### Execute CLI
```
make
```

### Create data folder for DB in ui
```
mkdir ./ui/data
```

### Copy file config.example to config and put correct configuration for DB
````
cp config.example.json config.json
````

### Launch DB with docker-compose
```
docker-compose up -d
```

### You want to look log of DB
```
docker-compose logs -f
```
    
### Execute website
```
cd ui
node app.js
```

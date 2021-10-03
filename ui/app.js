const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const path = require('path')
const router = express.Router()
const {Client} = require('pg')
const config = require('./config/config')

// Require the dependency
const execSync = require('child_process').execSync
const exec = require('child_process').exec
const client = new Client(config)
client.connect()

app.use(express.static(__dirname+'/public'))
app.use(bodyParser.json())

router.get('/', (req, res) => {
    res.sendFile(path.join(path.__dirname+'/public/index.html'))
})

router.post('/generate', (req, res) => {
    let params = req.body
    let seed = params.seed
    let timestamp = params.timestamp
    let randomizerOutput = __dirname + '/tmp/randomizer-output_' + timestamp
    let randomizerCache = __dirname + '/tmp/randomizer-cache_' + timestamp
    let codeDisassembly = __dirname + '/tmp/pokered/'


    execSync('mkdir -pv ' + randomizerOutput)
    execSync('chmod -Rf 777 ' + randomizerOutput)

    execSync('mkdir -pv ' + randomizerCache)
    execSync('cp -r ' + codeDisassembly + '* ' + randomizerCache)

    let commandRandomizer = 'java -jar "' + __dirname + '/randomizer.jar" -shake -seed ' + seed + ' -pokemon_dir "' + randomizerCache + '" -output_dir "' + randomizerOutput + '"'
    execSync(commandRandomizer)

    execSync('rm ' + randomizerOutput + '/maps/CeladonGym.blk')
    execSync('cp CeladonGym.blk ' + randomizerOutput + '/maps/CeladonGym.blk')

    execSync('cd ' + randomizerOutput + ' && chmod -Rf 777 . && make')

    let nameRom = __dirname + '/public/pokered_' + timestamp + '.gbc'
    execSync("mv " + randomizerOutput + '/pokered.gbc ' + nameRom)
    exec('rm -Rf ' + randomizerOutput)
    exec('rm -Rf ' + randomizerCache)
    let query = "INSERT INTO stat_option (seed, info) VALUES(" + seed + ",'" + JSON.stringify(params) + "')"
    client.query(query)
        .catch(err => {
            console.log(err)
        })
        .finally(() => {
            client.end()
        })
    res.download(nameRom)
})

app.use('/',router)
app.use('/generate',router)
app.listen(3000, () => {
    console.log('Example app listening on port 3000!')
})

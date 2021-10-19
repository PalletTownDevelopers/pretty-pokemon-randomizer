const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const path = require('path')
const router = express.Router()
const {Client} = require('pg')
const config = require('./config/config')
const {raw} = require("express");

// Require the dependency
const execSync = require('child_process').execSync
const exec = require('child_process').exec

app.use(express.static(__dirname+'/public'))
app.use(bodyParser.json())

router.get('/', (req, res) => {
    res.sendFile(path.join(path.__dirname+'/public/index.html'))
})

router.post('/generate', async (req, res) => {
    let params = req.body
    let randomizerOutput = __dirname + '/tmp/randomizer-output_' + params.timestamp
    let codeDisassembly = __dirname + '/tmp/pokered/'

    let commandRandomizer = 'java -jar "' + __dirname + '/randomizer.jar" -shake -seed ' + params.seed + ' -pokemon_dir "' + codeDisassembly + '" -output_dir "' + randomizerOutput + '"'
    if(params.debug) {
        commandRandomizer += " -debug"
    }
    if(params.spriteYellow) {
        commandRandomizer += " -sprite_yellow"
    }
    execSync(commandRandomizer)

    let nameRom = __dirname + '/public/pokered_' + params.timestamp + '.gbc'
    execSync("mv " + randomizerOutput + '/pokered.gbc ' + nameRom)
    exec('rm -Rf ' + randomizerOutput)
    let query = "INSERT INTO stat_option (seed, info) VALUES(" + params.seed + ",'" + JSON.stringify(params) + "')"
    try {
        const client = new Client(config)
        await client.connect()
        await client.query(query)
    } catch(error) {
        console.log(err)
    }
    res.download(nameRom)
})

app.use('/',router)
app.use('/generate',router)
app.listen(3000, () => {
    console.log('Example app listening on port 3000!')
})

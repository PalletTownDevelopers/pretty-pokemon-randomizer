const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const path = require('path')
const router = express.Router()

// Require the dependency
const execSync = require('child_process').execSync
const exec = require('child_process').exec

app.use(express.static(__dirname+'/public'))
app.use(bodyParser.json())

router.get('/', (req, res) => {
    res.sendFile(path.join(__dirname+'/public/index.html'))
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

    let nameRom = 'public/pokered_' + timestamp + '.gbc'
    execSync("mv " + randomizerOutput + '/pokered.gbc ' + nameRom)
    exec('rm -Rf ' + randomizerOutput)
    exec('rm -Rf ' + randomizerCache)
    res.download(nameRom)
})

app.use('/',router)
app.use('/generate',router)
app.listen(3000, () => {
    console.log('Example app listening on port 3000!')
})

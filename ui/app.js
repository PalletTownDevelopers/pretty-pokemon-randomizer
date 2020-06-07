const express = require('express')
const bodyParser = require('body-parser')
const app = express()
const path = require('path')
const router = express.Router()
const exec = require('child_process').exec

app.use(express.static(__dirname+'/public'))
app.use(bodyParser.urlencoded({ extended: true }))

router.get('/', (req, res) => {
    res.sendFile(path.join(__dirname+'/public/index.html'))
})

router.post('/generate', (req, res) => {
    let params = req.body
    
    let seed = params.seed
    let randomizerOutput = __dirname + '/tmp/randomizer-output'
    let randomizerCache = __dirname + '/tmp/randomizer-cache'
    let codeDisassembly = __dirname + '/tmp/pokered/'

    exec('rm -Rf ' + randomizerOutput)
    exec('mkdir -pv ' + randomizerOutput)
    exec('chmod -Rf 777 ' + randomizerOutput)

    exec('rm -Rf ' + randomizerCache)
    exec('mkdir -pv ' + randomizerCache)
    exec('cp -r ' + codeDisassembly + '* ' + randomizerCache)

    let commandRandomizer = 'java -jar "' + __dirname + '/randomizer.jar" -shake -seed ' + seed + ' -pokemon_dir "' + randomizerCache + '" -output_dir "' + randomizerOutput + '"'
    let processRandomizer = exec(commandRandomizer)
    console.log(commandRandomizer)

    processRandomizer.stdout.on('data', (data) => {
        console.log('stdout: ' + data.toString())
    })

    processRandomizer.stderr.on('data', (data) => {
        console.log('stderr: ' + data.toString())
    })

    processRandomizer.on('exit', (code) => {
        console.log('child process exited with code ' + code.toString())
    })

    res.sendFile(path.join(__dirname+'/public/index.html'))
})

app.use('/',router)
app.use('/generate',router)
app.listen(3000, () => {
    console.log('Example app listening on port 3000!')
})

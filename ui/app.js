const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const path = require('path');
const router = express.Router();
const exec = require('child_process').exec;

app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: true }));

router.get('/', function (req, res) {
    res.sendFile(path.join(__dirname+'/public/index.html'));
});

router.post('/generate', function (req, res) {
    var params = req.body;
    exec('../run.sh', (error, stdout, stderr) => {
        if (error) {
            console.error(`exec error: ${error}`);
            return;
        }
        console.log(`stdout: ${stdout}`);
        console.log(`stderr: ${stderr}`);
    });
    res.sendFile(path.join(__dirname+'/public/index.html'));
});

app.use('/',router);
app.use('/generate',router);
app.listen(3000, function () {
    console.log('Example app listening on port 3000!')
})

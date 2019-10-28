const express = require('express');
const bodyParser = require('body-parser');
const app = express();
const path = require('path');
const router = express.Router();

app.use(express.static('public'));
app.use(bodyParser.urlencoded({ extended: true }));

router.get('/', function (req, res) {
    res.sendFile(path.join(__dirname+'/public/index.html'));
});

router.post('/generate', function (req, res) {
    var params = req.body;
});

app.use('/',router);
app.listen(3000, function () {
    console.log('Example app listening on port 3000!')
})

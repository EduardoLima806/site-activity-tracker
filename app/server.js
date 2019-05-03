const express = require('express');
const mongoose = require('mongoose');
const requireDir = require('require-dir');
const cors = require('cors');
var redisClient = require('./src/lib/redisCreateClient').createClient;

// Iniciando o App
const app = express();
app.use(express.json()); // Permite o envio de payloads json
app.use(cors());

// Iniciando o DB
mongoose.connect('mongodb://db:27017/users', { useNewUrlParser: true }, (err) => { useNewUrlParser: true
 }, (err) => {
    if (err)
        throw err;
    console.log('connected to mongo');
});

redisClient.on('connect', function () {
    console.log('Redis client connected');
});

requireDir('./src/models');

const UserInteraction = mongoose.model('UserInteraction');

// Rotas
app.use('/api', require('./src/routes'));

app.set('port', (process.env.PORT || 3001));

app.listen(app.get('port'), () => {
    console.log("Node is runnig at localhost:" + app.get('port'));
});
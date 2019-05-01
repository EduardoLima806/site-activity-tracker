const express = require('express');
const mongoose = require('mongoose');
const requireDir = require('require-dir');
const cors = require('cors');

// Iniciando o App
const app = express();
app.use(express.json()); // Permite o envio de payloads json
app.use(cors());

// Iniciando o DB
mongoose.connect('mongodb://mongodb:27017/users', { useNewUrlParser: true }, (err) => {
//mongoose.connect('mongodb+srv://admin:mgadmin@mongodb-8yuec.mongodb.net/users-interactions', { useNewUrlParser: true }, (err) => {
    if (err)
        throw err;
    console.log('connected to mongo');
});

requireDir('./src/models');

const UserInteraction = mongoose.model('UserInteraction');

// Rotas
app.use('/api', require('./src/routes'));

app.set('port', (process.env.PORT || 3001));

app.listen(app.get('port'), () => {
    console.log("Node is runnig at localhost:" + app.get('port'));
});
const express = require('express');
const routes = express.Router();

const UserInteractionController = require('./controllers/UserInteractionController');

routes.get('/interactions', UserInteractionController.index);
routes.post('/interactions', UserInteractionController.store);
routes.get('/interactions/:id', UserInteractionController.show);
routes.put('/interactions/:id', UserInteractionController.update);
routes.delete('/interactions/:id', UserInteractionController.destroy);


module.exports = routes;
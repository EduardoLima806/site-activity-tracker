const mongoose = require('mongoose');

const UserInteraction = mongoose.model('UserInteraction');
var redisClient = require('../lib/redisCreateClient').createClient;

module.exports = {
    async index(req, res) {
        const { page = 1} = req.query;
        const interactions = await UserInteraction.paginate({}, { page, limit: 10 });

        return res.json(interactions);
    },

    async show(req, res) {
        const interaction = await UserInteraction.findById(req.params.id);

        return res.json(interaction);
    },

    async update(req, res) {
        const interaction = await UserInteraction.findByIdAndUpdate(req.params.id, req.body, { new: true });

        return res.json(interaction);
    },

    async store(req, res) {
        const token = req.get("Authorization");
       
        const interaction = await UserInteraction.create(req.body);
        const counts = await redisClient.incr(token);
       
        return res.json(interaction);
    },

    async destroy(req, res) {
        await UserInteraction.findByIdAndRemove(req.params.id);
        
        return res.send();
    }
}
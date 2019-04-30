const mongoose = require('mongoose');

const UserInteraction = mongoose.model('UserInteraction');

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
        const interaction = await UserInteraction.create(req.body);

        return res.json(interaction);
    },

    async destroy(req, res) {
        await UserInteraction.findByIdAndRemove(req.params.id);
        
        return res.send();
    }
}
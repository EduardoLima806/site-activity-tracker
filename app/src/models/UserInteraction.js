const mongoose = require('mongoose');
const mongoosePaginate = require('mongoose-paginate');

const UserInteractionSchema = new mongoose.Schema({
    website: {
        type: String,
        required: true
    },
    event: {
        type: String,
        required: true
    },
    user: {
        type: String,
        required: true
    },
    timestamp: {
        type: Date,
        default: Date.now
    }
});

UserInteractionSchema.plugin(mongoosePaginate);
mongoose.model('UserInteraction', UserInteractionSchema);
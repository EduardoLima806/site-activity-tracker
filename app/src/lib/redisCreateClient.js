var redis = require('redis');

exports.createClient = redis.createClient(6379, 'redis');
var express = require('express');
var ParseServer = require('parse-server').ParseServer;
var ParseDashboard = require('parse-dashboard');

var allowInsecureHTTP = false

var api = new ParseServer({
    // Parse Server settings
    databaseURI: 'mongodb://localhost:27017/dev', // Connection string for your MongoDB database
    // cloud: '/home/myApp/cloud/main.js', // Absolute path to your Cloud Code
    appId: 'myAppId',
    masterKey: 'myMasterKey', // Keep this key secret!
    fileKey: 'optionalFileKey',
    serverURL: 'http://localhost:1337/parse' // Don't forget to change to https if needed
});

var dashboard = new ParseDashboard({
    // Parse Dashboard settings
    "apps": [
      {
        "serverURL": "http://localhost:1337/parse",
        "appId": "myAppId",
        "masterKey": "myMasterKey",
        "appName": "MyApp"
      }
    ]
}, allowInsecureHTTP);

var app = express();

// make the Parse Server available at /parse
app.use('/parse', api);

// make the Parse Dashboard available at /dashboard
app.use('/dashboard', dashboard);

var httpServer = require('http').createServer(app);
httpServer.listen(4040);
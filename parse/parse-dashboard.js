var express = require('express');
var ParseDashboard = require('parse-dashboard');

var config = {
  "allowInsecureHTTP": true,
  "apps": [
    {
      "serverURL": "http://69.89.12.148:1337/parse",
      "appId": "Goblob",
      "masterKey": "cassandra",
      "appName": "Goblob"
    }
  ],"users": [
    {
      "user":"goblob",
      "pass":"goblob"
    }
  ]
};
var dashboard = new ParseDashboard(config, config.allowInsecureHTTP);

var app = express();

// make the Parse Dashboard available at /dashboard
app.use('/dashboard', dashboard);

var httpServer = require('http').createServer(app);
httpServer.listen(4040);
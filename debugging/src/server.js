/**
  # in seprate window
  node-inspector

  node --debug-brk src/server.js
  // uncommnet debugger;
  node --debug src/server.js

  # in separate window
  curl http://127.0.0.1:1337/
**/
var http = require('http');
var count = 0;

http.createServer(function (req, res) {
  // debugger;
  count += 1;
  res.writeHead(200, {'Content-Type': 'text/plain'});
  res.end('Hello Request ' + count + '\n');
}).listen(1337, '127.0.0.1');
console.log('Server running at http://127.0.0.1:1337/');

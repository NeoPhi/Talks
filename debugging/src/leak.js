/**
  node src/leak.js
**/
var memwatch = require('memwatch');
memwatch.on('leak', function(info) {
  console.log('LEAK');
  console.dir(info);
  process.exit(0);
});
memwatch.on('stats', function(stats) {
  console.log('STATS');
  console.dir(stats);
});  

var parsers = [];
var data = [];
var xml2js = require('xml2js');
var fs = require('fs');
function loadData() {
  fs.readFile('sample.xml', function(err, xml) {
    var parser = new xml2js.Parser();
    parsers.push(parser);
    parser.parseString(xml, function(err, json) {
      data.push(json);
    });
    process.nextTick(loadData);
  });
}

loadData();

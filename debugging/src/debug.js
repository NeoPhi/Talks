/**
  node src/debug.js
  DEBUG=* node src/debug.js
  DEBUG="worker:*" node src/debug.js
  DEBUG=* node src/debug.js 2>&1 | tee /dev/null
**/

var a = require('debug')('worker:a');
var b = require('debug')('worker:b');
var c = require('debug')('controller');

function workA() {
  a('doing lots of uninteresting work');
  setTimeout(workA, Math.random() * 1000);
}

workA();
c('worker a started');

function workB() {
  var wait = Math.random() * 2000;
  b('doing some work for %d', wait);
  setTimeout(workB, wait);
}

workB();
c('worker b started');

/**
  node src/heap.js | less
**/
var memwatch = require('memwatch');

function LeakingClass() {
}

var leaks = [];
var heapDiff = new memwatch.HeapDiff();
for (var i = 0; i < 100; i++) {
  leaks.push(new LeakingClass());
}
console.log(JSON.stringify(heapDiff.end(), null, '  '));

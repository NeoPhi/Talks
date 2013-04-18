/**
  node src/time.js
  node --prof src/time.js
  ./node_modules/.bin/node-tick-processor | less
**/
function addAwhile(stopTime) {
  var sum = 0;
  while (Date.now() < stopTime) {
    sum += 1;
  }
}

console.time('add-op');
addAwhile(Date.now() + 5 * 1000);
console.timeEnd('add-op');

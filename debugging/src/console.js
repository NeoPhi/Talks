/**
  node src/console.js
  node src/console.js 2> /dev/null
  node src/console.js 1> /dev/null
**/
var obj = {
  a: 12,
  b: {
    c: 'd'
  }
};

console.info('-------------------- console.log --------------------');
console.log('logged message');
console.log('util.inspect', 0.1, obj);
console.log('format syntax %d, %s, and %j', 0.1, obj, obj);

console.warn('-------------------- console.error --------------------');
console.error('logged message');
console.error('util.inspect', 0.1, obj, obj);
console.error('format syntax %d, %s, and %j', 0.1, obj, obj);

console.info('-------------------- console.dir --------------------');
console.dir(obj);

console.warn('-------------------- console.trace --------------------');
console.trace('db-op');

console.warn('-------------------- console.assert --------------------');
console.assert(1 === 1, 'one is one');
console.assert(1 === 2, 'one is two');

console.log('never logged');

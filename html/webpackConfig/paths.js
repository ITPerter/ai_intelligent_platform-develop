const path = require('path');

function resolvePath(dest) {
  return path.resolve(__dirname, dest);
}
module.exports = {
  HTML_PATH: resolvePath('../src/public/index.html'),
  LOGIN_PATH:resolvePath('../src/public/userLogin.html'),
  ENTRY_PATH: resolvePath('../src/index.js'),
  LOGIN_PATH:resolvePath('../src/userLogin.js'),
  OUTPUT_PATH: resolvePath('../dist'),
  JS_INCLUDE_PATH: resolvePath('../src'),
};

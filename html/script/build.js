const webpackProdConfig = require('../webpackConfig/webpack.config.prod');
const webpack = require('webpack');
console.log('webpackProdConfig is ', webpackProdConfig);
const compiler = webpack(webpackProdConfig);

compiler.run((error, stats) => {
  // console.log('error is ', error);
  // console.log('stats result is ', stats.toJson());
  if (error && stats.hasErrors()) {
    console.log('build result is ', stats.toJson());
  }
});

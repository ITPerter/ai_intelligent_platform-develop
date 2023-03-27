const webpack = require('webpack');
const webpackDevServer = require('webpack-dev-server');
const webpackConfigDev = require('../webpackConfig/webpack.config.dev');
const webpackDevConfig = require('../webpackConfig/webpack.config.dev');

process.env.NODE_ENV = 'development';

const compiler = webpack(webpackDevConfig);

const options = {
  contentBase: '../dist',
  hot: true,
  host: 'localhost',
  proxy:{
     '/api':{
         target:'http://localhost:8021',
         changeOrigin: true,
         pathRewrite:{
           '^/api':''
         }
     }
  }
};

webpackDevServer.addDevServerEntrypoints(webpackDevConfig, options);

const server = new webpackDevServer(compiler, options);
//9080
server.listen(9080, 'localhost', (error) => {
  console.log('server start in port 9080');
  if (error) {
    console.log('hei');
  }
});

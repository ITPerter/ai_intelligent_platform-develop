const webpackBaseConfig = require('./webpack.config.base');
const webpack = require('webpack');
const { merge } = require('webpack-merge');
const Paths = require('./paths');
module.exports = merge(webpackBaseConfig, {
  mode: 'development',
  entry: [
    'webpack-dev-server/client?http://0.0.0.0:9080',
    'webpack/hot/only-dev-server',
    Paths.ENTRY_PATH,
    
  ],
  module: {
    rules: [
      {
        test: /\.css$/,
        use: ['style-loader', 'css-loader'],
      },
      {
        test: /\.less$/,
        use: [
          'style-loader',
          {
            loader:'css-loader',
            options:{
              modules:false,
              importLoaders:1,
              //localIdentName: '[path][name]__[local]--[hash:base64:5]'
            }
          },
          {
            loader: 'less-loader',
            options: {
              lessOptions: {
                javascriptEnabled: true,
              },
            },
          },
        ],
      },
    ],
  },
  plugins: [new webpack.HotModuleReplacementPlugin()],
});

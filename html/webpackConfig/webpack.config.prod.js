const { merge } = require('webpack-merge');
const webpackBaseConfig = require('./webpack.config.base');
const webpack = require('webpack');
const Paths = require('./paths');
const BundleAnalyzerPlugin=require('webpack-bundle-analyzer').BundleAnalyzerPlugin;
const WebpackRemoveConsole=require('webpack-remove-console');
const RemoveConsole=require('../webpackPlugin/removeConsole');
const handler = (percentage, message, ...args) => {
  console.info('percentage is ' + percentage);
};
module.exports = merge(webpackBaseConfig, {
  mode: 'production',
  entry: {
    main: Paths.ENTRY_PATH,
  },
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
          'css-loader',
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
  optimization:{
    splitChunks:{
      cacheGroups:{
        vendor:{
          test:/node_modules/,
          chunks:'initial',
          name:'vendor',
        }
      }
    }
  },
  plugins: [new webpack.ProgressPlugin(),new BundleAnalyzerPlugin(),new RemoveConsole({name:'haoxin_qiu'})],
});

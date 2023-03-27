const { CleanWebpackPlugin } = require('clean-webpack-plugin');
const HtmlWebpackPlugin = require('html-webpack-plugin');
const Paths = require('./paths.js');
process.env.NODE_ENV = 'development';
module.exports = {
  output: {
    path: Paths.OUTPUT_PATH,
    filename: '[name].[hash:8].js',
    publicPath: './',
  },
  resolve: {
    extensions: ['.js', '.jsx', '.json'],
  },
  module: {
    rules: [
      {
        test: /\.jsx?$/,
        loader: 'babel-loader',
        include: Paths.JS_INCLUDE_PATH,
      },
      {
        test: /\.(png|jpe?g|gif)$/,
        use: [

          {
            loader:'url-loader',
            options:{
              limit:102400,
            }
          },
        
        ],
      },
    ],
  },
  plugins: [
    new CleanWebpackPlugin(),
    new HtmlWebpackPlugin({ inject: true, template: Paths.HTML_PATH }),
  ],
};

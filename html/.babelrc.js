module.exports = {
  presets: [['@babel/preset-env'], 'babel-preset-react-app'],
  plugins:[
     [
       "import",
       {
         libraryName:'antd',
         style:true,
       }
     ]
  ]
};

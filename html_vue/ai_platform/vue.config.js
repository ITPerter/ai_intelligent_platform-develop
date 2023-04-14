// const { defineConfig } = require('@vue/cli-service')
// //
// module.exports = defineConfig({
//   transpileDependencies: true,
//   devServer: {
//     proxy: {
//       '/api': {
//         target: 'http://localhost:8021/',
//         changeOrigin: true, // 是否跨域
//         pathRewrite: {
//           '/api': ''  // 重写路径，去掉 /api 前缀
//         }
//       }
//     }
//   }
// })

//   // devServer: {
//   //   port: 3000, // 自定义前端访问接口
//   //   // 配置开发环境下的方向代理
//   //   // proxy:{
//   //   //   "/Servers1":{
//   //   //     target:"http://localhost:8085/",
//   //   //     // changeOrigin:true
//   //   //   }
//   //   // }
//   // }

module.exports = {
  devServer: {
    open: true,//vue项目启动时自动打开浏览器
    host: 'localhost',//本机
    port: 3000,//端口号
    https: false,
    //以上的ip和端口是我们本机的;下面为需要跨域的
    proxy: {  //配置跨域
      '/api': {
        target: 'http://localhost:8021',  //这里后台的地址模拟的;应该填写你们真实的后台接口
        ws: true,
        changOrigin: true,  //允许跨域
        pathRewrite: {
          ['^' + process.env.VUE_APP_BASE_API]: ''
        }
      }
    }
  }
}


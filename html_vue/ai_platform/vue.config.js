const { defineConfig } = require('@vue/cli-service')

module.exports = defineConfig({
  transpileDependencies: true,
  devServer: {
    port: 3000, // 自定义前端访问接口
    // 配置开发环境下的方向代理
    // proxy:{
    //   "/Servers1":{
    //     target:"http://localhost:8085/",
    //     // changeOrigin:true
    //   }
    // }
  }
})

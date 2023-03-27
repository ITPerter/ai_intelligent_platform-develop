import baseURL from "./config.js";
// 引入baseURL 后面会讲到
export default function request(options){
  // options为调用时传入的参数对象
  return new Promise((resolve,reject)=>{
    wx.request({
      header:{
        'pc-token':'4a82b23dbbf3b23fd8aa291076e660ec'
         //定义公共头部信息
      },
      url:baseURL+options.url, 
      // 拼接请求地址
      data:options.data||{},
      // 传入data参数
      method:options.method||'get',
      // 传入请求类型默认为get
      success:function(res){
        resolve(res)
        // 成功回调
      },
      fail:function(res){
        reject(res)
        // 失败回调
      }
    })
  })
}
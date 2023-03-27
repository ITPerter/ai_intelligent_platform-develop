import requset from "./netwrok";
// 引入requset请求
export  function getToken(page){
  // 配置当前请求的地址和传入的参数
  return requset({
    url:"auth/getUserToken",
    method:'post',
    data: page
  })
}

export  function getChat(page){
    // 配置当前请求的地址和传入的参数
    return requset({
      url:"third/chat_v2",
      method:'post',
      data: page
    })
  }
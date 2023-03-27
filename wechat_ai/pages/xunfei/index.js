

const recorderManager = wx.getRecorderManager();
var wxst; //语音websocket
var status = 0;  // 音频的状态
var iatResult = [] // 识别结果
const searchoptions = {
  duration: 60000,//指定录音的时长，单位 ms
  sampleRate: 8000,//采样率
  numberOfChannels: 1,//录音通道数
  encodeBitRate: 48000,//编码码率
  format: 'PCM',//音频格式
  frameSize: 5,//指定帧大小，单位 KB
}

const app = getApp()

const util = require('../../utils/util.js')

const plugin = requirePlugin("WechatSI")
// 引入封装好的单个请求
import {getToken,getChat} from "../../api/request.js";
 
 
// 调用请求获取数据

import { language } from '../../utils/conf.js'
// 获取**全局唯一**的语音识别管理器**recordRecoManager**
const manager = plugin.getRecordRecognitionManager()


Page({
  data: {
    dialogList: [
      // {
      //   // 当前语音输入内容
      //   create: '04/27 15:37',
      //   lfrom: 'zh_CN',
      //   lto: 'en_US',
      //   text: '这是测试这是测试这是测试这是测试',
      //   translateText: 'this is test.this is test.this is test.this is test.',
      //   voicePath: '',
      //   translateVoicePath: '',
      //   autoPlay: false, // 自动播放背景音乐
      //   id: 0,
      // },
    ],
    scroll_top: 10000, // 竖向滚动条位置

    bottomButtonDisabled: false, // 底部按钮disabled

    tips_language: language[0], // 目前只有中文

    initTranslate: {
      // 为空时的卡片内容
      create: '04/27 15:37',
      text: '等待说话',
    },
    msg:"",
    userId: "",
    userName:"",

    currentTranslate: {
      // 当前语音输入内容
      create: '04/27 15:37',
      text: '等待说话',
    },
    recording: false,  // 正在录音
    recordStatus: 0,   // 状态： 0 - 录音中 1- 翻译中 2 - 翻译完成/二次翻译

    toView: 'fake',  // 滚动位置
    lastId: -1,    // dialogList 最后一个item的 id
    currentTranslateVoice: '', // 当前播放语音路径

  },
  
  getUrl() {
    wx.request({
      url: 'http://172.17.4.15:8088/url',
      method: 'GET',
      header: {
        'content-type': 'application/json' // 默认值
      },
      success(result) {
        apiUrl = result.data;
        console.log(apiUrl);
      }
    })
  },
  start: function () {
    wxst = wx.connectSocket({ // 开启websocket连接
        url: apiUrl,
        method: 'GET',
        success: function (res) {
            recorderManager.start(options);//开始录音
        }
    });
},
/* 结束录音 */
stop: function() {
  recorderManager.stop(); // 停止录音
  recorderManager.onStop((result) => {
      console.log('录音结束' + result); // tempFilePath是录音的文件暂时的存放路径
  });
},
  start_say: function (e) { //开始录音按钮
    let detail = e.detail || {}
    let buttonItem = detail.buttonItem || {}
    var that = this;
    wx.getSetting({//查看用户有没有开启语音权限
      success(res) {
        if (res.authSetting['scope.record']) {
          wx.authorize({
            scope: 'scope.record',
            success() {
              var xfurl = "";
              
              wx.request({//请求接口 获取讯飞语音鉴权
                url: "http://172.17.4.162:8088/url",
                method: "get",
                header: {
                  'content-type': 'application/json' // 默认值
                },
         
                success: function (res) {
                 console.log(res);
                  if (res.statusCode == "200" && res.data) {
                   
                    xfurl = res.data;
                    wxst = wx.connectSocket({ // 开启websocket连接
                      url: xfurl,
                      method: 'GET',
                      success: function (res) {
                        console.log(res);
                        // that.setData({//我这里是个遮罩层 开启他
                        //   shows: true,
                        // })
                        recorderManager.start(searchoptions);//开始录音
                        that.setData({
                          recordStatus: 0,
                          recording: true,
                          currentTranslate: {
                            // 当前语音输入内容
                            create: util.recordTime(new Date()),
                            text: '正在聆听中',
                            lfrom: buttonItem.lang,
                            lto: buttonItem.lto,
                          },
                        })
                        that.scrollToNew();
                      }
                    });
                  } else {
                    wx.showToast({
                      title: '获取语音鉴权失败',
                      icon: 'none',
                      mask: true,
                      duration: 3000
                    })
                  }
                },
                fail: function (res) {
            
                  wx.showToast({
                    title: '获取语音鉴权失败',
                    icon: 'none',
                    mask: true,
                    duration: 3000
                  })
                }
              })
            },
            fail() {
              wx.showModal({
                title: '微信授权',
                content: '您当前未开启语音权限，请在右上角设置(···)中开启“录音功能”',
                showCancel: false,
                success(res) {
                  if (res.confirm) {
                    console.log('用户点击确定')
                  }
                }
              })
            }
          })
        }else{
          wx.showModal({
            title: '微信授权',
            content: '您当前未开启语音权限，请在右上角设置(···)中开启“录音功能”',
            showCancel: false,
            success(res) {
              if (res.confirm) {
                console.log('用户点击确定')
              }
            }
          })
        }
      }
    })
    
  },
  end_say: function (e) { //结束录音按钮
    let detail = e.detail || {}  // 自定义组件触发事件时提供的detail对象
    let buttonItem = detail.buttonItem || {}
    var that = this;
    recorderManager.stop();
    // that.setData({//关闭遮罩层
    //   shows: false,
    // })
    this.setData({
      bottomButtonDisabled: true,
    })

  },
  playmp3: function() {
   
    var srcurl="/mp3/11.mp3"
    
    const innerAudioContext = wx.createInnerAudioContext()
    
    innerAudioContext.autoplay = true
    
    innerAudioContext.src = srcurl
    
    innerAudioContext.onPlay(() => {
    
    console.log('开始播放')
    
    })
    
    
    },



  /**
   * 按住按钮开始语音识别
   */
  streamRecord: function(e) {
    // console.log("streamrecord" ,e)
    let detail = e.detail || {}
    let buttonItem = detail.buttonItem || {}
    manager.start({
      lang: buttonItem.lang,
    })

    this.setData({
      recordStatus: 0,
      recording: true,
      currentTranslate: {
        // 当前语音输入内容
        create: util.recordTime(new Date()),
        text: '正在聆听中',
        lfrom: buttonItem.lang,
        lto: buttonItem.lto,
      },
    })
    this.scrollToNew();

  },


  /**
   * 松开按钮结束语音识别
   */
  streamRecordEnd: function(e) {

    // console.log("streamRecordEnd" ,e)
    let detail = e.detail || {}  // 自定义组件触发事件时提供的detail对象
    let buttonItem = detail.buttonItem || {}

    // 防止重复触发stop函数
    if(!this.data.recording || this.data.recordStatus != 0) {
      console.warn("has finished!")
      return
    }
    recorderManager.stop();
    manager.stop()

    this.setData({
      bottomButtonDisabled: true,
    })
  },
  AudioContext :function(content) {
    const innerAudioContext = wx.createInnerAudioContext()
    innerAudioContext.autoplay = true
    plugin.textToSpeech({
    lang: "zh_CN",
    tts: true,
    content: content,
    success: function(res) {
    // console.log(res);
    // console.log("succ tts", res.filename);
    innerAudioContext.src = res.filename;
    innerAudioContext.onPlay(() => {
    // console.log('开始播放');
    })
    innerAudioContext.onStop(() => {
    // console.log('i am onStop');
    innerAudioContext.stop();
    //播放停止，销毁该实例
    innerAudioContext.destroy();
    })
    innerAudioContext.onEnded(() => {

    // console.log('i am onEnded');
    //播放结束，销毁该实例
    innerAudioContext.destroy();
    // console.log('已执行destory()');
    })
    innerAudioContext.onError((res) => {
    /* console.log(res.errMsg);
    console.log(res.errCode); */
    innerAudioContext.destroy();
    })
    },
    fail: function(res) {
    console.log("fail tts", res)
    },
    
    })
    },


  /**
   * 翻译
   */
  translateText: function(item, index) {
    // let lfrom =  item.lfrom || 'zh_CN'
    // let lto = item.lto || 'en_US'
    
    let tmpDialogList = this.data.dialogList.slice(0)
    getChat({
      "chatMsg":item.text,
      "robotId":"10",
    }).then((res)=>{
      // console.log(res.data.data.msg);
      this.setData({
        msg: res.data.data.msg,
      })
      if(res.data.data.msg == "当前词槽需要澄清"){
        this.setData({
          msg: res.data.data.currentSlot.clarification,
        })
      }
      this.AudioContext(this.data.msg)
      let tmpTranslate = Object.assign({}, item, {
        autoPlay: true, // 自动播放背景音乐
        translateText: this.data.msg,
     
      })
    
        tmpDialogList[index] = tmpTranslate
        this.setData({
          dialogList: tmpDialogList,
    
        })
      
        wx.hideLoading()
        this.setData({
          recordStatus: 1,
        })
    
        this.setData({
          bottomButtonDisabled: false,
          recording: false,
      })
      this.scrollToNew();

      // console.log(res);
      // let tmpTranslate = Object.assign({}, item, {
      //   autoPlay: true, // 自动播放背景音乐
      //   translateText: res.data.data.msg,
  
      // })
      //   tmpDialogList[index] = tmpTranslate
      // this.AudioContext(tmpDialogList[index].translateText)
      // this.setData({
      //   dialogList: tmpDialogList,
      //   bottomButtonDisabled: false,
      //   recording: false,
      // })
    })

   

    

    // console.log(res);
         



               

    // plugin.translate({
    //   lfrom: lfrom,
    //   lto: lto,
    //   content: item.text,
    //   tts: true,
    //   success: (resTrans)=>{

    //     let passRetcode = [
    //       0, // 翻译合成成功
    //       -10006, // 翻译成功，合成失败
    //       -10007, // 翻译成功，传入了不支持的语音合成语言
    //       -10008, // 翻译成功，语音合成达到频率限制
    //     ]

    //     if(passRetcode.indexOf(resTrans.retcode) >= 0 ) {
    //       let tmpDialogList = this.data.dialogList.slice(0)

    //       if(!isNaN(index)) {

    //         let tmpTranslate = Object.assign({}, item, {
    //           autoPlay: true, // 自动播放背景音乐
    //           translateText: "梧州水位20米",
    //           translateVoicePath: resTrans.filename || "",
    //           translateVoiceExpiredTime: resTrans.expired_time || 0
    //         })

    //         tmpDialogList[index] = tmpTranslate
            
    //         this.setData({
    //           dialogList: tmpDialogList,
    //           bottomButtonDisabled: false,
    //           recording: false,
    //         })

    //         this.scrollToNew();

    //       } else {
    //         console.error("index error", resTrans, item)
    //       }
    //     } else {
    //       console.warn("翻译失败", resTrans, item)
    //     }

    //   },
    //   fail: function(resTrans) {
    //     console.error("调用失败",resTrans, item)
    //     this.setData({
    //       bottomButtonDisabled: false,
    //       recording: false,
    //     })
    //   },
    //   complete: resTrans => {
    //     this.setData({
    //       recordStatus: 1,
    //     })
    //     wx.hideLoading()
    
    //   }
    // })

  },
  /**
   * 修改文本信息之后触发翻译操作
   */
  translateTextAction: function(e) {
    // console.log("translateTextAction" ,e)
    let detail = e.detail  // 自定义组件触发事件时提供的detail对象
    let item = detail.item
    let index = detail.index
    this.translateText(item, index)



  },

  /**
   * 语音文件过期，重新合成语音文件
   */
  expiredAction: function(e) {
    let detail = e.detail || {}  // 自定义组件触发事件时提供的detail对象
    let item = detail.item || {}
    let index = detail.index
    console.log("77");
    if(isNaN(index) || index < 0) {
      return
    }

    let lto = item.lto 

    plugin.textToSpeech({
      lang: lto,
      content: item.translateText,
      success: resTrans => {
        if(resTrans.retcode == 0) {
  
          let tmpDialogList = this.data.dialogList.slice(0)

          let tmpTranslate = Object.assign({}, item, {
            autoPlay: true, // 自动播放背景音乐
            translateVoicePath: resTrans.filename,
            translateVoiceExpiredTime: resTrans.expired_time || 0
          })

          tmpDialogList[index] = tmpTranslate


          this.setData({
            dialogList: tmpDialogList,
          })

        } else {
          console.warn("语音合成失败", resTrans, item)
        }
      },
      fail: function(resTrans) {
        console.warn("语音合成失败", resTrans, item)
      }
  })
  },

  /**
   * 初始化为空时的卡片
   */
  initCard: function () {
    let initTranslateNew = Object.assign({}, this.data.initTranslate, {
      create: util.recordTime(new Date()),
    })
    this.setData({
      initTranslate: initTranslateNew
    })
  },


  /**
   * 删除卡片
   */
  deleteItem: function(e) {
    // console.log("deleteItem" ,e)
    let detail = e.detail
    let item = detail.item

    let tmpDialogList = this.data.dialogList.slice(0)
    let arrIndex = detail.index
    tmpDialogList.splice(arrIndex, 1)

    // 不使用setTImeout可能会触发 Error: Expect END descriptor with depth 0 but get another
    setTimeout( ()=>{
      this.setData({
        dialogList: tmpDialogList
      })
      if(tmpDialogList.length == 0) {
        this.initCard()
      }
    }, 0)

  },


  /**
   * 识别内容为空时的反馈
   */
  showRecordEmptyTip: function() {
    this.setData({
      recording: false,
      bottomButtonDisabled: false,
    })
    wx.showToast({
      title: this.data.tips_language.recognize_nothing,
      icon: 'success',
      image: '/image/no_voice.png',
      duration: 1000,
      success: function (res) {

      },
      fail: function (res) {
        console.log(res);
      }
    });
  },


  /**
   * 初始化语音识别回调
   * 绑定语音播放开始事件
   */
  initRecord: function() {
    //有新的识别内容返回，则会调用此事件
    manager.onRecognize = (res) => {
      let currentData = Object.assign({}, this.data.currentTranslate, {
                        text: res.result,
                      })
      this.setData({
        currentTranslate: currentData,
      })
      this.scrollToNew();
    }

    // 识别结束事件
    manager.onStop = (res) => {

      let text = res.result

      if(text == '') {
        this.showRecordEmptyTip()
        return
      }

      let lastId = this.data.lastId + 1

      let currentData = Object.assign({}, this.data.currentTranslate, {
                        text: res.result,
                        translateText: "小R正在思考...",
                        id: lastId,
                        voicePath: res.tempFilePath
                      })
      this.setData({
        currentTranslate: currentData,
        recordStatus: 1,
        lastId: lastId,
      })

      this.scrollToNew();

      this.translateText(currentData, this.data.dialogList.length)
    }

    // 识别错误事件
    manager.onError = (res) => {

      this.setData({
        recording: false,
        bottomButtonDisabled: false,
      })

    }

    // 语音播放开始事件
    wx.onBackgroundAudioPlay(res=>{

      const backgroundAudioManager = wx.getBackgroundAudioManager()
      let src = backgroundAudioManager.src

      this.setData({
        currentTranslateVoice: src
      })

    })
  },

  /**
   * 设置语音识别历史记录
   */
  setHistory: function() {
    try {
      let dialogList = this.data.dialogList
      dialogList.forEach(item => {
        item.autoPlay = false
      })
      wx.setStorageSync('history',dialogList)

    } catch (e) {

      console.error("setStorageSync setHistory failed")
    }
  },

  /**
   * 得到历史记录
   */
  getHistory: function() {
    try {
      let history = wx.getStorageSync('history')
      if (history) {
          let len = history.length;
          let lastId = this.data.lastId
          if(len > 0) {
            lastId = history[len-1].id || -1;
          }
          this.setData({
            dialogList: history,
            toView: this.data.toView,
            lastId: lastId,
          })
      }

    } catch (e) {
      // Do something when catch error
      this.setData({
        dialogList: []
      })
    }
  },

  /**
   * 重新滚动到底部
   */
  scrollToNew: function() {
    this.setData({
      toView: this.data.toView
    })
  },

  onShow: function() {
    var that = this;
    recorderManager.onStart(() => {//开始录音时触发
      status = 0;
      iatResult = []
      console.log('recorder start')
    });
    recorderManager.onError((res) => {//错误回调
      console.log(res);
    });
    recorderManager.onStop((res) => {//结束录音时触发
      console.log('recorder stop', res)
      status = 2;
      var sendsty = '{"data":{"status":2,"audio":"","format":"audio/L16;rate=8000","encoding":"raw"}}'
      wxst.send({
        data: sendsty
      })
    });
    recorderManager.onFrameRecorded((res) => {//每帧触发
      const { frameBuffer } = res
      var int16Arr = new Int8Array(res.frameBuffer);
      const base64 = wx.arrayBufferToBase64(int16Arr)
      switch (status) {
        case 0:
          status = 1;
          var sendsty = '{"common":{"app_id":"1a2a4aa8"},"business":{"language":"zh_cn","domain":"iat","accent":"mandarin","dwa":"wpgs","vad_eos":1000},"data":{"status":0,"format":"audio/L16;rate=8000","encoding":"raw","audio":"' + base64 + '"}}'
          wxst.send({
            data: sendsty
          })
          break;
        case 1:
          var sendsty = '{"data":{"status":1,"format":"audio/L16;rate=8000","encoding":"raw","audio":"' + base64 + '"}}'
          wxst.send({
            data: sendsty
          })
          break;
        default:
          console.log("default");
      }
    })

    this.scrollToNew();

    this.initCard()

    if(this.data.recordStatus == 2) {
      wx.showLoading({
        // title: '',
        mask: true,
      })
    }

  },

  onLoad: function () {
    var that = this;
    wx.onSocketOpen((res) => {// websocket打开
      console.log('监听到 WebSocket 连接已打开' + res);
    })
    wx.onSocketError((err) => {//连接失败
      console.log('websocket连接失败', err);
      wx.showToast({
        title: 'websocket连接失败',
        icon: 'none',
        duration: 2000,
        mask: false
      })
    })
    wx.onSocketMessage((res) => {//接收返回值
      var data = JSON.parse(res.data)
      if (data.code != 0) {
        console.log("error code " + data.code + ", reason " + data.message)
        return
      }
      let str = ""
      if (data.data.status == 2) {//最终识别结果
        // data.data.status ==2 说明数据全部返回完毕，可以关闭连接，释放资源
        wxst.close();
      } else {//中间识别结果
      }
      iatResult[data.data.result.sn] = data.data.result
      if (data.data.result.pgs == 'rpl') {
        data.data.result.rg.forEach(i => {
          iatResult[i] = null
        })
      }
      iatResult.forEach(i => {
        if (i != null) {
          i.ws.forEach(j => {
            j.cw.forEach(k => {
              str += k.w
            })
          })
        }
      })
      that.setData({
        searchKey: str //这个是中间的语音识别结果
      })
    })
    wx.onSocketClose((res) => {//WebSocket连接已关闭！
      var that = this;
      recorderManager.stop();
      that.setData({//把之前开开的遮罩层关上
        shows: false,
      })
      var str = that.data.searchKey;
      console.log(str);
      str = str.replace(/\s*/g, "");//去除空格
      if (str.substr(str.length - 1, 1) == "。") {//去除句号
        str = str.substr(0, str.length - 1);
      }
      console.log(str);
      that.setData({
        searchKey: str//这个是最后确定的语音识别结果
      })
      console.log('WebSocket连接已关闭！')
    })
    this.getHistory()
    this.initRecord()
    this.setData({toView: this.data.toView})
    app.getRecordAuth()

    this.playmp3()
  },

  onHide: function() {
    this.setHistory()
  },
})

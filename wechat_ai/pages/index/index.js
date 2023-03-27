
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
    innerAudioContext : wx.createInnerAudioContext(),
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
      defaultData: {
            title: "我的主页", // 导航栏标题
        },
    scroll_top: 10000, // 竖向滚动条位置

    bottomButtonDisabled: false, // 底部按钮disabled

    tips_language: language[0], // 目前只有中文

    initTranslate: {
      // 为空时的卡片内容
      create: '04/27 15:37',
      text: '小R等待您说话',
    },
    msg:"",
    userId: "",
    userName:"",
    token:"",
    data:"",

    currentTranslate: {
      // 当前语音输入内容
      create: '04/27 15:37',
      text: '小R等待您说话',
    },
    recording: false,  // 正在录音
    recordStatus: 0,   // 状态： 0 - 录音中 1- 翻译中 2 - 翻译完成/二次翻译

    toView: 'fake',  // 滚动位置
    lastId: -1,    // dialogList 最后一个item的 id
    currentTranslateVoice: '', // 当前播放语音路径

  },
  playmp3: function() {

    var srcurl="/mp3/14.mp3"
    const innerAudio = wx.createInnerAudioContext()
    this.data.innerAudioContext = innerAudio
    innerAudio.autoplay = true
    innerAudio.src = srcurl
    innerAudio.onPlay(() => {
    })
    innerAudio.onEnded(()=>{
      innerAudio.destroy()
    })
    },
    vibrateShortTap: function () {
      // 使手机振动15m
      wx.vibrateShort();
},

  /**
   * 按住按钮开始语音识别
   */
  streamRecord: function(e) {

    wx.vibrateShort();
    // console.log("streamrecord" ,e)
    let detail = e.detail || {}
    let buttonItem = detail.buttonItem || {}
    manager.start({
      lang: buttonItem.lang,
    })
    // //销毁音频实例
    // innerAudioContext.stop();
    this.data.innerAudioContext.destroy()
    this.setData({
      recordStatus: 0,
      recording: true,
      currentTranslate: {
        // 当前语音输入内容
        create: util.recordTime(new Date()),
        text: '小R正在聆听中',
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

    manager.stop()

    this.setData({
      bottomButtonDisabled: true,

    })
  },
  AudioContext :function(content) {

    const innerAudioContext = wx.createInnerAudioContext()
    this.data.innerAudioContext = innerAudioContext
    innerAudioContext.autoplay = true
    plugin.textToSpeech({
    lang: "zh_CN",
    tts: true,
    content: content,
    success: function(res) {
    // console.log(res);
    console.log("succ tts", res.filename);
    innerAudioContext.src = res.filename;
    innerAudioContext.onPlay(() => {
    console.log('开始播放');
    })
    innerAudioContext.onStop(() => {
    console.log('i am onStop');
    // innerAudioContext.stop();
    //播放停止，销毁该实例
    innerAudioContext.destroy();
    })
    innerAudioContext.onEnded(() => {

    // console.log('i am onEnded');
    //播放结束，销毁该实例
    innerAudioContext.destroy();
    console.log('已执行destory()');
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
      "userId":this.data.userId,
      "token":this.data.token,
      "data" :this.data.data

    }).then((res)=>{

      this.setData({
        msg: res.data.data.msg,
        data : res.data.data
      })

      if(res.data.data.state == "CLARIFY"){f
        this.setData({
          msg: res.data.data.currentSlot.clarification
        })
      }else if(res.data.data.state =="COMPLETE"){
        this.setData({
          data : ""
        })
        if(res.data.data.intent.number == "manual_service"){
          wx.showModal({
            title: '提示',
            content: '确定拨打077-14163482转人工客服',
            success: function (res) {
              if (res.confirm) {//这里是点击了确定以后
                wx.makePhoneCall({
                  phoneNumber: "077/14163482",
                  success: (result) => {
                  },
                  fail: (e) => {
                  },
                  complete: () => {}
                })
              } else {//这里是点击了取消以后
                console.log('用户点击取消')
              }
            }
          })
        }

      }
      this.AudioContext(this.data.msg)
      let tmpTranslate = Object.assign({}, item, {
        autoPlay: true, // 自动播放背景音乐
        translateText: this.data.msg,
        wordSlots : res.data.data.intent == null ? null : res.data.data.intent.chatSlotList ,
        state : res.data.data.state
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
    // wx.onBackgroundAudioPlay(res=>{

    //   const backgroundAudioManager = wx.getBackgroundAudioManager()
    //   let src = backgroundAudioManager.src

    //   this.setData({
    //     currentTranslateVoice: src
    //   })

    // })
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
    // this.getHistory()
    this.initRecord()
    this.setData({toView: this.data.toView})
    app.getRecordAuth()
    getToken({
      "password":"admin",
      "userName":"admin"
    }).then((res)=>{
         console.log(res.data.data);
         this.data.userId = res.data.data.userId
         this.data.token = res.data.data.chatToken
    })

    this.playmp3()

  },

  onHide: function() {
    // this.setHistory()
  },
})

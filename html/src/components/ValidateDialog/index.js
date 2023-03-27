/*
 *  对话测试
 */
import React, { Component } from 'react';
import './index.less';
import { Modal } from 'antd';
// import Utility from '../../utils/utils';
import { connect } from 'react-redux';
import { isEmpty, FilterMaxId } from '../../utils';
import Actions from '../../actions';
import ClassNames from 'class-names';
const confirm = Modal.confirm;
const prefixCls = 'vi-vd';
class ValidateDialog extends Component {
  constructor(props) {
    super(props);
    this.chatToken="";
  }
  state = {
    dialogList: [],
    visible: false,
    robotId: null,
  };
  componentWillReceiveProps(nextProps) {
    if (nextProps.robotId !== this.props.robotId) {
      this.setState({
        visible: nextProps.visible,
        robotId: nextProps.robotId,
        
      });
      this.chatToken="";
    } else {
      this.setState({
        visible: nextProps.visible,
        robotId: nextProps.robotId,
      });
    }
    if (this.props.visible === true && nextProps.visible === false) {
      this.setState({
        dialogList: [],
      });
    }
    if(nextProps.visible==true&& this.chatToken==''){
       this.fetchChatSession();
    }
  }

  componentDidMount() {}

  handleClick = (item) => {
    const { onMarkClick } = this.props;
    onMarkClick && onMarkClick(item);
  };

  renderWordslots = (wordslotList) => {
    if (wordslotList == null || wordslotList.length === 0) return null;
    const str = wordslotList.map((item) => {
      const value=item && item.slotState!='VERIFY_CHOOSE' ? item?.verifyValueList[0]?? '' : '';
      return  <li key={item.number}>
        <span className={'swNum'}>{item.number}</span>
        <span className={'swName'} style={{marginLeft:10}}>{value}</span>
      </li>
    });
    return (
      <div className={'wsListWrapper'}>
        <div>词槽:</div>
        <ul className={'wsList'}>{str}</ul>
      </div>
    );
  };

  renderDialogList = () => {
    const { dialogList } = this.state;
    const dialogStr = dialogList.map((item) => (
      <li key={item.id} className={item.className}>
        {item.intentionName
          ? this.renderIntentionDialog(item)
          : this.renderNormalText(item)}
      </li>
    ));
    return (
      <ul ref={(el) => (this.ULWrapper = el)}>
        <li className={'system'}>
          <div className={'system'}>
            <div>Hi~我是小R机器人</div>
            <div>请问我能帮您做点什么？</div>
          </div>
        </li>
        {dialogStr}
      </ul>
    );
  };

  renderNormalText = (item) => {
    return <div className={item.className}>{item.text}</div>;
  };
 /**
  * 
  * @param {*} item 
  *         <ul className={item.className}>
           {
             customeList && customeList.map(item=>{
               
             })
           }
        </ul>
  */
  renderIntentionDialog = (item) => {
    const {customeList}=item;
    return (

        <div className={item.className}>
          {item.text ? <div>{item.text}</div> : null}
          <div>
            <span>意图:</span>
            <span>{item.intentionName}</span>
          </div>
          {this.renderWordslots(item.wordslots)}
          <ul style={{marginTop:10,cursor:'pointer'}}>
            {customeList && <div>请选择一条数据:</div>}
            {
              customeList && customeList.map((itemData,index)=>{
              return <li key={index} onClick={()=>this.handleCustomerClick(itemData)}>{itemData}</li>
              })
            } 
          </ul>
        </div>

    );
  };
  //自定义选项选择
  handleCustomerClick(item){
    console.log("you click custome data is ",item);
    this.addUserSay(item);

  }
  scroll = () => {
    if (this.BodyWrapper && this.BodyWrapper.scrollHeight > 400) {
      this.BodyWrapper.scrollTop =
        this.BodyWrapper.scrollHeight +
        this.BodyWrapper.offsetHeight +
        this.BodyWrapper.scrollTop;
    }
    if (this.ULWrapper && this.ULWrapper.scrollHeight > 400) {
      const offsetHeight = this.ULWrapper.offsetHeight;
      const scrollTop = this.ULWrapper.scrollTop;
      const scrollHeight = this.ULWrapper.scrollHeight;
      console.log(
        'offsetHeight is ' +
          offsetHeight +
          ' and scrollTop is ' +
          scrollTop +
          ' and scrollHeight is ' +
          scrollHeight
      );
      this.ULWrapper.scrollTop =
        this.ULWrapper.scrollHeight +
        this.ULWrapper.offsetHeight +
        this.ULWrapper.scrollTop;
      console.log('scrollTop is ' + this.ULWrapper.scrollTop);
    }
  };

  handleKeyUp = (e) => {
    const code = e.keyCode;

    if (code === 13) {
      const val = e.target.value;
      if (isEmpty(val)) {
        return;
      }
      this.addUserSay(val,()=>{
        e.target.value="";
      });
    }
  };
  addUserSay(val,callBack){
    const _this=this;
    const { dialogList, robotId } = this.state;
    const { dispatch } = this.props;

    // e.target.value = '';
    let id = FilterMaxId(dialogList, 'id');
    dialogList.push({ text: val, id, className: 'use' });
    const tempArr = dialogList.filter((item) => item.id !== 0);

    this.setState(
      {
        dialogList: tempArr,
      },
      () => {
        _this.handleChat({
          robotId,
          chatMsg: val,
        });
      }
    );
    callBack && callBack();
    setTimeout(() => this.scroll(), 100);
  }
  //对话
  handleChat(params) {
    const _this = this;
    const { dispatch } = this.props;
    // const {chatToken}=this.state;
    let dialogList = this.state.dialogList.slice(0);
    params = {
      ...params,
      token: this.chatToken,
    };
    dispatch({
      type: Actions.CHAT,
      payload: params,
      callback: function (response) {
        const { code, data:{intent,currentSlot,state,msg} } = response;
        if (code == 0) {
          const id = FilterMaxId(dialogList, 'id');
          const clarification=state=='COMPLETE' ? "意图完成" : currentSlot?.clarification ?? msg;
          
          dialogList.push({
            className: 'system',
            id,
            text: clarification,
            intentionName: intent && intent.name,
            wordslots: intent && intent.chatSlotList,
            //这个是用户选择的列表，当currentSlot的slotState为VERIFY_CHOOSE时，用户选择一个当做话术向后端传
            customeList:currentSlot && currentSlot.slotState=="VERIFY_CHOOSE" ? currentSlot.verifyValueList : null,
          });
          const temp = dialogList.filter((item) => item.id !== 0);
          _this.setState(
            {
              dialogList: temp,
            },
            () => {}
          );
          setTimeout(() => _this.scroll(), 100);
        }
      },
    });
  }
  fetchChatSession(cb) {
    const _this = this;
    const { dispatch } = this.props;
    dispatch({
      type: Actions.FETCH_CHAT_SESSION,
      payload: {
        userName: 'admin',
        password: 'admin'

      },
      callback: function (result) {
        const { code, msg, data } = result;
        if (code == 0) {
          _this.chatToken=data.chatToken;
          cb && cb();
        } else {
          message.error(msg);
        }
      },
    });
  }
  handleReset = () => {
    const { dialogList } = this.state;
    const _this=this;
    this.fetchChatSession(()=>{
      let id = FilterMaxId(dialogList, 'id');
      dialogList.push({
        className: 'reset',
        id,
        text:
          '--------------------------------  已重置对话  -----------------------------------',
      });
      this.setState(
        {
          dialogList,
        },
        () => {
          setTimeout(() => this.scroll(), 100);
        }
      );
    })
  };

  handleDel = () => {
    const { onClose } = this.props;
    this.setState(
      {
        dialogList: [],
      },
      () => {
        onClose && onClose();
      }
    );
  };

  getTrainStatus = (cb) => {
    const { dispatch, onTrain } = this.props;
    const { robotId } = this.state;
    dispatch({
      type: 'chatbot/getTrainStatus',
      payload: {
        robotId: robotId,
      },
      callback: function (response) {
        console.log('getTrainStatus respone is ' + JSON.stringify(response));
        if (response.result === -1) {
          message.error(response.alerMes);
        } else {
          onTrain && onTrain(true);
          cb && cb();
        }
      },
    });
  };

  train = () => {
    //先获取训练的状态
    this.getTrainStatus(this.trainSample);
  };

  trainSample = () => {
    const { dispatch, onTrain } = this.props;
    const { robotId } = this.state;
    dispatch({
      type: 'chatbot/train',
      payload: {
        robotId: robotId,
      },
      callback: function (response) {
        if (response.result === 1) {
          onTrain && onTrain(false);
        }
      },
    });
  };

  handleTrain = () => {
    confirm({
      title: '即将开始训练业务系统',
      content: '预计耗时几分钟，期间无法进行对话测试，要继续么',
      okText: '继续',
      cancelText: '取消',
      onOk: this.train,
    });
  };

  render() {
    const { title, visible, sampleCount } = this.props;
    const visibleClass = visible ? 'show' : 'hide';
    const wrapperCls = ClassNames(
      {
        show: visible,
        hide: !visible,
      },
      `${prefixCls}-wrapper`
    );
    return (
      <div className={wrapperCls}>
        <div className={'innerWrapper'}>
          <div className={'header'}>
            <span>{title}</span>
            <span className={'del'} onClick={this.handleDel} />
          </div>

          <div className={'body'} ref={(el) => (this.BodyWrapper = el)}>
            {this.renderDialogList()}
          </div>
          <div className={'footer'}>
            <input
              placeholder="请输入用户说法，按enter发送"
              onKeyUp={this.handleKeyUp}
            />
            <span className={'btn'} onClick={this.handleReset}>
              重置对话
            </span>
          </div>
        </div>

        <div className={'masker'}></div>
      </div>
    );
  }
}

export default connect()(ValidateDialog);

/**
 *      <Tip visible={sampleCount > 0} count={sampleCount} onTrain={this.handleTrain}/>
 */

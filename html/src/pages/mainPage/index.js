import React from 'react';
import { Table, Button, Modal, Input, Divider, message } from 'antd';
import Actions from '../../actions';
import { connect } from 'react-redux';
import { chatbotColumns, getChatBotColumns } from '../../common/columns';
import Btns from '../../common/btn';
import AsLink from '../../components/AsLink';
const { confirm } = Modal;
import {trainState} from '../../common/constant';
import './index.less';
import { train } from '../../service';
// import { isSelected } from '../../utils';


function getRenderObj(onAction, onLink, dispatch, history) {
  return {
    action: function (text, record) {
      return (
        <div className="action-row" onClick={(e) => onAction(e, record)}>
          <span className="del">{Btns.DELETE_BTN.name}</span>
          <Divider type="vertical" />
          <span>{Btns.EDIT_BTN.name}</span>
          <Divider type="vertical" />
          <span>{Btns.ENABLE_BTN.name}</span>
        </div>
      );
    },
    name: function (text, record) {
      return (
        <AsLink
          name={record.name}
          showName={record.name}
          history={history}
          dispatch={dispatch}
          path={`/robotDetail/${record.id}`}
          style={{ color: '#40a9ff', cursor: 'pointer' }}
        ></AsLink>
      );
      //<Link to={`/robotDetail/${record.id}`}>{record.name}</Link>;
    },
    trainState: function (text, record) {
      let statusText = '';
      const stateNum = record.trainState;
      switch (stateNum) {
        case trainState.UN_TRAIN.number:
          statusText = trainState.UN_TRAIN.name;
          break;
        case trainState.TRAIN_SUCCESS.number:
          statusText = trainState.TRAIN_SUCCESS.name;
          break;
        case trainState.TRAIN_ERROR.number:
          statusText = trainState.TRAIN_ERROR.name;
          break;
        case trainState.TRAINING.number:
          statusText = trainState.TRAINING.name;
          break;
      }
      return <span>{statusText}</span>;
    },
  };
}

class MainPage extends React.Component {
  // constructor加载数据
  constructor(props) {
    super(props);
    this.handleNew = this.handleNew.bind(this);
    this.handleModalCancel = this.handleModalCancel.bind(this);
    this.handleModalOk = this.handleModalOk.bind(this);
    this.handleActionBtns = this.handleActionBtns.bind(this);
    this.handleLink = this.handleLink.bind(this);
  }
  state = {
    title: '新建机器人',
    visible: false,
    name: '',
    des: '',
  };

  componentWillMount() {
    const { dispatch, history } = this.props;
    const columns = getChatBotColumns(
      getRenderObj(this.handleActionBtns, this.handleLink, dispatch, history)
    );
  }
  
  handleLink(item) {
    const { history } = this.props;
    history.push(`/intentionList/${item.id}`);
  }

  // 生命周期函数，完成加载之后的操作
  componentDidMount() {
    this.fetchChatBotList();
  }


  fetchChatBotList() {
    const { dispatch } = this.props;
    dispatch({
      type: Actions.FETCH_CHATBOT_LIST,
      payload: {
        data: {
          userId: 5,
          pagination: {
            current: 1,
            pageSize: 10,
          },
        },
      },
    });
  }


  handleActionBtns(e, record) {
    const target = e.target;
    const text = target.innerText;
    const _this = this;
    const { name, number,des, id } = record;
    _this.robotId = id;
    switch (text) {
      case Btns.EDIT_BTN.name:
        //编辑
        this.setState({
          name,
          number,
          des,
          visible: true,
        });
        break;
      case Btns.DELETE_BTN.name:
        //删除
        confirm({
          title: '确定要删除该机器人？',
          onOk: function () {
            _this.handleDel(id);
          },
          onCancel: function () {},
          okText: '确定',
          cancelText: '取消',
        });
        break;
      case Btns.ENABLE_BTN.name:
        // 启用
        break;
    }
  }

  // 删除提示
  delInfo() {
    confirm({
      title: '确定要删除该机器人？',
      onOk: function () {
        _this.handleDel(id);
      },
      onCancel: function () {},
      okText: '确定',
      cancelText: '取消',
    });
  }

  // 删除
  handleDel(id) {
    let params = { id };
    const _this = this;
    const { dispatch } = this.props;
    dispatch({
      type: Actions.DELETE_CHATBOT,
      payload: params,
      callback: function () {
        console.log('callback delete');
        _this.fetchChatBotList();
      },
    });
  }

  // 填写提示
  verify() {
    const { name, number ,des } = this.state;
    const emptyRegExp = /^\s*$/;
    if (emptyRegExp.test(name)) {
      message.error('机器人名称不能为空!');
      return false;
    }
    if (emptyRegExp.test(number)) {
      message.error('机器人编码不能为空!');
      return false;
    }
    // if (isSelected("test_id_1")) {
    //   message.error("租户不能为空!");
    //   return false;
    // }
    // if (emptyRegExp.test(des)) {
    //   return false;
    // }
    return true;
  }

  // 清空上次输入
  clear() {
    this.setState({
      name: '',
      number: '',
      des: '',
    });
    var Select = document.getElementById("test_id_1");
    Select.selectedIndex = 0;
  }


  handleModalOk() {
    const _this = this;
    const result = this.verify();
    if (!result) return;
    this.handleSaveRobot(function () {
      _this.setState((preState) => {
        return {
          visible: false,
        };
      });
      _this.clear();
    });
  }
  handleSaveRobot(cb) {
    const { dispatch } = this.props;
    const _this = this;
    let { name, des } = this.state;
    let params = {
      name,
      des,
      id: this.robotId,
    };
    dispatch({
      type: Actions.NEW_CHATBOT,
      payload: params,
      callback: function (result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(mgs);
        } else {
          message.success('保存成功!');
          cb && cb();
          _this.fetchChatBotList();
          _this.robotId = null;
        }
      },
    });
  }
  handleModalCancel = () => {
    this.setState({
      visible: false,
    },() => {
      this.clear();
    });
  }
  handleNew() {
    this.setState({
      visible: true,
    });
  }
  handleInputChange(e, key) {
    const val = e.target.value;
    this.setState({
      [key]: val,
    });
  }
  render() {
    const { chatbotList, isLoading } = this.props;
    const { title, visible, name, number, des, user } = this.state;
    return (
      <div className="mainPage">
        <div className="mainPage-new">
          <Button onClick={this.handleNew} type="primary">
            新增
          </Button>
          <Button onClick={this.delInfo} type="primary">
            删除
          </Button>
        </div>
        <Table
          dataSource={chatbotList}
          loading={isLoading}
          columns={chatbotColumns}
          rowKey="id"
          pagination={false}
        />
        <Modal
          title={title}
          onOk={this.handleModalOk}
          onCancel={this.handleModalCancel}
          visible={visible}
          bordered={true}
          okText="确定"
          cancelText="取消"
        >
          <div className="row">
            <label>
              <span className="redHot">*</span>名称：
            </label>
            <Input
              style={{ width: 400 }}
              value={name}
              onChange={(e) => this.handleInputChange(e, 'name')}
              placeholder = "请输入机器人名称"
            />
          </div>

          <div className="row">
            <label>描述：</label>
            <Input
              style={{ width: 400 }}
              value={des}
              onChange={(e) => this.handleInputChange(e, 'des')}
              placeholder = "请输入机器人描述"
            />
          </div>

          <div className="row">
            <label>
              <span className="redHot">*</span>编码：
            </label>
            <Input
              style={{ width: 400 }}
              value={number}
              onChange={(e) => this.handleInputChange(e, 'number')}
              placeholder = "请输入机器人编码"
            />
          </div>

          <div className="row">
            <label>
              <span className="redHot">*</span>租户：
            </label>
            <select id="test_id_1">
                <option value disabled>选择租户</option>
                <option>苍穹HR</option>
                <option>test</option>
                <option>苍穹</option>
            </select>
          </div>
        </Modal>
      </div>
    );
  }
}

export default connect((state) => {
  return {
    chatbotList: state.chatbot.list,
    page: state.chatbot.page,
    isLoading: state.chatbot.isLoading,
  };
})(MainPage);

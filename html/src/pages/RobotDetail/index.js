import React, { Component } from 'react';
import { connect } from 'react-redux';
import PageInfo from '../../components/pageInfo';
import {  Card, Button, Modal, message } from 'antd';
import { withRouter } from 'react-router-dom';
import Actions from '../../actions';
import StandardTable from '../../components/StandardTable';
import Btns from '../../common/btn';
import ValidateDialog from '../../components/ValidateDialog';
import {getTrainStateText} from '../../common/constant';
import AsLink from '../../components/AsLink';
import './index.less';
import {
  getIntentionColumns,
  robotIntentionColumns,
} from '../../common/columns';
const { confirm } = Modal;
function getRenderObj(onAction,dispatch,history) {
  return {
    action: function (text, record) {
      return (
        <div className="action-row" onClick={(e) => onAction(e, record)}>
          <span className="del">{Btns.DELETE_BTN.name}</span>
        </div>
      );
    },
    name:function(text,record){
      console.log("record is ",record);
      return <AsLink           
      name={record.name}
      showName={record.name}
      history={history}
      dispatch={dispatch}
      path={`/intentionDetail/${record.id}`}
      style={{ color: '#40a9ff', cursor: 'pointer' }}/>
    }
  };
}
function getBtnList(btnClick) {
  return [
    {
      id: 1,
      btnName: '训练',
      number: 'train',
      onClick: btnClick,
    },
    {
      id: 2,
      number: 'dialog',
      btnName: '对话测试',
      onClick: btnClick,
    },
  ];
}

class RobotDetail extends Component {
  constructor(props) {
    super(props);
    this.handleModalCancel = this.handleModalCancel.bind(this);
    this.handleModalOK = this.handleModalOK.bind(this);
    this.handleAddIntention = this.handleAddIntention.bind(this);
    this.handleIntentionAction = this.handleIntentionAction.bind(this);
    this.handleBtnClick = this.handleBtnClick.bind(this);
    this.standardTableRef = React.createRef();
    //this.fetchChatSession = this.fetchChatSession.bind(this);
  }
  state = {
    modalVisible: false,
    dialogTestModalVisible: false,
    robotDetail:null,
    //chatSession: '',
  };
  componentWillMount() {
    this.btnList = getBtnList(this.handleBtnClick);
    const {dispatch,history}=this.props;
    this.intentColumns = getIntentionColumns(
      getRenderObj(this.handleIntentionAction,dispatch,history)
    );
  }
  componentDidMount() {
    this.fetchRobotDetail();
    this.fetchIntentionRobotList();
    this.fetchAllIntentionList();
  }
  componentWillReceiveProps(nextProps){
    const nextRobotId=nextProps.match.params.id;
    const curRobotId=this.props.match.params.id;
    if(nextRobotId!=curRobotId){
      this.fetchIntentionRobotList(nextRobotId);
    }
  }
  handleBtnClick(number) {
    const _this = this;
    switch (number) {
      case 'train':
        this.handleTrain();
        break;
      case 'dialog':
        this.setState(
          {
            dialogTestModalVisible: true,
          },
          () => {
            //_this.fetchChatSession();
          }
        );
        break;
    }
  }
  fetchRobotDetail(){
    const _this=this;
    const {dispatch,match:{params:{id}}}=this.props;
    dispatch({
      type:Actions.FETCH_ROBOT_BY_ID,
      payload:{
         id,
      },
      callback:function(result){
         const {code,msg,data}=result;
         if(code!=0){
           message.error(msg);
         }else{
           _this.setState({
            robotDetail:data ?? {},
           })
         }
      }
    })
  }
  handleTrain() {
    const robotId = this.props.match.params.id;
    const { dispatch } = this.props;
    const _this = this;
    dispatch({
      type: Actions.TRAIN,
      payload: {
        id: robotId,
      },
      callback(result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          message.success('训练成功！');
          _this.fetchIntentionRobotList();
        }
      },
    });
  }
  handleIntentionAction(e, record) {
    const target = e.target;
    const text = target.innerText;
    const _this = this;
    switch (text) {
      case Btns.DELETE_BTN.name:
        confirm({
          title: '确定移除该意图吗？',
          onOk: function () {
            _this.deleteIntentionFromRobot(record.id);
          },
          onCancel: function () {},
          okText: '确定',
          cancelText: '取消',
        });
        break;
    }
  }
  deleteIntentionFromRobot(id) {
    const _this = this;
    const { dispatch } = this.props;
    const robotId = this.props.match.params.id;
    dispatch({
      type: Actions.DEL_INTENTION_FROM_ROBOT,
      payload: {
        robotId,
        intentIdList: [id],
      },
      callback: function (result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(mgs);
        } else {
          message.success('删除成功!');
          _this.fetchIntentionRobotList();
        }
      },
    });
  }
  fetchAllIntentionList() {
    const { dispatch } = this.props;
    dispatch({
      type: Actions.FETCH_INTENTION_LIST,
      payload: {
        current: 1,
        pageSize: 10,
      },
      callback: function (result) {
        console.log('fetchAllIntentionList result is ', result);
      },
    });
  }
  fetchIntentionRobotList(robotId=this.props.match.params.id) {
    const { dispatch } = this.props;
    dispatch({
      type: Actions.FETCH_INTENTION_BY_ROBOT,
      payload: {
        robotId,
        current: 1,
        pageSize: 10,
      },
      callback: function (result) {
        const {code,msg}=result;
        if(code!=0){
          message.error(msg);
        }
      },
    });
  }
  handleAddIntention() {
    this.setState({
      modalVisible: true,
    });
  }
  //获取增量的rowKeys
  getAddSelectedRowKeys(selectedRowKeys) {
    const { dispatchedIds } = this.props;
    const dispatchedSet = new Set(dispatchedIds);
    const addSet = new Set();
  }
  handleModalOK() {
    if (this.standardTableRef) {
      const selectedRowKeys = this.standardTableRef.current.getSelectedRowKeys();
      this.getAddSelectedRowKeys(selectedRowKeys);
      if (selectedRowKeys.length > 0) {
        this.handleAddIntentToRobot(selectedRowKeys);
      }
    }
    this.setState({
      modalVisible: false,
    });
  }
  handleAddIntentToRobot(intentIdList) {
    const _this = this;
    const { dispatch } = this.props;
    const robotId = this.props.match.params.id;
    dispatch({
      type: Actions.ADD_INTENTION_TO_ROBOT,
      payload: {
        robotId,
        intentIdList,
      },
      callback: function (result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          _this.fetchIntentionRobotList();
        }
      },
    });
  }
  handleModalCancel() {
    this.setState({
      modalVisible: false,
    });
  }
  renderExtra(){
    const {robotDetail}=this.state;
    return <div className={'extra-wrapper'}>
       <div>
         <label>机器人名称:</label>
         <span>{robotDetail?.name ??''}</span>
       </div>
       <div>
         <label>训练状态:</label>
         <span>{getTrainStateText(robotDetail?.trainState)}</span>
       </div>
    </div>
  }
  render() {
    const { intentionRobotList, intentionList, dispatchedIds } = this.props;
    const { modalVisible, dialogTestModalVisible } = this.state;
    const robotId = this.props.match.params.id;
    const newIntentionList = intentionList.filter(
      (item) => !dispatchedIds.includes(item.id)
    );
    return (
      <div>
        <PageInfo btnList={this.btnList} extra={this.renderExtra()}/>
        <div style={{ marginTop: 10, backgroundColor: '#fff' }}>
          <Card
            title="意图管理"
            extra={
              <div>
                <Button
                  onClick={this.handleAddIntention}
                  style={{ color: 'rgba(85, 130, 243, 1)' }}
                  type="text"
                >
                  添加意图
                </Button>
              </div>
            }
          >
            <StandardTable
              columns={this.intentColumns}
              dataSource={intentionRobotList}
            ></StandardTable>
          </Card>
        </div>
        <Modal
          style={{maxHeight:400}}
          visible={modalVisible}
          title="分配意图"
          onOk={this.handleModalOK}
          onCancel={this.handleModalCancel}
          okText="确定"
          cancelText="取消"
        >
          <StandardTable
            columns={robotIntentionColumns}
            dataSource={newIntentionList}
            ref={this.standardTableRef}
            selectedRowKeys={dispatchedIds}
            needSelect={true}
          ></StandardTable>
        </Modal>

        <ValidateDialog
          title={'测试小R对话'}
          visible={dialogTestModalVisible}
          onClose={() => this.setState({ dialogTestModalVisible: false })}
          onMarkClick={(e) => console.log(e)}
          robotId={robotId}
        />
      </div>
    );
  }
}

export default withRouter(
  connect((state) => {
    return {
      intentionRobotList: state.robotDetail.intentionList,
      intentionList: state.intention.list,
      dispatchedIds: state.robotDetail.dispatchedIds,
    };
  })(RobotDetail)
);

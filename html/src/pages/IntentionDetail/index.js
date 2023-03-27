import React from 'react';
import { connect } from 'react-redux';
import {wordslotType as wordslotTypeList} from '../../common/selectData';
import {
  Card,
  Button,
  Modal,
  Input,
  Radio,
  Divider,
  message,
  Select,
} from 'antd';
import { getWordslotColumns, getSampleColumns } from '../../common/columns';
import Actions from '../../actions';
import { isEmpty } from '../../utils';
import StandardTable from '../../components/StandardTable';
const prefixCls = 'vi-intentionDetail';
import './index.less';
import { withRouter } from 'react-router-dom';
import NLPSentence from '../../components/NLPSentence';
const { confirm } = Modal;
import Btns from '../../common/btn';
let firstId, lastId;
function getRenderObj(onAction) {
  return {
    action: function (text, record) {
      return (
        <div className="action-row" onClick={(e) => onAction(e, record)}>
          <span
            style={{
              color: record.isLast ? '#999' : '',
              cursor: record.isLast ? 'not-allowed' : 'pointer',
            }}
          >
            下移
          </span>
          <Divider type="vertical" />
          <span
            style={{
              color: record.isFirst ? '#999' : '',
              cursor: record.isFirst ? 'not-allowed' : 'pointer',
            }}
          >
            上移
          </span>
          <Divider type="vertical" />
          <span>{Btns.EDIT_BTN.name}</span>
          <Divider type="vertical" />
          <span style={{ color: 'red' }}>{Btns.DELETE_BTN.name}</span>
        </div>
      );
    },
    must:function(text,record){
      const {must}=record;
      return <span>{must ? '是' :'否'}</span>
    },
    type:function(text,record){
      const {type}=record;
      const data=wordslotTypeList.filter(item=>item.number==type)[0];
    return <span>{data.name}</span>
    }
  };
}

function getSampleRenderObj(onAction, wordslotList) {
  return {
    name: function (text, record) {
      const {
        id,
        intentId,
        intentTrain,
        content,
        sampleItemList,
        creator,
      } = record;
      const otherProps = {
        sampleId: id,
        intentId,
        intentTrain,
        content,
        sampleItemList,
        creator,
      };
      return <NLPSentence {...otherProps} />;
    },
    intentTrain: function (text, record) {
      const { intentTrain } = record;
      return <span>{intentTrain == 0 ? '未训练' : '已训练'}</span>;
    },
    action: function (text, record) {
      return (
        <div className="action-row" onClick={(e) => onAction(e, record)}>
          <span className="del">{Btns.DELETE_BTN.name}</span>
          <Divider type="vertical" />
          <span>{Btns.EDIT_BTN.name}</span>
        </div>
      );
    },
  };
}
class IntentionDetail extends React.Component {
  constructor(props) {
    super(props);
    this.handleSampleAction = this.handleSampleAction.bind(this);
    this.handleSelectChange=this.handleSelectChange.bind(this);
  }
  state = {
    modalVisible: false,
    modalTitle: '新增词槽',
    name: '',
    clarification: '',
    must: false,
    des: '',
    number: '',
    sampleVisible: false,
    sampleTitle: false,
    sampleText: '',
    wordslotType:'TEXT',
    baseDataTypeList:[],
    baseDataType:'',
  };
  componentWillMount() {
    this.wordslotColumns = getWordslotColumns(
      getRenderObj(this.handleBtnAction)
    );
    this.sampleColumns = getSampleColumns(
      getSampleRenderObj(this.handleSampleAction)
    );
  }

  componentDidMount() {
    this.fetchWordslotList();
    this.fetchSampleList();
    this.fetchBaseDataType();
  }
  componentWillReceiveProps(nextProps) {
    const intentionID = nextProps.match.params.id;
    const preIntentionID = this.props.match.params.id;
    if (intentionID != preIntentionID) {
      this.fetchWordslotList(intentionID);
      this.fetchSampleList(intentionID);
    }
  }
  handleSampleAction(e, record) {
    const target = e.target;
    const text = target.innerText;
    const _this = this;
    switch (text) {
      case Btns.DELETE_BTN.name:
        confirm({
          title: '确定删除该样本吗？',
          onOk: () => {
            _this.handleSampleDel(record.id);
          },
          onCancel: () => {},
          okText: '确定',
          cancelText: '取消',
        });
        break;
      case Btns.EDIT_BTN.name:
        const { content,id } = record;
        this._editSampleId=id;
        this.setState({
          sampleTitle: '编辑样本',
          sampleVisible: true,
          sampleText: content,
        });
        break;
    }
  }
  handleBtnAction = (e, record) => {
    const target = e.target;
    const text = target.innerText;
    const _this = this;
    const { name, clarification, des, number, must, id,type,baseDataNumber } = record;
    console.log("record isdflsdkf is ",record);
    switch (text) {
      case '删除':
        confirm({
          title: '确定要删除该词槽吗？',
          onOk() {
            _this.handleDel(record);
          },
          onCancel() {},
          okText: '确定',
          cancelText: '取消',
        });
        break;
      case '编辑':
        this.editId = id;
        this.setState({
          name,
          number,
          des,
          clarification,
          must,
          modalVisible: true,
          modalTitle: '编辑词槽',
          wordslotType:type,
          baseDataType:baseDataNumber,
        });
        break;
      case '上移':
      case '下移':
        console.log('record is ');
        if (
          (record.isFirst && text == '上移') ||
          (record.isLast && text == '下移')
        )
          return;
        this.handleMoveWordslot(record, text == '上移' ? -1 : 1);
        break;
    }
  };
  handleMoveWordslot(record, flag) {
    const _this = this;
    const { dispatch } = this.props;
    const intentId = this.props.match.params.id;
    const { id } = record;
    let params = {
      intentId,
      slotId: id,
      upOrDown: flag,
      page: {
        number: 1,
        size: 10,
      },
    };
    dispatch({
      type: Actions.MOVE_WORDSLOT_PRIOR,
      payload: params,
      callback: function (result) {
        _this.fetchWordslotList();
      },
    });
  }
  handleDel = (record) => {
    const _this = this;
    const intentId = this.props.match.params.id;
    const { dispatch } = this.props;
    const { id } = record;
    dispatch({
      type: Actions.DELETE_WORDSLOT,
      payload: {
        slotIdList: [id],
        intentId,
      },
      callback: function (result) {
        const { code } = result;
        if (code == 0) {
          message.success('删除成功！');
          _this.fetchWordslotList();
        }
      },
    });
  };
  handleNew = () => {
    this.clarify();
    this.setState({
      modalTitle: '新增词槽',
      modalVisible: true,
    });
  };
  handleNewSample = () => {
    this.setState({
      sampleTitle: '新增样本',
      sampleVisible: true,
    });
  };
  handleSampleSure = () => {
    const { sampleText } = this.props;
    this.handleSampleSave();
    this.setState({
      sampleVisible: false,
    });
  };
  handleSampleCancel = () => {
    this.setState({
      sampleVisible: false,
      sampleText: '',
    });
  };
  handleSampleDel = (id) => {
    const { dispatch } = this.props;
    const _this = this;
    dispatch({
      type: Actions.DEL_SAMPLE,
      payload: {
        ids: [id],
      },

      callback: function (result) {
        console.log('result is ', result);
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          message.success('删除样本成功！');
          _this.fetchSampleList();
        }
      },
    });
  };
  handleSampleSave = () => {
    const _this = this;
    const { dispatch } = this.props;
    let { sampleText } = this.state;
    const intentId = this.props.match.params.id;
    console.log("sampleText is ",sampleText);
    sampleText=sampleText.replace(/\s*/g,'');
    console.log("after sampleText is ",sampleText);
    let params = {
      id:this._editSampleId,
      intentId,
      content: sampleText,
    };
    dispatch({
      type: Actions.NEW_SAMPLE,
      payload: params,
      callback: function (result) {
        const {code,msg}=result;
        if(code!=0){
          message.error(msg);
        }else{
          message.success(msg);
          _this._editSampleId='';
          _this.fetchSampleList();
        }
        _this.clarify();
      },
    });
  };
  clarify() {
    this.setState({
      name: '',
      clarification: '',
      des: '',
      must: false,
      number: '',
      wordslotType:'',
      baseDataType:'',
    });
  }
  fetchWordslotList(id = this.props.match.params.id) {
    const _this = this;

    const { dispatch } = this.props;
    dispatch({
      type: Actions.FETCH_WORDSLOT_LIST,
      payload: {
        intentionId: id,
        current: 1,
        pageSize: 30,
      },
      callback: function (result) {
        const {
          code,
          data: { list },
        } = result;
        if (list && list.length > 0) {
          firstId = list[0].id;
          lastId = list[list.length - 1].id;
        }
      },
    });
  }
  fetchSampleList(intentId = this.props.match.params.id, pagination) {
    const { dispatch } = this.props;
    let params = {
      intentId,
      current: 1,
      pageSize: 10,
    };
    if (pagination) {
      params.current = pagination.current;
      params.pageSize = pagination.pageSize;
    }
    dispatch({
      type: Actions.FETCH_SAMPLE_LIST,
      payload: params,
      callback: function (result) {},
    });
  }
  verify() {
    const { name, clarification, number } = this.state;
    if (isEmpty(name)) {
      message.error('请输入词槽名称！');
      return false;
    }
    if (isEmpty(number)) {
      message.error('请输入词槽编码！');
      return false;
    }
    if (isEmpty(clarification)) {
      message.error('请输入词槽澄清语');
      return false;
    }
    return true;
  }
  handleModalSure = () => {
    const result = this.verify();

    if (!result) return;
    this.handleNewWordslot();
    this.setState({
      modalVisible: false,
    });
  };
  handleNewWordslot() {
    const _this = this;
    const { id } = this.props.match.params;
    const { name, clarification, must, des, number,wordslotType,baseDataType } = this.state;
    const { dispatch } = this.props;
    let params = {
      intentId: id,
      slot: {
        id: this.editId ? this.editId : '',
        name,
        clarification,
        des,
        must: must ? 1 : 0,
        number,
        type: wordslotType, // 0 : 候选集 1:基础类型
        baseDataNumber:baseDataType
      },
    };
    dispatch({
      type: Actions.NEW_WORDSLOT,
      payload: params,
      callback: function (result) {
        const { code } = result;
        _this.editId = null;
        if (code == 0) {
          message.success('保存成功！');
          _this.fetchWordslotList();
        }
      },
    });
  }
  handleInputChange = (e, key) => {
    const target = e.target;
    let val = target.value;
    this.setState({
      [key]: val,
    });
  };
  handleModalCancel = () => {
    this.setState({
      modalVisible: false,
    });
  };
  handleRadioChange = (e) => {
    this.setState({
      must: e.target.value,
    });
  };
  handleSampleTableChange(pagination) {
    const intentId = this.props.match.params.id;
    this.fetchSampleList(intentId, pagination);
  }
  //词槽类型
  handleSelectChange(value,key){
    this.setState({
      [key]:value,
    },()=>{
      if(value=='BASE_DATA' && key=='wordslotType'){
        this.fetchBaseDataType();
      }
    })
  }
  fetchBaseDataType(){
    const _this=this;
    const {dispatch}=this.props;
    dispatch({
      type:Actions.FETCH_BASE_TYPE_LIST,
      payload:{
        pageSize:100,
        current:1,
      },
      callback(result){
         const {code,msg,data}=result;
         if(code!=0){
           message.error(msg);
         }
         const list=data?.list ?? [];
         _this.setState({
           baseDataTypeList:list,
         })
      }
    })
  }
  render() {
    const {
      modalVisible,
      modalTitle,
      must,
      name,
      clarification,
      des,
      number,
      sampleVisible,
      sampleTitle,
      sampleText,
      wordslotType,
      baseDataTypeList,
      baseDataType,
    } = this.state;
    const { wordslotList, sampleList } = this.props;
    return (
      <div className={`${prefixCls}-wrapper`}>
        <Card
          // style={{ height: '44%', overflow: 'auto' }}
          title="词槽管理"
          extra={
            <div>
              <Button
                onClick={this.handleNew}
                style={{ color: 'rgba(85, 130, 243, 1)' }}
                type="text"
              >
                新增
              </Button>
            </div>
          }
        >
          <StandardTable
            columns={this.wordslotColumns}
            dataSource={wordslotList}
            pagination={false}
          ></StandardTable>
        </Card>
        <Card
          style={{  marginTop:20, }}
          title="样本管理"
          extra={
            <div>
              <Button
                onClick={this.handleNewSample}
                style={{ color: 'rgba(85, 130, 243, 1)' }}
                type="text"
              >
                新增
              </Button>
            </div>
          }
        >
          <StandardTable
            columns={this.sampleColumns}
            dataSource={sampleList}
            onTableChange={this.handleSampleTableChange}
          ></StandardTable>
        </Card>
        <Modal
          title={modalTitle}
          visible={modalVisible}
          cancelText="取消"
          okText="确定"
          onCancel={this.handleModalCancel}
          onOk={this.handleModalSure}
        >
          <div className="vi-row">
            <label>
              <span className="redHot">*</span>词槽名称：
            </label>
            <Input
              value={name}
              onChange={(e) => this.handleInputChange(e, 'name')}
              style={{ width: '200px' }}
              placeholder="请输入"
            />
          </div>
          <div className="vi-row">
            <label>
              <span className="redHot">*</span>词槽编码：
            </label>
            <Input
              value={number}
              onChange={(e) => this.handleInputChange(e, 'number')}
              style={{ width: '200px' }}
              placeholder="请输入"
            />
          </div>
          <div className="vi-row">
            <label>澄清术语：</label>
            <Input
              value={clarification}
              onChange={(e) => this.handleInputChange(e, 'clarification')}
              style={{ width: '200px' }}
              placeholder="请输入"
            />
          </div>
          <div className="vi-row">
            <label>描述:</label>
            <Input
              value={des}
              onChange={(e) => this.handleInputChange(e, 'des')}
              style={{ width: '200px' }}
              placeholder="请输入"
            />
          </div>
          <div className="vi-row">
            <label>词槽类型:</label>
            <Select style={{width:200}} onChange={(value)=>this.handleSelectChange(value,'wordslotType')} value={wordslotType}>
               {
                 wordslotTypeList.map(item=><Select.Option key={item.number}>{item.name}</Select.Option>)
               }
            </Select>
          </div>
          <div className="vi-row">
          <label>基础资料类型:</label>
            <Select style={{width:200}} disabled={wordslotType=='BASE_DATA' ? false:true} 
            onChange={(value)=>this.handleSelectChange(value,'baseDataType')} value={baseDataType}>
               {
                 baseDataTypeList.map(item=><Select.Option key={item.number}>{item.name}</Select.Option>)
               }
            </Select>
          </div>
          <div className="vi-row">
            <label>是否必须：</label>
            <Radio.Group value={must} onChange={this.handleRadioChange}>
              <Radio value={false}>否</Radio>
              <Radio value={true}>是</Radio>
            </Radio.Group>
          </div>
        </Modal>
        <Modal
          title={sampleTitle}
          visible={sampleVisible}
          okText="确定"
          cancelText="取消"
          onOk={this.handleSampleSure}
          onCancel={this.handleSampleCancel}
        >
          <div className="sample-row">
            <div className="vi-row">
              <label>样本:</label>
              <Input
                placeholder="请录入样本按Enter键保存"
                value={sampleText}
                onChange={(e) => this.handleInputChange(e, 'sampleText')}
                style={{ width: '200px' }}
                placeholder="请输入"
              />
            </div>
            <div
              style={{
                marginLeft: 50,
                marginTop: 30,
                fontSize: 12,
                color: 'red',
                display: sampleTitle == '编辑样本' ? 'block' : 'none',
              }}
            >
              注：编辑样本会重置样本标注数据
            </div>
          </div>
        </Modal>
      </div>
    );
  }
}

export default withRouter(
  connect((state) => {
    return {
      wordslotList: state.wordslot.list,
      wordslotPagination: state.wordslot.pagination,
      sampleList: state.sample.list,
      samplePagination: state.sample.pagination,
    };
  })(IntentionDetail)
);

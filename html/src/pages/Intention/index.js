import React from 'react';
import { withRouter, Link } from 'react-router-dom';
import { connect } from 'react-redux';
import Actions from '../../actions';
import { Table, Modal, Divider, Button, Input, message } from 'antd';
import { getIntentionColumns, getSampleColumns } from '../../common/columns';
import Btns from '../../common/btn';
import StandardTable from '../../components/StandardTable';
import './index.less';
import { isEmpty } from '../../utils';
import AsLink from '../../components/AsLink';
let columns;
const { confirm } = Modal;
const prefixCls = 'vi-intention';
function getRenderObj(onAction, history, dispatch) {
  return {
    action: function (text, record) {
      return (
        <div className="action-row" onClick={(e) => onAction(e, record)}>
          <span className="del">{Btns.DELETE_BTN.name}</span>
          <Divider type="vertical" />
          <span>{Btns.EDIT_BTN.name}</span>
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
          path={`/intentionDetail/${record.id}`}
          style={{ color: '#40a9ff', cursor: 'pointer' }}
        ></AsLink>
      );
    },
  };
}
class Intention extends React.Component {
  constructor(props) {
    super(props);
    this.handleNew = this.handleNew.bind(this);
  }
  componentDidMount() {
    const { history, dispatch } = this.props;

    columns = getIntentionColumns(
      getRenderObj(this.handleActionClick, history, dispatch)
    );
    this.fetchIntentionList();
  }
  state = {
    name: '',
    des: '',
    number: '',
    modalVisible: false,
    modalTitle: '新增意图',
  };
  handleDelIntention(id) {
    console.log('handleDel id is ', id);
    const _this = this;
    const { dispatch } = this.props;
    dispatch({
      type: Actions.DEL_INTENTION,
      payload: {
        ids: [id],
      },
      callback(result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          message.success('删除意图成功！');
          _this.fetchIntentionList();
        }
      },
    });
  }
  handleActionClick = (e, record) => {
    const target = e.target;
    const text = target.innerText;
    const { name, number, des } = record;
    this.editId = record.id;
    switch (text) {
      case Btns.DELETE_BTN.name:
        confirm({
          title: '确定删除该意图？',
          onOk: () => {
            this.handleDelIntention(record.id);
          },
          onCancel: () => {},
          okText: '确定',
          cancelText: '取消',
        });
        break;
      case Btns.EDIT_BTN.name:
        this.setState({
          name,
          number,
          des,
          modalVisible: true,
          modalTitle: '编辑意图',
        });
        break;
    }
  };
  fetchIntentionList(pagination) {
    const { dispatch } = this.props;
    let params = {
      current: 1,
      pageSize: 10,
    };
    if (pagination) {
      params = {
        current: pagination.current,
        pageSize: pagination.pageSize,
      };
    }
    dispatch({
      type: Actions.FETCH_INTENTION_LIST,
      payload: params,
      callback: function (result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
        }
      },
    });
  }
  clearIntention() {
    this.setState({
      name: '',
      des: '',
      number: '',
    });
  }
  handleNew() {
    this.setState({
      modalTitle: '新增意图',
      modalVisible: true,
    });
  }
  verify() {
    const { name, number, des } = this.state;
    if (isEmpty(name)) {
      message.error('意图名称不能为空！');
      return false;
    }
    if (isEmpty(number)) {
      message.error('意图编码不能为空！');
      return false;
    }
    return true;
  }
  handleSaveIntention(callBack) {
    const _this = this;
    const { dispatch } = this.props;
    const { name, des, number } = this.state;
    let params = {
      intent: {
        name,
        number,
        des,
        id: this.editId ? this.editId : null,
      },
    };
    dispatch({
      type: Actions.NEW_INTENTION,
      payload: params,
      callback: function (result) {
        const { code, msg } = result;
        if (code != 0) {
          message.error(msg);
        } else {
          message.success('保存成功！');
          callBack && callBack();
          _this.fetchIntentionList();
          _this.editId = null;
        }
      },
    });
  }
  handleModalOK = () => {
    const _this = this;
    if (!this.verify()) {
      return;
    }
    this.handleSaveIntention(function () {
      _this.setState({
        modalVisible: false,
      });
      _this.clearIntention();
    });
  };
  handleModalCancel = () => {
    this.setState(
      {
        modalVisible: false,
      },
      () => {
        this.clearIntention();
      }
    );
  };
  handleInputChange = (e, key) => {
    const target = e.target;
    const val = target.value;
    this.setState({
      [key]: val,
    });
  };
  handleTableChange = (pagination) => {
    console.log('intention pagination is ', pagination);
    const { current, pageSize, total } = pagination;
    this.fetchIntentionList(pagination);
  };
  render() {
    const { list, pagination } = this.props;
    const { name, des, number, modalVisible, modalTitle } = this.state;
    return (
      <div className={`${prefixCls}-wrapper`}>
        <div className={`${prefixCls}-btn-row`}>
          <Button type="primary" onClick={this.handleNew}>
            新增
          </Button>
        </div>
        <StandardTable
          rowKey="id"
          columns={columns}
          dataSource={list}
          pagination={pagination}
          onTableChange={this.handleTableChange}
        ></StandardTable>
        <Modal
          visible={modalVisible}
          title={modalTitle}
          onOk={this.handleModalOK}
          onCancel={this.handleModalCancel}
          okText="确定"
          cancelText="取消"
          className={`${prefixCls}-modal`}
        >
          <div className="row">
            <label>
              <span className="redHot">*</span>意图名称：
            </label>
            <Input
              placeholder="请输入"
              value={name}
              onChange={(e) => this.handleInputChange(e, 'name')}
            />
          </div>
          <div className="row">
            <label>
              <span className="redHot">*</span>意图编码：
            </label>
            <Input
              placeholder="请输入"
              value={number}
              onChange={(e) => this.handleInputChange(e, 'number')}
            />
          </div>
          <div className="row">
            <label>意图描述：</label>
            <Input
              placeholder="请输入"
              value={des}
              onChange={(e) => this.handleInputChange(e, 'des')}
            />
          </div>
        </Modal>
      </div>
    );
  }
}

export default withRouter(
  connect((state) => {
    return {
      list: state.intention.list,
      pagination: state.intention.pagination,
      isLoading: state.intention.isLoading,
    };
  })(Intention)
);

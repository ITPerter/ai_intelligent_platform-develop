import React from 'react';
import './index.less';
import {connect} from 'react-redux';
const prefixCls="vi-basedata";
import {Divider,message,Button,Modal,Input} from 'antd';
import StandardTable from '../../components/StandardTable';
import Actions from '../../actions';
import {getBaseDataTypeColumns} from '../../common/columns';
import AsLink from '../../components/AsLink';
import Btns from '../../common/btn';
import {isEmpty} from '../../utils';
const confirm=Modal.confirm;
function renderObj(onAction,dispatch,history){
    return {
        action:function (text, record) {
            return (
              <div className="action-row" onClick={(e) => onAction(e, record)}>
                <span className="del">{Btns.DELETE_BTN.name}</span>
                <Divider type="vertical"/>
                <span className="edit">{Btns.EDIT_BTN.name}</span>
              </div>
            );
        },
        name:function(text,record){
            return <AsLink           
            name={record.name}
            showName={record.name}
            history={history}
            dispatch={dispatch}
            path={`/baseDataDetail/${record.number}`}
            style={{ color: '#40a9ff', cursor: 'pointer' }}/>
        }
    }
}
class BaseData extends React.Component{
    constructor(props){
        super(props);
        this.fetchBaseDataType=this.fetchBaseDataType.bind(this);
        this.handleNewBaseDataType=this.handleNewBaseDataType.bind(this);
        this.handleModalCancel=this.handleModalCancel.bind(this);
        this.handleModalOK=this.handleModalOK.bind(this);
        this.handleTableBtnClick=this.handleTableBtnClick.bind(this);
    }
    state={
        name:'',
        number:'',
        des:'',
        modalVisible:false,
        modalTitle:'',
    }
    componentDidMount(){
        const {dispatch,history}=this.props;
        this.columns=getBaseDataTypeColumns(renderObj(this.handleTableBtnClick,dispatch,history));
        this.fetchBaseDataType();
    }
    handleTableBtnClick(e,record){
       const _this=this;
       const target=e.target;
       const text=target.innerText;
       switch(text){
           case Btns.DELETE_BTN.name:
                confirm({
                    title:'确定删除该基础资料吗?',
                    cancelText:'取消',
                    okText:'确定',
                    onOk:function(){
                        _this.handleDel(record);
                    },
                    onCancel:function(){

                    }
                })
           break;
           case Btns.EDIT_BTN.name:
                const {name,number,des,id}=record;
                this._editId=id;
                this.setState({
                    modalVisible:true,
                    modalTitle:'编辑基础资料',
                    name,
                    number,
                    des
                })
           break;
           
       }
    }
    handleDel(record){
        const _this=this;
        const {dispatch}=this.props;
        dispatch({
            type:Actions.DELETE_BASE_TYPE,
            payload:{
                baseDataNumberList:[record.number],
            },
            callback:function(result){
               const {code,msg}=result;
               if(code!=0){
                   message.error(msg);
               }else{
                   message.success(msg);
                   _this.fetchBaseDataType();
               }
            }
        })
    }
    fetchBaseDataType(requestPagination){
        const {dispatch,pagination}=this.props;
        let params={
            pageSize:requestPagination ? requestPagination.pageSize : pagination.pageSize,
            current:requestPagination ? requestPagination.current : pagination.current,
        }
        dispatch({
            type:Actions.FETCH_BASE_TYPE_LIST,
            payload:params,
            callback:function(result){
                const {code,msg}=result;
                if(code!=0){
                    message.error(msg);
                }
            }
        })
    }
    handleNewBaseDataType(){
        this.setState({
            modalTitle:'新增基础资料',
            modalVisible:true,
        })
    }
    handleInputChange(e,key){
        const value=e.target.value;
        this.setState({
            [key]:value,
        })
    }
    verify(){
        const {name,number,des}=this.state;
        if(isEmpty(name)){
            message.error("名称不能为空!");
            return false;
        }
        if(isEmpty(number)){
            message.error("编码不能为空!");
            return false;
        }
        return true;
    }
    handleModalOK(){
       if(!this.verify())return;
       this.handleSaveBaseData();
    }
    //保存数据
    handleSaveBaseData(){
       const _this=this;
       const {dispatch}=this.props;
       const {name,number,des}=this.state;
       dispatch({
           type:Actions.NEW_BASE_TYPE,
           payload:{
              name,
              number,
              des,
              id:this._editId,
           },
           callback:function(result){
              const {code,msg}=result;
              if(code!=0){
                  message.error(msg);
              }else{
                 _this.setState({
                     modalVisible:false,
                 })
                 _this.clear();
                 _this.fetchBaseDataType();
              }
              _this._editId=null;
           }
       })
    }
    clear(){
        this.setState({
            name:'',
            des:'',
            number:''
        })
    }
    handleModalCancel(){
        const _this=this;
        this.setState({
            modalVisible:false,
        },()=>{
          _this.clear();
        })
    }
    handleTableChange=(pagination)=>{
       this.fetchBaseDataType(pagination);
    }
    render(){
        const {baseDataTypeList,pagination}=this.props;
        const {modalVisible,modalTitle,name,number,des}=this.state;
        return <div className={`${prefixCls}-wrapper`}>
            <div>
                <Button type="primary" onClick={this.handleNewBaseDataType}>新增</Button>
            </div>
            <div style={{marginTop:20}}>
                <StandardTable onTableChange={this.handleTableChange} columns={this.columns} dataSource={baseDataTypeList} pagination={pagination}></StandardTable>
            </div>
            <Modal title={modalTitle} visible={modalVisible} cancelText="取消" okText="确定" 
            onOk={this.handleModalOK} onCancel={this.handleModalCancel}>
                <div className={`${prefixCls}-row`}>
                     <label>名称:</label>
                     <Input value={name} placeholder="请输入" onChange={(e)=>this.handleInputChange(e,'name')}/>
                </div>
                <div className={`${prefixCls}-row`}>
                     <label>编码:</label>
                     <Input value={number} placeholder="请输入" onChange={(e)=>this.handleInputChange(e,'number')}/>
                </div>
                <div className={`${prefixCls}-row`}>
                     <label>描述:</label>
                     <Input value={des} placeholder="请输入" onChange={(e)=>this.handleInputChange(e,'des')}/>
                </div>
            </Modal>
        </div>
    }
}

export default connect(state=>{
    return {
        baseDataTypeList:state.baseData.baseDataTypeList,
        pagination:state.baseData.pagination,
    }
})(BaseData);
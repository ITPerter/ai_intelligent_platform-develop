import React from 'react';
import './index.less';
import {connect} from 'react-redux';
import StandardTable from '../../components/StandardTable';
import Actions from '../../actions';
import {message,Button,Modal,Divider,Input} from 'antd';
import Btns from '../../common/btn';
import {isEmpty} from '../../utils';
const prefixCls="vi-bd-detail";
import {getBaseDataValueColumns} from '../../common/columns';
const confirm=Modal.confirm;
function renderObj(onAction){
    return {
        action:function (text, record) {
            return (
              <div className="action-row" onClick={(e) => onAction(e, record)}>
                <span className="del">{Btns.DELETE_BTN.name}</span>
                {/* <Divider type="vertical"/>
                <span className="edit">{Btns.EDIT_BTN.name}</span> */}
              </div>
            );
        },
    }
}
class BaseDataDetail extends React.Component{
    constructor(props){
        super(props);
        this.handleAction=this.handleAction.bind(this);
        this.handleNew=this.handleNew.bind(this);
    }
    state={
        modalTitle:'',
        modalVisible:false,
        value:'',
        number:'',
    }
    componentDidMount(){
       this.fetchBaseDataValue();
       this.columns=getBaseDataValueColumns(renderObj(this.handleAction));
    }
    componentWillReceiveProps(nextProps){
       const nextNumber=nextProps.match.params.number;
       const curNumber=this.props.match.params.number;
       if(nextNumber!=curNumber){
           this.fetchBaseDataValue(nextNumber);
       }
    }
    handleAction(e,record){
        const _this=this;
       const target=e.target;
       const text=target.innerText;
       switch(text){
           case Btns.DELETE_BTN.name:
              confirm({
                  title:'确定删除该项数据？',
                  cancelText:'取消',
                  okText:'确定',
                  onOk:function(){
                      _this.handleDelete(record);
                  }
              })
           break;
           case Btns.EDIT_BTN.name:
               this._editId=record.id;
               const {value,number}=record;
               this.setState({
                   modalVisible:true,
                   modalTitle:'编辑',
                   number,
                   value,
               })
           break;
       }
    }
    handleDelete(record){
        const {dispatch}=this.props;
        const _this=this;
        dispatch({
            type:Actions.DEL_BASE_DATA_VALUE,
            payload:{
                numberList:[record.number]
            },
            callback:function(result){
                const {code,msg}=result;
                if(code!=0){
                    message.error(msg);
                }else{
                  _this.fetchBaseDataValue();
                }
            }
        })
    }
    handleSaveBaseDataValue(cb){
        const _this=this;
        const {dispatch}=this.props;
        const {value,number}=this.state;
        const baseDataNumber=this.props.match.params.number;
        dispatch({
            type:Actions.SAVE_BASE_DATA_VALUE,
            payload:{
               value,
               number,
               baseDataNumber,
               id:this._editId,
            },
            callback:function(result){
               const {code,msg}=result;
               if(code!=0){
                   message.error(msg);
               }else{
                   _this._editId=null;
                   cb&&cb();
                   dispatch({
                       type:Actions.ASYNC_BASE_VALUE,
                       payload:{
                           value,
                           number,
                       }
                   })
                //    _this.fetchBaseDataValue();
               }
            }
        })
    }
    fetchBaseDataValue(number=this.props.match.params.number,extraPagination){
        const {dispatch,pagination}=this.props;
        let params={
            baseDataNumber:number,
            pageSize:extraPagination ? extraPagination.pageSize : pagination.pageSize,
            current:extraPagination ? extraPagination.current : pagination.current,
        }
        dispatch({
           type:Actions.FETCH_BASE_VALUE_LIST,
           payload:params,
           callback:function(result){
              const {code,msg}=result;
              if(code!=0){
                 message.error(msg);
              }
           }
        })
    }
    clear(){
        this.setState({
            value:'',
            number:'',
        })
    }
    handleNew(){
        this.setState({
            modalTitle:'新建',
            modalVisible:true,
        })
    }
    handleModalOK=()=>{
        const _this=this;
       if(!this.verify())return;
       this.handleSaveBaseDataValue(function(){
        _this.setState({
           
            modalVisible:false,
        })
        _this.clear();
       })
    }
    handleModalCancel=()=>{
       this.setState({
           modalVisible:false,
       },()=>{
           this.clear();
       })
    }
    verify(){
        const {value,number}=this.state;
        if(isEmpty(value)){
            message.error("值不能为空!");
            return false;
        }
        if(isEmpty(number)){
            message.error("编码不能为空!");
            return false;
        }
        return true;
    }
    handleInputChange(e,key){
        const value=e.target.value;
        this.setState({
            [key]:value,
        })
    }
    handleTableChange=(pagination)=>{
       console.log("pagination is ",pagination);
       this.fetchBaseDataValue(this.props.match.params.number,pagination)
    }
    render(){
        const {isLoading,pagination,list}=this.props;
        const {modalTitle,modalVisible,value,number}=this.state;
        return <div className={`${prefixCls}-wrapper`}>
             <div className={`${prefixCls}-row`}>
                <Button type="primary" onClick={this.handleNew}>新建</Button>
             </div>
             <StandardTable rowKey="number" pagination={pagination} isLoading={isLoading} onTableChange={this.handleTableChange} 
             columns={this.columns} dataSource={list}></StandardTable>
             <Modal visible={modalVisible} title={modalTitle} cancelText="取消" okText="确定" 
             onOk={this.handleModalOK} onCancel={this.handleModalCancel}>
                 <div className="vi-row">
                     <label><span className={'redHot'}>*</span>值:</label>
                     <Input value={value} placeholder="请输入" onChange={(e)=>this.handleInputChange(e,'value')}/>
                 </div>
                 <div className="vi-row">
                     <label><span className={'redHot'}>*</span>编码:</label>
                     <Input value={number} placeholder="请输入" onChange={(e)=>this.handleInputChange(e,'number')}/>
                 </div>
             </Modal>
        </div>
    }
}

export default connect(state=>{
    return {
        list:state.baseDataDetail.list,
        pagination:state.baseDataDetail.pagination,
        isLoading:state.baseDataDetail.isLoading,
    }
})(BaseDataDetail);
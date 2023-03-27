import React from 'react';
import './index.less';
import {Input,Button,message} from 'antd';
import {connect} from 'react-redux';
import {withRouter} from 'react-router-dom';
import {isEmpty} from '../../utils';
import Actions from '../../actions';
const prefixCls="vi-user";

class UserLayout extends React.Component{
    constructor(props){
        super(props);
    }
    state={
        userName:'',
        password:''
    }

    verify(){
        const {userName,password}=this.state;
        if(isEmpty(userName)){
            message.error("请输入用户名!")
            return false;
        }
        if(isEmpty(password)){
            message.error("请输入密码!");
            return false;
        }
        return true;
    }
    handleSubmit(){
         if(!this.verify())return;
         const {userName,password}=this.props;
         const {dispatch,history}=this.props;
         dispatch({
             type:Actions.LOGIN,
             payload:{
                 userName,
                 password
             },
             callback:function(result){
                 const {code,msg}=result;
                 if(code!=0){
                     message.error(msg);
                 }else{
                     console.log("login haoxin_qiu");
                 }
             }
         })
    }
    render(){
        const {userName,password}=this.state;
        return <div className={`${prefixCls}-wrapper`}>
             <div className="row">
                 <label>用户名：</label>
                 <Input placeholder="请输入" value={userName}/>
             </div>
             <div className="row">
                 <label>密码：</label>
                 <Input placeholder="请输入" value={password}/>
             </div>
             <Button type="primary" onClick={this.handleSubmit}>登录</Button>
        </div>
    }
}

export default UserLayout;
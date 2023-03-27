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
        this.handleSubmit=this.handleSubmit.bind(this);
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
         const {userName,password}=this.state;
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
                     history.push('/mainPage')
                 }
             }
         })
    }
    handleInputChange=(e,key)=>{
       const target=e.target;
       const value=target.value;
       this.setState({
           [key]:value,
       })
    }
    render(){
        const {userName,password}=this.state;
        return <div className={`${prefixCls}-wrapper`}>
             <div className="row">
                 <label><span className="redhot">*</span>用户名：</label>
                 <Input onChange={(e)=>this.handleInputChange(e,'userName')} placeholder="请输入" value={userName}/>
             </div>
             <div className="row">
                 <label><span className="redhot">*</span>密码：</label>
                 <Input type="password" onChange={(e)=>this.handleInputChange(e,'password')} placeholder="请输入" value={password}/>
             </div>
             <Button style={{marginTop:20,width:'100%'}} type="primary" onClick={this.handleSubmit}>登录</Button>
        </div>
    }
}

export default withRouter(connect(state=>{
    return {
        
    }
})(UserLayout));
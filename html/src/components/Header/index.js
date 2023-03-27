import React from 'react';
import './index.less';
import { Dropdown, Menu } from 'antd';
const prefixCls = 'vi-header';
import xiaoK from '../../images/xiaok.png';
export default function (props) {
  const menu = (
    <Menu>
      <Menu.Item onClick={props.onLogout}>退出登录</Menu.Item>
    </Menu>
  );
  return (
    <div className={`${prefixCls}-wrapper`}>
      <div className={`${prefixCls}-left`}></div>
      <div className={`${prefixCls}-right`}>
        <div>
          <div>您好，欢迎</div>
          <div>张三</div>
        </div>
        <Dropdown overlay={menu}>
          <img src={xiaoK} />
        </Dropdown>
      </div>
    </div>
  );
}

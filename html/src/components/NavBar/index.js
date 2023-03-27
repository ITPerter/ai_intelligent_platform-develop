/**
 * 全局的导航栏
 * 1、展示当前路由的页面
 * 2、展示其他路由名称，但隐藏页面
 * 3、支持切换其他菜单
 * 4、支持删除菜单
 */

import React from 'react';
import './index.less';
import PropTypes from 'prop-types';
const prefixCls = 'vi-navbar';
import { connect } from 'react-redux';
import { HomeFilled } from '@ant-design/icons';
import ClassNames from 'class-names';
import Tab from '../Tab';
class NavBar extends React.Component {
  constructor(props) {
    super(props);
  }
  static getDerivedStateFromProps(nextProps, prevState) {
    return null;
  }
  state = {};
  handleItemClick = () => {};
  renderRouteList() {
    const { routeList, currentRouteKey } = this.props;
  }
  handleTabClick = (item) => {
    const { onMenuTabClick } = this.props;
    onMenuTabClick && onMenuTabClick(item.actualPath, item);
  };
  handleDelClick = (item) => {
    const { onMenuDel } = this.props;
    onMenuDel && onMenuDel(item.path, item);
  };
  renderTabs = () => {
    const { routeList, currentRouteKey, history } = this.props;
    const str = routeList.map((item) => (
      <Tab
        onClick={this.handleTabClick}
        onDelClick={this.handleDelClick}
        key={item.actualPath}
        style={{ marginRight: 30 }}
        data={item}
        isSelected={`${currentRouteKey}` === item.actualPath ? true : false}
      />
    ));

    const currentRouteItem = routeList.filter(
      (item) => item.actualPath == currentRouteKey
    )[0];
    history.push(currentRouteItem.actualPath);
    return (
      <div className={`${prefixCls}-list`} ref={(el) => (this.menuList = el)}>
        {str}
      </div>
    );
  };
  render() {
    return (
      <div className={`${prefixCls}-wrapper`}>
        <div style={{ paddingLeft: 10, paddingRight: 10 }}>
          <HomeFilled style={{ fontSize: 18, color: '#999' }} />
        </div>
        {this.renderTabs()}
      </div>
    );
  }
}
NavBar.defaultProps = {};
NavBar.propTypes = {};
export default connect((state) => {
  return {
    routeList: state.global.routeList,
    currentRouteKey: state.global.currentRouteKey,
  };
})(NavBar);

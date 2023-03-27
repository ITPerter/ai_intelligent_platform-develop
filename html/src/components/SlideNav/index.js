/**
 * 侧边功能导航栏
 * data:2019/06/03
 * author:haoxin_qiu
 */

import React from 'react';
import './index.less';
import { withRouter } from 'react-router-dom';
import { connect } from 'react-redux';
const prefixCls = 'kd-sn';
import Actions from '../../actions';
import AsLink from '../AsLink';
class SlideNav extends React.Component {
  constructor(props) {
    super(props);
  }
  state = {
    isExtend: false, //是否拓展
    showSecondMenu: false, //是否显示二级菜单
    hoverItem: -1,
  };
  handleMenuItemClick = (menuItem, moduleId) => {
    const { onMenuItemClick } = this.props;
    console.log(
      `moduleId is ${moduleId} and menuItem is ${JSON.stringify(menuItem)}`
    );
    this.setState(
      {
        isExtend: false,
        hoverItem: -1,
      },
      () => {
        onMenuItemClick && onMenuItemClick(moduleId, menuItem);
      }
    );
  };
  renderSecondMenu = (menuData, id) => {
    const { hoverItem } = this.state;
    const { history, dispatch } = this.props;
    const str = menuData.map((item) => {
      const showNav = item.showNav;
      //onClick={()=>this.handleMenuItemClick(item,id)}
      return showNav ? (
        <li key={item.id}>
          <AsLink {...item} history={history} dispatch={dispatch} />
        </li>
      ) : null;
    });
    return (
      <ul
        className={`${prefixCls}-secondMenu`}
        style={{ visibility: hoverItem === id ? 'visible' : 'hidden' }}
      >
        {str}
      </ul>
    );
  };
  renderNav = () => {
    const { data } = this.props;
    const { hoverItem } = this.state;
    const str = data.map((item) => {
      return (
        <li
          key={item.id}
          onMouseEnter={() => this.handleItemHover(item)}
          style={this.getItemStyle(item.id)}
        >
          <img src={item.img} />
        </li>
      );
    });
    return <ul className={`${prefixCls}-nav`}>{str}</ul>;
  };
  handleItemHover = (item) => {
    const { hoverItem } = this.state;
    if (item && hoverItem != item.id) {
      this.setState({
        hoverItem: item.id,
      });
    }
  };
  getItemStyle = (id) => {
    const { hoverItem } = this.state;
    if (hoverItem === id) {
      return {
        background: '#21242D',
        color: '#5582F3',
      };
    }
    return null;
  };
  renderExtend = () => {
    const { data } = this.props;

    const { isExtend, hoverItem } = this.state;
    const str = data.map((item) => (
      <li
        key={item.id}
        onMouseEnter={() => this.handleItemHover(item)}
        style={this.getItemStyle(item.id)}
      >
        <div>{item.name}</div>
        {this.renderSecondMenu(item.children, item.id)}
      </li>
    ));
    return (
      <ul
        className={`${prefixCls}-extend`}
        style={{ width: isExtend ? 80 : 0, overflow: isExtend ? '' : 'hidden' }}
      >
        {str}
      </ul>
    );
  };
  handleMouseEnter = () => {
    const isExtend = this.state.isExtend;
    if (!isExtend) {
      this.setState({
        isExtend: true,
      });
    }
  };
  handleMouseLeave = () => {
    this.setState({
      isExtend: false,
      hoverItem: -1,
    });
  };
  render() {
    const { className, style } = this.props;
    const { isExtend } = this.state;
    let classList = [
      `${prefixCls}-wrapper`,
      isExtend ? `${prefixCls}-extend` : '',
    ];
    const classNames = classList.join(' ');
    return (
      <div
        className={classNames}
        style={style}
        onMouseEnter={this.handleMouseEnter}
        onMouseLeave={this.handleMouseLeave}
      >
        {this.renderNav()}
        {this.renderExtend()}
      </div>
    );
  }
}

// SlideNav.defaultProps={
//     onMenuItemClick:null,
// }
// SlideNav.propTypes={
//     onMenuItemClick:PropTypes.func,
// }
export default withRouter(connect()(SlideNav));

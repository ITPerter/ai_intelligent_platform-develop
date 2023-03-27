/**
 * 页签
 */

import React from 'react';
import PropTypes from 'prop-types';
import ClassNames from 'class-names';
import './index.less';
const prefixCls = 'kd-tab';
class Tab extends React.Component {
  constructor(props) {
    super(props);
  }
  state = {
    showDecoration: false,
  };
  handleClick = (data) => {
    const { onClick } = this.props;
    onClick && onClick(data);
    // this.setState({
    // 	showDecoration:true,
    // })
  };
  handleDelClick = (data) => {
    const { onDelClick } = this.props;
    onDelClick && onDelClick(data);
  };
  renderDecoration = () => {
    //const {showDecoration}=this.state;
    const { isSelected } = this.props;
    return (
      <div
        className={`${prefixCls}-decoration ${isSelected ? 'show' : ''}`}
        style={{ width: isSelected ? '100%' : 0 }}
      ></div>
    );
  };
  render() {
    const { className, style, data, isSelected } = this.props;
    const classNames = ClassNames(
      {
        [className]: !!className,
      },
      `${prefixCls}-wrapper`
    );
    let menuName = data.name;
    if (data.showName) {
      menuName += `-${data.showName}`;
    }
    const { showDecoration } = this.state;
    if (data)
      return (
        <div className={classNames} style={style}>
          <div className={`${prefixCls}-content`}>
            <div
              className={`${prefixCls}-text`}
              onClick={() => this.handleClick(data)}
              style={{ color: isSelected ? '#5582F3' : '#5F7196' }}
            >
              {menuName}
            </div>
            <div
              className={`${prefixCls}-del`}
              onClick={() => this.handleDelClick(data)}
            ></div>
          </div>
          {this.renderDecoration()}
        </div>
      );
  }
}

Tab.defaultProps = {};
Tab.propTypes = {};
export default Tab;

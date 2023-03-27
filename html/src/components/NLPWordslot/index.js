/**
 * 标注的词
 * 
 */

import React from 'react';
import './index.less';
import ClassNames from 'class-names';
import PropTypes from 'prop-types';

const prefixCls = 'web-nlpwordslot';
class NLPWordslot extends React.Component {
    constructor(props) {
        super(props);
    }
    // componentWillUpdate(nextProps) {
    //     return this.props.status != nextProps.status;
    // }
    handleTagClick = (e, item) => {
        if (item.status === 'noSelect') return;
        const { onClick } = this.props;
        onClick && onClick(e, item);
    }
    render() {
        const { className, style, item } = this.props;
        const { status, value, id } = item;
        const classNames = ClassNames({
            [className]: !!className,
            [`${prefixCls}-${status}`]: !!status,
        }, `${prefixCls}-wrapper`)
        return <span className={classNames} style={style} onClick={(e) => this.handleTagClick(e, item)}>
            {value}
        </span>
    }
}
NLPWordslot.defaultProps = {

}
NLPWordslot.propTypes = {
    value: PropTypes.string,
    status: PropTypes.string,//词的状态  selecting:'选中'  selected:'已标注'  
}
export default NLPWordslot;
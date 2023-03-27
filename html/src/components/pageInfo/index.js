import React from 'react';
import './index.less';
import { Button } from 'antd';
const prefixCls = 'vi-pageInfo';
export default function (props) {
  const { btnList,extra } = props;
  return (
    <div className={`${prefixCls}-wrapper`}>
      <div className={`${prefixCls}-btns`}>
        {btnList.map((item) => (
          <Button
            key={item.number}
            type="primary"
            onClick={() => item.onClick(item.number)}
          >
            {item.btnName}
          </Button>
        ))}
      </div>
      <div className={`${prefixCls}-extra`}>
      {
          extra
       }
      </div>
    </div>
  );
}

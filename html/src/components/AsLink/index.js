import React from 'react';
import Actions from '../../actions';
function AsLink(props) {
  const { path, history, dispatch, style, name, showName } = props;

  function click() {
    dispatch({
      type: Actions.NEW_MENU,
      payload: {
        path,
        showName, //展示的二级名称
      },
    });
    history.push(path);
  }
  return (
    <div style={style} onClick={click}>
      {name}
    </div>
  );
}

export default AsLink;

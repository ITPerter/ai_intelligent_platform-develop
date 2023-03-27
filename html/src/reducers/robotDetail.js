const defaultState = {
  robotInfo: {},
  intentionList: [], //已经分配给当前机器人的意图列表
  dispatchedIds: [], //已分配过的意图ids
};
import Actions from '../actions';
export default function (state = defaultState, action) {
  const { type } = action;
  switch (type) {
    case Actions.ASYNC_INTENTION_BY_ROBOT:
      const {
        payload: { list, page },
      } = action;
      let tempArr = [];
      list.forEach((item) => {
        const { id } = item;
        tempArr.push(id);
      });
      return {
        ...state,
        intentionList: list,
        dispatchedIds: tempArr,
      };
      break;
    default:
      return state;
      break;
  }
}

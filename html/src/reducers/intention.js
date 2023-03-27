const defaultState = {
  list: [],
  pagination: {
    current: 1,
    pageSize: 10,
  },
  isLoading: false,
};
import Actions from '../actions';
export default function (state = defaultState, action) {
  const { type, data } = action;
  switch (type) {
    case Actions.ASYNC_INTENTION_LIST:
      const { list, page } = data.data;
      return {
        ...state,
        list,
        pagination: {
          total: page.total,
          current: page.number,
          pageSize: page.size,
        },
      };
      break;
    default:
      return state;
  }
}

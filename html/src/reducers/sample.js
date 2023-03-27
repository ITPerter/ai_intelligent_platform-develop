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
  const type = action.type;
  switch (type) {
    case Actions.ASYNC_SAMPLE_LIST:
      const {
        payload: { page, list },
      } = action;
      list.forEach((item) => {
        if (!item.hasOwnProperty('sampleItemList')) {
          item.sampleItemList = [];
        }
        // const { sampleItemList } = item;
        // //提取已经使用过的词槽
        // sampleItemList.forEach((sampleItem) => {
        //   if (sampleItem.hasOwnProperty('slotNumber')) {
        //     tempArr.push(sampleItem.slotNumber);
        //   }
        // });
      });
      return {
        ...state,
        list,
        pagination: {
          current: page.number,
          pageSize: page.size,
          total: page.total,
        },
      };

      break;
    default:
      return state;
      break;
  }
}

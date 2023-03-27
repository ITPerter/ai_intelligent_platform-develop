const defaultState = {
  data: {
    list: [],
    pagination: {},
  },
  isLoading: false,
};
import Actions from '../actions';
export default function (state = defaultState, action) {
  const type = action.type;
  switch (type) {
    case Actions.ASYNC_WORDSLOT_LIST:
      const {payload:{data}}=action;
      const list=data?.list??[];
      if(list.length > 0){
        list.forEach((item,index)=>{
            if(index==0){
              item.isFirst=true;
            }
            if(index==list.length - 1 && index!=0){
               item.isLast=true;
            }
        })
      }

      const page=data?.page??{};
      return {
        ...state,
        list,
        pagination:{
          current:page.number,
          size:page.pageSize,
          total:page.total,
        }
      }
      break;
    
    default:
      return state;
  }
}

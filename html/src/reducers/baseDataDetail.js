import Actions from '../actions';
const defaultState={
    list:[],
    pagination:{
       current:1,
       pageSize:10,
       total:0
    },
    isLoading:false,
};

export default function(state=defaultState,action){
   const type=action.type;
   switch(type){
       case Actions.ASYNC_BASE_VALUE_LIST:
          const {payload:{list,page}}=action;
          return {
              ...state,
              list,
              pagination:{
                  total:page.total,
                  current:page.number,
                  pageSize:page.size,
              }
          }
       break;
       case Actions.ASYNC_BASE_VALUE://同步本地数据，因为服务器的数据存ES要时间延迟
         const {payload}=action;
         const tempList=state.list.slice();
         tempList.push(payload);
         return {
           ...state,
           list:tempList,
         };
       break;
       default:
          return state;
       break;
   }
}
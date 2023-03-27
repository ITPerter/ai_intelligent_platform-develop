import Actions from '../actions';
const defaultState={
    baseDataTypeList:[],
    pagination:{
       total:0,
       current:1,
       pageSize:10,
    }, 

}
export default function(state=defaultState,action){
   const type=action.type;
   switch(type){
       case Actions.ASYNC_BASE_TYPE_LIST: //ASYNC_BASEDATA_TYPE_LIST
          const {payload:{list,page}}=action;
          return {
              ...state,
              baseDataTypeList:list,
              pagination:{
                  total:page.total,
                  pageSize:page.size,
                  current:page.number,
              }
          }
       break;
       default:
          return state;
       break;
   }
}
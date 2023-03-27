import Actions from '../actions';
const defaultState = {
  list: [],
  page: {},
  isLoading: false,
};
export default function (state = defaultState, action) {
  const type = action.type;
  switch (type) {
    case Actions.ASYNC_CHATBOT_LIST:
      const {msg,data} =action?.payload??{msg:'',data:{}};
      return {
        ...state,
        ...data,
        isLoading:false,
      }
      break;
    case Actions.CHATBOT_LOADING:
      return {
        ...state,
        isLoading:action.payload
      }
    break;
    default:
      return state;
  }
}

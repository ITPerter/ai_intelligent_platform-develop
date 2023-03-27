const defaultState={
    currentUser:'',
    userList:[],
};
import Actions from '../actions';
export default function(state=defaultState,action){
    const type=action.type;
    switch(type){
        // case Actions.LOGIN:
        //     const {payload}=action;
            
        // break;
        default:
           return state;
        break;
    }
}

import {put,call,takeLatest} from 'redux-saga/effects';
import Actions from '../actions';
import {addWordslot,getWordslotByIntentionID,delWordslotByIntentId,moveWordslotPrior} from '../service'
export function* watchAddWordslot(){
    yield takeLatest(Actions.NEW_WORDSLOT,addNewWordslot);
}

export function* watchFetchWordslotList(){
    yield takeLatest(Actions.FETCH_WORDSLOT_LIST,fetchWordslotList);
}

export function* watchDelWordslot(){
    yield takeLatest(Actions.DELETE_WORDSLOT,deleteWordslot);
}

export function* watchMoveWordslot(){
    yield takeLatest(Actions.MOVE_WORDSLOT_PRIOR,changeWordslotPrior);
}

function* changeWordslotPrior(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(moveWordslotPrior,payload);
    console.log("result  in changeWordslot prior is ",result)
    callback && callback(result);
}

function* addNewWordslot(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(addWordslot,payload);
    callback && callback(result)
}

function* deleteWordslot(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(delWordslotByIntentId,payload);
    callback && callback(result);
    console.log('result is ',result)
}


function* fetchWordslotList(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(getWordslotByIntentionID,payload);
    console.log("fetch wordslot result ",result)
    const {code,data}=result;
    if(code==0){
        yield put({
            type:Actions.ASYNC_WORDSLOT_LIST,
            payload:result
        })
    }
    callback && callback(result)
}


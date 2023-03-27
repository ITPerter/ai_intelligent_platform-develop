import {getIntentionList,saveIntention,delIntentionByIds} from '../service';
import {call,put,takeLatest} from 'redux-saga/effects';
import Actions from '../actions';

export function* watchFetchIntentionList(){
    yield takeLatest(Actions.FETCH_INTENTION_LIST,fetchIntentionList);
}

export function* watchSaveIntention(){
    yield takeLatest(Actions.NEW_INTENTION,newOrEditIntention)
}

export function* watchDelIntention(){
  yield takeLatest(Actions.DEL_INTENTION,deleteIntention);
}

function* deleteIntention(requestParams){
  const {payload,callback}=requestParams;
    const result=yield call(delIntentionByIds,payload);
    callback&& callback(result);
}

function* newOrEditIntention(requestParams){
   const {payload,callback}=requestParams;
   const result=yield call(saveIntention,payload);
   callback&& callback(result);
}

function* fetchIntentionList(options){
    const {payload,callback}=options;
    const result=yield call(getIntentionList,payload);
 
    console.log("fetch intention list is ",result);
    const {code,msg,data}=result;
    console.log('code is ',code);
    if(code==0){
      console.log("sdfsfsfsdfs");
      yield put({
        type:Actions.ASYNC_INTENTION_LIST,
        data:{
          code,msg,data
        }
      })
    }
    callback&& callback({code,msg,data});
 }
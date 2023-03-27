import {call,put,takeLatest} from 'redux-saga/effects';
import Actions from '../actions';
import {getSampleList,saveSample,delSample,delSampleEntity,addSampleEntity} from '../service';
export function* watchGetSampleList(){

   yield takeLatest(Actions.FETCH_SAMPLE_LIST,getSampleListByIntentId);
}

export function* watchSaveSample(){
    yield takeLatest(Actions.NEW_SAMPLE,newOrEditSample);
}
export function* watchDelSamples(){
    yield takeLatest(Actions.DEL_SAMPLE,delSamples);
}
export function* watchAddSampleEntity(){
    yield takeLatest(Actions.ADD_SAMPLE_ENTITY,saveSampleEntity)
}
export function* watchDelSampleEntity(){
    yield takeLatest(Actions.REMOVE_SAMPLE_ENTITY,deleteSampleEntity)
}
function* deleteSampleEntity(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(delSampleEntity,payload);
    callback && callback(result);
}
function* saveSampleEntity(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(addSampleEntity,payload);
    callback && callback(result);
}
function* delSamples(requestParams){
   const {payload,callback}=requestParams;
   const result=yield call(delSample,payload);
   callback && callback(result);
}
function* newOrEditSample(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(saveSample,payload);
    callback&& callback(result);
}
function* getSampleListByIntentId(requestParams){
    console.log("fetch sample list!!");
   const {payload,callback}=requestParams;
   const result = yield call(getSampleList,payload);
   console.log("get sample list is ",result);
   const {code,data}=result;
   if(code==0){
       yield put({
           type:Actions.ASYNC_SAMPLE_LIST,
           payload:data
       })
   }
   callback && callback(result);
}


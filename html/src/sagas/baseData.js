import {getBaseDataTypeList,getBaseDataValue,saveBaseDataType,delBaseDataType,
    delBaseDataValue,saveBaseDataValue} from '../service';
import {call,put,takeLatest} from 'redux-saga/effects';
import Actions from '../actions';

//获取基础资料数据类型列表
export function* watchGetBaseDataTypeList(){
    yield takeLatest(Actions.FETCH_BASE_TYPE_LIST,fetchBaseDataType);
}

export function* watchGetBaseDataValue(){
    yield takeLatest(Actions.FETCH_BASE_VALUE_LIST,fetchBaseDataValue);
}

export function* watchSaveBaseDataType(){
    yield takeLatest(Actions.NEW_BASE_TYPE,saveBaseDataTypeAPI);
}

export function* watchDelBaseDataType(){
    yield takeLatest(Actions.DELETE_BASE_TYPE,deleteBaseDataType)
}

export function* watchDelBaseDataValue(){
    yield takeLatest(Actions.DEL_BASE_DATA_VALUE,deleteBaseDataValue);
}

export function* watchSaveBaseDataValue(){
    yield takeLatest(Actions.SAVE_BASE_DATA_VALUE,saveBaseDataValueAPI);
}
function* saveBaseDataValueAPI(requestParams){
    const {callback,payload}=requestParams;
    const result=yield call(saveBaseDataValue,payload);
    callback && callback(result)
}

function* deleteBaseDataValue(requestParams){
   const {callback,payload}=requestParams;
   const result=yield call(delBaseDataValue,payload);
   callback && callback(result)
}

function* deleteBaseDataType(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(delBaseDataType,payload);
    callback && callback(result);
}
function* saveBaseDataTypeAPI(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(saveBaseDataType,payload);
    callback && callback(result);
}

function* fetchBaseDataValue(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(getBaseDataValue,payload);
    const {code,data:{list,page}}=result;
    if(code==0){
        yield put({
            type:Actions.ASYNC_BASE_VALUE_LIST,
            payload:{
               list,
               page
            }
        })
    }
    callback && callback(result);
}

function* fetchBaseDataType(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(getBaseDataTypeList,payload);
    const {code,data:{list,page}}=result;
    if(code==0){
        yield put({
            type:Actions.ASYNC_BASE_TYPE_LIST,
            payload:{
              list,
              page
            }
        });
    }
    callback && callback(result);
}
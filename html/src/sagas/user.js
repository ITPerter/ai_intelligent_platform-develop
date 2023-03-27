import {login,logout} from '../service';
import {call,put,takeLatest} from 'redux-saga/effects';
import Actions from '../actions';

export function* watchLogin(){
    yield takeLatest(Actions.LOGIN,userLogin);
}

function* userLogin(requestParams){
    const {payload,callback}=requestParams;
   const result=yield call(login,payload);
   callback && callback(result);
}

export function* watchLogout(){
    yield takeLatest(Actions.LOGOUT,userLogout);
}

function* userLogout(requestParams){
    const {payload,callback}=requestParams;
    const result=yield call(logout);
    callback && callback(result);
}
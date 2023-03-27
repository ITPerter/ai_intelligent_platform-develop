import {
  getIntentionListByRobotId,
  dispatchIntent2Robot,
  removeIntentFromRobot,
  train,
  chat,
  getChatSession,
  getRobotById,
} from '../service';
import { put, call, takeLatest } from 'redux-saga/effects';
import Actions from '../actions';
export function* watchFetchChatSession() {
  yield takeLatest(Actions.FETCH_CHAT_SESSION, fetchChatSession);
}
export function* watchFetchIntentionListByRobotId() {
  yield takeLatest(
    Actions.FETCH_INTENTION_BY_ROBOT,
    fetchIntentionListByRobotId
  );
}
export function* watchAddIntentToRobot() {
  yield takeLatest(Actions.ADD_INTENTION_TO_ROBOT, addIntentionToRobot);
}
export function* watchRemoveIntentFromRobot() {
  yield takeLatest(Actions.DEL_INTENTION_FROM_ROBOT, removeIntentionFromRobot);
}
export function* watchFetchRobotById(){
  yield takeLatest(Actions.FETCH_ROBOT_BY_ID,fetchRobotById);
}

export function* watchTrain() {
  yield takeLatest(Actions.TRAIN, trainSample);
}

export function* watchChat() {
  yield takeLatest(Actions.CHAT, testChat);
}

function* fetchRobotById(requestParams){
  const {callback,payload}=requestParams;
  const result=yield call(getRobotById,payload);
  callback && callback(result);
}
function* fetchChatSession(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(getChatSession, payload);
  callback && callback(result);
}
function* testChat(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(chat, payload);
  const { code, data } = result;
  //  if(code==0){
  //    yield put({
  //      type:Actions.ASYNC_CHAT,
  //      payload:JSON.parse(data),
  //    })
  //  }
  callback && callback(result);
}
function* trainSample(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(train, payload);
  callback && callback(result);
}
function* removeIntentionFromRobot(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(removeIntentFromRobot, payload);
  callback && callback(result);
}

function* fetchIntentionListByRobotId(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(getIntentionListByRobotId, payload);
  const { code, data } = result;
  console.log('fetchIntentionListByRobotId  result is ', result);
  if (code == 0) {
    yield put({
      type: Actions.ASYNC_INTENTION_BY_ROBOT,
      payload: data,
    });
  }
  callback && callback(result);
}
function* addIntentionToRobot(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(dispatchIntent2Robot, payload);
  callback && callback(result);
}

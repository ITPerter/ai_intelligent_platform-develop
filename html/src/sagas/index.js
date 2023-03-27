import { call, fork, takeLatest, put } from 'redux-saga/effects';

import Actions from '../actions';
import {
  getChatBotList,
  saveChatbot,
  delChatbot,
  getIntentionList,
} from '../service';
import {
  watchAddWordslot,
  watchFetchWordslotList,
  watchDelWordslot,
  watchMoveWordslot,
} from './wordslot';
import {
  watchGetSampleList,
  watchSaveSample,
  watchDelSamples,
  watchDelSampleEntity,
  watchAddSampleEntity,
} from './sample';
import {
  watchFetchIntentionList,
  watchSaveIntention,
  watchDelIntention,
} from './intention';
import {
  watchRemoveIntentFromRobot,
  watchAddIntentToRobot,
  watchFetchIntentionListByRobotId,
  watchTrain,
  watchChat,
  watchFetchChatSession,
  watchFetchRobotById
} from './robotDetail';
import {
  watchGetBaseDataTypeList,
  watchGetBaseDataValue,
  watchSaveBaseDataType,
  watchDelBaseDataType,
  watchSaveBaseDataValue,
  watchDelBaseDataValue
} from './baseData';
import { watchLogin, watchLogout } from './user';
function* getChatbotList(payload) {
  yield put({
    type: Actions.CHATBOT_LOADING,
    payload: true,
  });
  const result = yield call(getChatBotList, payload.payload);
  console.log("hei result is ",result);
  const { code, data, msg } = result;
  const callback = payload.callback;
  if (code == 0) {
    yield put({
      type: Actions.ASYNC_CHATBOT_LIST,
      payload: {
        data,
        msg,
      },
    });
  }
  callback && callback();
}
function* createChatBot(requestParams) {
  const { payload, callback } = requestParams;
  const result = yield call(saveChatbot, payload);
  callback && callback(result);
}
function* deleteChatBot(payload) {
  const result = yield call(delChatbot, payload.payload);
  console.log('result delChatBot is ', result);
  const callback = payload.callback;
  callback && callback();
}
function* watchGetChatbotList() {
  yield takeLatest(Actions.FETCH_CHATBOT_LIST, getChatbotList);
}
function* watchNewChatbot() {
  yield takeLatest(Actions.NEW_CHATBOT, createChatBot);
}

function* watchDelChatbot() {
  yield takeLatest(Actions.DELETE_CHATBOT, deleteChatBot);
}

export default function* rootSaga() {
  try {
    yield fork(watchGetChatbotList);
    yield fork(watchNewChatbot);
    yield fork(watchDelChatbot);
    yield fork(watchFetchIntentionList);
    yield fork(watchSaveIntention);
    yield fork(watchDelIntention);
    yield fork(watchAddWordslot);
    yield fork(watchFetchWordslotList);
    yield fork(watchDelWordslot);
    yield fork(watchMoveWordslot);
    yield fork(watchGetSampleList);
    yield fork(watchSaveSample);
    yield fork(watchDelSamples);
    yield fork(watchAddSampleEntity);
    yield fork(watchDelSampleEntity);

    //机器人详情
    yield fork(watchFetchIntentionListByRobotId);
    yield fork(watchAddIntentToRobot);
    yield fork(watchRemoveIntentFromRobot);
    yield fork(watchFetchRobotById);
    yield fork(watchTrain);
    yield fork(watchChat);
    yield fork(watchFetchChatSession);

    yield fork(watchLogin);
    yield fork(watchLogout);

    yield fork(watchGetBaseDataTypeList);
    yield fork(watchGetBaseDataValue);
    yield fork(watchSaveBaseDataType);
    yield fork(watchDelBaseDataType);
    yield fork(watchSaveBaseDataValue);
    yield fork(watchDelBaseDataValue);
    
  } catch (e) {}
}

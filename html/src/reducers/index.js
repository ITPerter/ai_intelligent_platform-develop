import chatbot from './chatbot';
import intention from './intention';
import wordslot from './wordslot';
import robotDetail from './robotDetail';
import sample from './sample';
import global from './global';
import user from './user';
import baseData from './baseData';
import baseDataDetail from './baseDataDetail';
import { combineReducers } from 'redux';

export default combineReducers({
  chatbot,
  intention,
  wordslot,
  robotDetail,
  sample,
  global,
  user,
  baseData,
  baseDataDetail,
});

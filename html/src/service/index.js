import('isomorphic-fetch');
import request from './request';

// let urlPath = '';

// if (process.env.NODE_ENV == 'development') {
//   // urlPath = 'http://119.29.81.29';
//   // urlPath="http://localhost:8021";
//   urlPath = 'http://112.74.114.182';
// } else {
//   urlPath = 'http://112.74.114.182';
// }
let urlPathApi="/api";
/**
 * 获取机器人列表
 * @param {*} params
 */
export function getChatBotList(params) {
  const { current, pageSize } = params?.data?.pagination;
  return request(
    urlPathApi + `/robot/getList?number=${current}&size=${pageSize}`,
    {
      method: 'GET',
    }
  );
}
/**
 * 新建机器人
 * @param {*} params
 */
export function saveChatbot(params) {
  return request(urlPathApi + '/robot/save', {
    method: 'POST',
    body: params,
  });
}
//根据机器人id获取机器人信息
export function getRobotById(params){
  const {id}=params;
  return request(urlPathApi+`/robot/getById?id=${id}`,{
     method:'GET',
  })
}

export function modifyChatbot(params) {
  return request(urlPathApi + '/modifyChatbot', {
    method: 'POST',
    body: params,
  });
}
//删除机器人
export function delChatbot(params) {
  return request(urlPathApi + `/robot/delByIds?ids=${params.id}`, {
    method: 'GET',
  });
}
//获取意图列表
export function getIntentionListByChatbotId(params) {
  const { chatbotId, pageSize, current } = params;
  return request(
    urlPathApi +
      `/intent/getListByRobotid?robotId=${chatbotId}
  &number=${current}&size=${pageSize}`,
    {
      method: 'GET',
    }
  );
}
//添加意图
export function saveIntention(params) {
  return request(urlPathApi + '/intent/save', {
    method: 'POST',
    body: params,
  });
}

//获取单个意图
export function getIntentionById(params) {
  return request(urlPathApi + `/intent/getById?id=${params.id}`, {
    method: 'GET',
  });
}
//意图列表
export function getIntentionList(params) {
  const { current, pageSize } = params;
  return request(
    urlPathApi + `/intent/getList?number=${current}&size=${pageSize}`,
    {
      method: 'GET',
    }
  );
}
//根据机器人id获取所属意图列表
export function getIntentionListByRobotId(params) {
  const { current, pageSize, robotId } = params;
  return request(
    urlPathApi +
      `/intent/getListByRobotId?robotId=${robotId}&number=${current}&size=${pageSize}`,
    {
      method: 'GET',
    }
  );
}
/**
 * 给机器人分配意图列表
 * @param {*} params
 */
export function dispatchIntent2Robot(params) {
  return request(urlPathApi + `/intent/addIntentIdList2Robot`, {
    method: 'POST',
    body: params,
  });
}

//从robot机器人删除意图
export function removeIntentFromRobot(params) {
  return request(urlPathApi + '/intent/removeIntentIdsFromRobot', {
    method: 'POST',
    body: params,
  });
}

//删除意图
export function delIntentionByIds(params) {
  const { ids } = params;
  const idParams = ids.join(',');
  return request(urlPathApi + '/intent/delByIds?ids=' + idParams);
}
//根据意图id获取词槽列表
export function getWordslotByIntentionID(params) {
  const { current, pageSize, intentionId } = params;
  return request(
    urlPathApi +
      `/slot/getListByIntentId?intentId=${intentionId}&number=${current}&size=${pageSize}`,
    {
      method: 'GET',
    }
  );
}
//新增、编辑词槽
export function addWordslot(params) {
  return request(urlPathApi + '/slot/save', {
    method: 'POST',
    body: params,
  });
}
//删除词槽
export function delWordslot(params) {
  const { ids } = params;
  const params_id = ids.join(',');
  return request(urlPathApi + `/slot/delByIds?ids=${params_id}`, {
    method: 'GET',
  });
}
//根据意图id删除词槽
export function delWordslotByIntentId(params) {
  return request(urlPathApi + '/slot/removeSlotIdsFromIntent', {
    method: 'POST',
    body: params,
  });
}
//修改词槽优先级
export function moveWordslotPrior(params) {
  return request(urlPathApi + '/slot/changeSlotSeqAndGetList', {
    method: 'POST',
    body: params,
  });
}
//获取样本列表
export function getSampleList(params) {
  const { intentId, pageSize, current } = params;
  return request(
    urlPathApi +
      `/sample/getListByIntentId?intentId=${intentId}&number=${current}&size=${pageSize}`,
    {
      method: 'GET',
    }
  );
}
//新增、编辑样本
export function saveSample(params) {
  return request(urlPathApi + `/sample/save`, {
    method: 'POST',
    body: params,
  });
}
//删除样本
export function delSample(params) {
  const { ids } = params;
  const delIds = ids.join(',');
  return request(urlPathApi + `/sample/delByIds?ids=${delIds}`, {
    method: 'GET',
  });
}
//保存样本分词
export function addSampleEntity(params) {
  return request(urlPathApi + '/sample/saveSampleItem', {
    method: 'POST',
    body: params,
  });
}
//移除样本分词
export function delSampleEntity(params) {
  return request(
    urlPathApi + `/sample/delBySampleItemId?sampleItemId=${params.id}`,
    {
      method: 'GET',
    }
  );
}
//训练接口
export function train(params) {
  return request(urlPathApi + `/robot/train?id=${params.id}`, {
    method: 'GET',
  });
}
//登录
export function login(params) {
  return request(urlPathApi + '/user/login', {
    method: 'POST',
    body: params,
  });
}

export function logout() {
  return request(urlPathApi + '/user/logout', {
    method: 'GET',
  });
}
//对话测试
export function chat(params) {
  const token = params.token;
  return request(urlPathApi + `/third/chat?token=${token}`, {
    method: 'POST',
    body: params,
  });
}
//获取对话测试session
export function getChatSession(params) {
  return request(urlPathApi + `/auth/getUserToken`, {
    method: 'POST',
    body: params,
  });
}
//获取基础资料数据类型
export function getBaseDataTypeList(params){
  const {current,pageSize}=params;
  return request(urlPathApi+`/baseData/getList?number=${current}&size=${pageSize}`,{
    method:'GET',
  })
}
//获取某种类型基础资料数据
export function getBaseDataValue(params){
  const {current,pageSize,baseDataNumber}=params;
  return request(urlPathApi+`/baseData/getValueListByDataBaseId?baseDataNumber=${baseDataNumber}&number=${current}&size=${pageSize}`,{
    method:'GET',
  })
}

//保存基础资料类型
export function saveBaseDataType(params){
   return request(urlPathApi+'/baseData/saveBaseData',{
     method:'POST',
     body:params,
   })
}
//删除基础资料类型
export function delBaseDataType(params){
  return request(urlPathApi + '/baseData/delByNumberList',{
    method:'POST',
    body:params,
  })
}
//删除基础资料值
export function delBaseDataValue(params){
  return request(urlPathApi+`/baseData/delValueByNumberList`,{
    method:'POST',
    body:params
  })
}
//保存基础资料值
export function saveBaseDataValue(params){
  return request(urlPathApi+'/baseData/saveValue2BaseData',{
    method:'POST',
    body:params,
  })
}


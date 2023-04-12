package com.q.ai.mvc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.biz.entity.ChatIntent;
import com.q.ai.biz.entity.ChatSlot;
import com.q.ai.biz.entity.ChatVo;
import com.q.ai.biz.entity.Session;
import com.q.ai.component.enuz.CHAT_STATE;
import com.q.ai.component.enuz.SLOT_STATE;
import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.component.session.RequestContext;
import com.q.ai.component.io.RsException;
import com.q.ai.component.session.SessionUtil;
import com.q.ai.component.util.NormalizationUtil;
import com.q.ai.component.util.WaterLevelClientUtil;
import com.q.ai.mvc.dao.po.*;
import com.q.ai.component.io.Page;
import com.q.ai.mvc.dao.Robot2IntentDao;
import com.q.ai.mvc.dao.RobotMapper;
import com.q.ai.mvc.esDao.BaseDataValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class RobotService {


    @Autowired
    RobotMapper robotMapper;
    @Autowired
    Robot2IntentDao robot2IntentDao;
    @Autowired
    BaseDataValueDao baseDataValueDao;
    @Autowired
    NlpService nlpService;
    @Autowired
    private WordSlotService wordSlotService;
    @Autowired
    BaseDataValueService baseDataValueService;
    @Autowired
    RequestContext requestContext;
    @Resource(name = "mysql")
    SessionUtil sessionUtil;

    static int I=1;


    public Robot getById(int id) {
        return robotMapper.getById(id);
    }

    /**
     * 获取机器人数量并将其设置为分页总数
     * @param page
     * @return
     */
    public Page getPage(Page page) {
        page.setTotal(robotMapper.getCount());
        return page;
    }

    /**
     * 获取机器人分页数据
     * @param page
     * @return
     */
    public List<Robot> getList(Page page) {
        return robotMapper.getList(page.getOffset(), page.getLimit());
    }

    public ChatVo chat(int robotId, String chatMsg) {
        if (chatMsg.length() > 200) {
            chatMsg = chatMsg.substring(0, 200);
        }
        Robot robot = getById(robotId);
        if (null == robot) {
            throw new RsException("机器人不存在。");
        }
        Session session = requestContext.getSession();

        String currentIntentNumber = session.getCurrentIntentNumber();
        ChatSlot chatSlot2Fill = session.getChatSlot2Fill();
        String chatSlotNumber = chatSlot2Fill == null ? null : chatSlot2Fill.getNumber();
        JSONObject dataJson = nlpService.getIntent(robotId, chatMsg, currentIntentNumber, chatSlotNumber);

        JSONArray intentJsonArray = dataJson.getJSONArray("intent");
        JSONObject slot2Value = dataJson.getJSONObject("entities");

        currentIntentNumber = intentJsonArray == null ? null : intentJsonArray.size() == 0 ? null : intentJsonArray.getJSONObject(0).getString("name");
        Map<String, Object> slot2ValueMap = slot2Value == null ? new HashMap<>() : slot2Value.getInnerMap();

        session = sessionUtil.buildIntentAndFillSlot(session, currentIntentNumber, slot2ValueMap, chatMsg);
//        sessionUtil.save(session);
        //提取填充完成之后，校验，问询
        //为什么要重新获取，因为在nlp填槽过程中意图可能已经切换

        ChatIntent currentChatIntent = session.getCurrentChatIntent();
        ChatVo chatVo = new ChatVo();
        chatVo.setIntent(currentChatIntent);


        if (null == currentChatIntent) {
            chatVo.setState(CHAT_STATE.NO_INTENT);
            chatVo.setMsg("当前没有获得意图");
            return chatVo;
        }

        List<ChatSlot> chatSlotList = currentChatIntent.getChatSlotList();
        if (null != chatSlotList && chatSlotList.size() != 0) {
            for (ChatSlot chatSlot : chatSlotList) {
                SLOT_STATE slotState = SLOT_STATE.UN_FILL;
                List<String> processedList = new ArrayList<>();

                if (StringUtils.isEmpty(chatSlot.getOriginString())) {
                    slotState = SLOT_STATE.UN_FILL;
                } else if (!chatSlot.getSlotState().equals(SLOT_STATE.VERIFY_SUCCESS)) {//需要校验的值不是空的，并且之前没有校验成功
                    int slotId = chatSlot.getId();
                    SLOT_TYPE slotType = chatSlot.getType();
                    String origin = chatSlot.getOriginString();

                    Slot slot = wordSlotService.getById(slotId);
                    if (null == slot) {
                        throw new RsException("词槽不存在");
                    }


                    switch (slotType) {
                        case BASE_DATA:
                            String baseDataNumber = slot.getBaseDataNumber();
                            List<BaseDataValue> baseDataValueList = baseDataValueDao.searchByBaseDataNumber(baseDataNumber, origin, new Page(1, 5));

                            if (baseDataValueList == null || baseDataValueList.isEmpty()) {//没有校验通过
                                slotState = SLOT_STATE.VERIFY_FAIL;
                                processedList = new ArrayList<>();
                            } else if (baseDataValueList.get(0).getValue().equals(origin) || baseDataValueList.size() == 1) {//完全匹配，成功了
                                slotState = SLOT_STATE.VERIFY_SUCCESS;
                                BaseDataValue baseDataValue = baseDataValueList.get(0);
                                String processedString = StringUtils.isEmpty(baseDataValue.getNumber()) ? baseDataValue.getValue() : baseDataValue.getNumber();
                                processedList.add(processedString);
                            } else {
                                slotState = SLOT_STATE.VERIFY_CHOOSE;
                                for (BaseDataValue baseDataValue : baseDataValueList) {
                                    processedList.add(baseDataValue.getValue());
                                }
                            }
                            break;

                        case TIME:
                            LocalDateTime time = NormalizationUtil.getNormalizationTime(origin);
                            if (null == time) {
                                slotState = SLOT_STATE.VERIFY_FAIL;
                                processedList = new ArrayList<>();
                            } else {
                                slotState = SLOT_STATE.VERIFY_SUCCESS;
                                processedList.add(time.toString());
                            }
                            break;
                        case TEXT:
                            slotState = SLOT_STATE.VERIFY_SUCCESS;
                            processedList.add(origin);
                            break;
                        default://更多类型词槽，待续
                            slotState = SLOT_STATE.VERIFY_SUCCESS;
                            processedList.add(origin);

                    }
                }

                if (!chatSlot.getSlotState().equals(SLOT_STATE.VERIFY_SUCCESS)) {
                    chatSlot.setVerifyValueList(processedList);
                    chatSlot.setSlotState(slotState);
                }
            }
        }


        chatSlot2Fill = session.getChatSlot2Fill();

        if (null != chatSlot2Fill) {
            chatVo.setState(CHAT_STATE.CLARIFY);
            chatVo.setCurrentSlot(chatSlot2Fill);
            chatVo.setMsg("当前词槽需要澄清");
            return chatVo;
        }
        chatVo.setState(CHAT_STATE.COMPLETE);
        chatVo.setMsg("当前意图已经完成");
        //判断是不是查水位的意图
//        if(chatVo.getIntent().getNumber().equals("water_level")){
//            //构造查询条件map对象
//            Map<String,String> map = new HashMap<>();
//            //遍历词槽
//            chatVo.getIntent().getChatSlotList().forEach(chatSlot -> {
//                //第一个词槽存站点名
//                if(map.size() == 0){
//                    map.put("stid",chatSlot.getOriginString());
//                }else {
//                    //将时间格式转换
//                    String s = "yyyy年MM月dd日HH点";
//                    SimpleDateFormat f = new SimpleDateFormat(s);
//                    try {
//                        Date date = f.parse(chatSlot.getOriginString());
//                        SimpleDateFormat sf = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
//                        map.put("date",sf.format(date));
//                        chatVo.setMsg(WaterLevelClientUtil.selectWater_level(map).getString("data"));
//                    } catch (ParseException e) {
//                        chatVo.setMsg("语音输入日期有误，请按2022年6月11日22点格式输入");
//                    }
//
//                }
//
//            });
//
//        }
        return chatVo;


    }

    /**
     * 聊天接口
     * @param robotId
     * @param chatMsg
     * @param userId
     * @param token
     * @param data
     * @return
     */
    public ChatVo chat2(int robotId, String chatMsg,String userId,String token,JSONObject data) {
        if (chatMsg.length() > 200) {
            chatMsg = chatMsg.substring(0, 200);
        }
        Robot robot = getById(robotId);
        if (null == robot) {
            throw new RsException("机器人不存在。");
        }
        Session session = requestContext.getSession();
        if(data!=null) {
            JSONObject intentJson =data;
            ChatIntent chatIntent = intentJson.getObject("intent",ChatIntent.class);
            session.setCurrentChatIntent(chatIntent);
        }
        //重新设置session
        session.setId(token);
        session.setUserId(Integer.parseInt(userId));
        String currentIntentNumber = session.getCurrentIntentNumber();
        ChatSlot chatSlot2Fill = session.getChatSlot2Fill();
        String chatSlotNumber = chatSlot2Fill == null ? null : chatSlot2Fill.getNumber();
        JSONObject dataJson = nlpService.getIntent(robotId, chatMsg, currentIntentNumber, chatSlotNumber);

        String chat_response = dataJson.getString("faq");
        JSONArray intentJsonArray = dataJson.getJSONArray("intent");
        System.out.println(dataJson);
        JSONObject slot2Value = dataJson.getJSONObject("entities");

        currentIntentNumber = intentJsonArray == null ? null : intentJsonArray.size() == 0 ? null : intentJsonArray.getJSONObject(0).getString("name");
        Map<String, Object> slot2ValueMap = slot2Value == null ? new HashMap<>() : slot2Value.getInnerMap();

        session = sessionUtil.buildIntentAndFillSlot(session, currentIntentNumber, slot2ValueMap, chatMsg);
        sessionUtil.save(session);
        //提取填充完成之后，校验，问询
        //为什么要重新获取，因为在nlp填槽过程中意图可能已经切换

        ChatIntent currentChatIntent = session.getCurrentChatIntent();
        ChatVo chatVo = new ChatVo();
        chatVo.setIntent(currentChatIntent);

        if (null == currentChatIntent) {
            chatVo.setState(CHAT_STATE.NO_INTENT);
//            chatVo.setMsg("当前没有获得意图");
            chatVo.setMsg(chat_response);
            return chatVo;
        }

        List<ChatSlot> chatSlotList = currentChatIntent.getChatSlotList();
        if (null != chatSlotList && chatSlotList.size() != 0) {
            for (ChatSlot chatSlot : chatSlotList) {
                SLOT_STATE slotState = SLOT_STATE.UN_FILL;
                List<String> processedList = new ArrayList<>();

                if (StringUtils.isEmpty(chatSlot.getOriginString())) {
                    slotState = SLOT_STATE.UN_FILL;
                } else if (!chatSlot.getSlotState().equals(SLOT_STATE.VERIFY_SUCCESS)) {//需要校验的值不是空的，并且之前没有校验成功
                    int slotId = chatSlot.getId();
                    SLOT_TYPE slotType = chatSlot.getType();
                    String origin = chatSlot.getOriginString();

                    Slot slot = wordSlotService.getById(slotId);
                    if (null == slot) {
                        throw new RsException("词槽不存在");
                    }


                    switch (slotType) {
                        case BASE_DATA:
                            String baseDataNumber = slot.getBaseDataNumber();
                            List<BaseDataValue> baseDataValueList = baseDataValueDao.searchByBaseDataNumber(baseDataNumber, origin, new Page(1, 5));

                            if (baseDataValueList == null || baseDataValueList.isEmpty()) {//没有校验通过
                                slotState = SLOT_STATE.VERIFY_FAIL;
                                processedList = new ArrayList<>();
                            } else if (baseDataValueList.get(0).getValue().equals(origin) || baseDataValueList.size() == 1) {//完全匹配，成功了
                                slotState = SLOT_STATE.VERIFY_SUCCESS;
                                BaseDataValue baseDataValue = baseDataValueList.get(0);
                                String processedString = StringUtils.isEmpty(baseDataValue.getNumber()) ? baseDataValue.getValue() : baseDataValue.getNumber();
                                processedList.add(processedString);
                            } else {
                                slotState = SLOT_STATE.VERIFY_CHOOSE;
                                for (BaseDataValue baseDataValue : baseDataValueList) {
                                    processedList.add(baseDataValue.getValue());
                                }
                            }
                            break;

                        case TIME:
                            LocalDateTime time = NormalizationUtil.getNormalizationTime(origin);
                            if (null == time) {
                                slotState = SLOT_STATE.VERIFY_FAIL;
                                processedList = new ArrayList<>();
                            } else {
                                slotState = SLOT_STATE.VERIFY_SUCCESS;
                                processedList.add(time.toString());
                            }
                            break;
                        case TEXT:
                            slotState = SLOT_STATE.VERIFY_SUCCESS;
                            processedList.add(origin);
                            break;
                        default://更多类型词槽，待续
                            slotState = SLOT_STATE.VERIFY_SUCCESS;
                            processedList.add(origin);

                    }
                }

                if (!chatSlot.getSlotState().equals(SLOT_STATE.VERIFY_SUCCESS)) {
                    chatSlot.setVerifyValueList(processedList);
                    chatSlot.setSlotState(slotState);
                }
            }
        }


        chatSlot2Fill = session.getChatSlot2Fill();

        if (null != chatSlot2Fill) {
            chatVo.setState(CHAT_STATE.CLARIFY);
            chatVo.setCurrentSlot(chatSlot2Fill);
            chatVo.setMsg("当前词槽需要澄清");
            return chatVo;
        }
        chatVo.setState(CHAT_STATE.COMPLETE);
        chatVo.setMsg("当前意图已经完成");
        //判断是不是查水位的意图
        if(chatVo.getIntent().getNumber().equals("water_level")){
            //构造查询条件map对象
            Map<String,String> map = new HashMap<>();
            //遍历词槽
            chatVo.getIntent().getChatSlotList().forEach(chatSlot -> {
                //第一个词槽存站点名
                if(map.size() == 0){
                    map.put("stid",chatSlot.getOriginString());
                }else {
//                    System.out.println( chatSlot.getVerifyValueList().toString().replace("/\\[|]/g",""));
                    map.put("date", chatSlot.getVerifyValueList().get(0).replace("T"," ")+":00");
                    chatVo.setMsg(WaterLevelClientUtil.selectWater_level(map).getString("data"));

                }
            });

        }
        return chatVo;
    }

    /**
     * 存储一个机器人
     * @param robot
     * @return
     */
    public Robot save(Robot robot) {
        if (robot.getId() != 0) {//编辑
            robot.setUpdateTime(LocalDateTime.now());
            robotMapper.update(robot);
        } else {//新增
            int userId = requestContext.getSession().getUserId();
            robot.setCreator(userId);
            robot.setUpdater(userId);
            robot.setTrainState(0);
            robot.setCreateTime(LocalDateTime.now());
            robot.setUpdateTime(LocalDateTime.now());
            robotMapper.insert(robot);
        }

        return robot;
    }


    /**
     * 通过id列表删除机器人
     * @param robotIdList
     * @return
     */
    @Transient
    public int delByIdList(List<Integer> robotIdList) {
        robot2IntentDao.delByRobotIdList(robotIdList);
        return robotMapper.delByIdList(robotIdList);
    }

    /**
     * 未写完
     * @param robotId
     * @param intentIdList
     * @return
     */
    public int addIntents(String robotId, List<Integer> intentIdList) {
        //todo here1
        // 1. slotdao  slotvaluedao
        // 2. 所有的 del 批量接口改造
        List<Robot2Intent> robot2Intents = new ArrayList<>();
        return robot2IntentDao.inserts(robot2Intents);
    }

    /**
     * 未写完
     * @param robotIdList
     * @return
     */
    public int delIntentsByRobotIdList(List<Integer> robotIdList) {
        return robot2IntentDao.delByRobotIdList(robotIdList);
    }

}

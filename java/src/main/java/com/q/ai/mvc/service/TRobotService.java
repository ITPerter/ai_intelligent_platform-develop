package com.q.ai.mvc.service;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.biz.entity.*;
import com.q.ai.component.enuz.CHAT_STATE;
import com.q.ai.component.enuz.SLOT_STATE;
import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.RsException;
import com.q.ai.component.session.RequestContext;
import com.q.ai.component.session.SessionUtil;
import com.q.ai.component.util.NormalizationUtil;
import com.q.ai.mvc.dao.TRobotDao;
import com.q.ai.mvc.dao.po.*;
import com.q.ai.mvc.esDao.BaseDataValueDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Service
public class TRobotService {
    @Resource
    private TRobotDao tRobotDao;
    @Autowired
    RequestContext requestContext;
    @Autowired
    NlpService nlpService;
    @Resource(name = "mysql")
    SessionUtil sessionUtil;

    @Autowired
    WordSlotService wordSlotService;

    @Autowired
    BaseDataValueDao baseDataValueDao;

    public List<TRobot> getRobotList(int number, int size) {
        return tRobotDao.getRobotList(number,size);
    }

    public int getRobotCount() {
        return tRobotDao.getRobotCount();
    }

    public TRobot getRobotById(int id) {
        return tRobotDao.getRobotById(id);
    }

    public int save(TRobot tRobot) {
        int i = 0;
        if (tRobot.getId() != null) {//编辑
            tRobot.setUpdateTime(LocalDateTime.now());
            i = tRobotDao.updateRobot(tRobot);
        } else {//新增
            long min = 100000000L; // 最小值为100000000
            long max = 999999999L; // 最大值为999999999
            // 为关系随机生成一个id
            long FID = (long)(Math.random() * (max - min + 1)) + min;
            tRobot.setId(FID);
            int userId = requestContext.getSession().getUserId();
            tRobot.setCreatorId(Long.valueOf(String.valueOf(userId)));
            tRobot.setCreateTime(LocalDateTime.now());
            i = tRobotDao.insertRobot(tRobot);
        }
        return i;
    }

    public int deleteRobotIds(List<Long> robotIdList) {
        return tRobotDao.deleteRobotIds(robotIdList);
    }

    public List<TRobot> getRobotByLIke(String name) {
        name = "%" + name + "%";
        return tRobotDao.getRobotByLIke(name);
    }

    public List<TSkill> getSkillByRobotId(Long id) {
        return tRobotDao.getSkillByRobotId(id);
    }

    public ChatVo chat(int robotId,String chatMsg){
        if (chatMsg.length() > 200) {
            chatMsg = chatMsg.substring(0, 200);
        }
        // 查看机器人是否存在
        TRobot robot = getRobotById(robotId);
        if (null == robot) {
            throw new RsException("机器人不存在。");
        }
        Session session = requestContext.getSession();

        String currentIntentionNumber = session.getCurrentIntentionNumber();
        ChatWordSlot chatSlot2Fill = session.getChatWordSlot2Fill();
        String chatSlotNumber = chatSlot2Fill == null ? null : chatSlot2Fill.getNumber();
        JSONObject dataJson = nlpService.getIntent(robotId, chatMsg, currentIntentionNumber, chatSlotNumber);

        System.out.println("-------------->" + dataJson);

        JSONArray intentJsonArray = dataJson.getJSONArray("intent");
        JSONObject slot2Value = dataJson.getJSONObject("entities");

        currentIntentionNumber = intentJsonArray == null ? null : intentJsonArray.size() == 0 ? null : intentJsonArray.getJSONObject(0).getString("name");
        Map<String, Object> slot2ValueMap = slot2Value == null ? new HashMap<>() : slot2Value.getInnerMap();

        session = sessionUtil.buildIntentionAndFillWordSlot(session, currentIntentionNumber, slot2ValueMap, chatMsg);
//        sessionUtil.save(session);
        //提取填充完成之后，校验，问询
        //为什么要重新获取，因为在nlp填槽过程中意图可能已经切换

        ChatIntention chatIntention = session.getCurrentChatIntention();
        System.out.println("------------------------>" + chatIntention.toString());
        ChatVo chatVo = new ChatVo();
        chatVo.setIntention(chatIntention);

        if (null == chatIntention) {
            chatVo.setState(CHAT_STATE.NO_INTENT);
            chatVo.setMsg("当前没有获得意图");
            return chatVo;
        }

        List<ChatWordSlot> chatSlotList = chatIntention.getChatSlotList();
        if (null != chatSlotList && chatSlotList.size() != 0) {
            for (ChatWordSlot chatSlot : chatSlotList) {
                SLOT_STATE slotState = SLOT_STATE.UN_FILL;
                List<String> processedList = new ArrayList<>();

                if (StringUtils.isEmpty(chatSlot.getOriginString())) {
                    slotState = SLOT_STATE.UN_FILL;
                } else if (!chatSlot.getSlotState().equals(SLOT_STATE.VERIFY_SUCCESS)) {//需要校验的值不是空的，并且之前没有校验成功
                    Long slotId = chatSlot.getId();
                    SLOT_TYPE slotType = chatSlot.getType();
                    String origin = chatSlot.getOriginString();

                    Slot slot = wordSlotService.getById(Integer.valueOf(String.valueOf(slotId)));
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
        chatSlot2Fill = session.getChatWordSlot2Fill();

        if (null != chatSlot2Fill) {
            chatVo.setState(CHAT_STATE.CLARIFY);
            chatVo.setCurrentWordSlot(chatSlot2Fill);
            chatVo.setMsg("当前词槽需要澄清");
            return chatVo;
        }
        chatVo.setState(CHAT_STATE.COMPLETE);
        chatVo.setMsg("当前意图已经完成");
        return chatVo;
    }

}

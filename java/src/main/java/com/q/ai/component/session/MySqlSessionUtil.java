package com.q.ai.component.session;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.mysql.cj.util.StringUtils;
import com.q.ai.biz.entity.*;
import com.q.ai.component.enuz.SLOT_STATE;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.SessionDao;
import com.q.ai.mvc.dao.po.*;
import com.q.ai.mvc.service.IntentService;
import com.q.ai.mvc.service.IntentionService;
import com.q.ai.mvc.service.WordSlotService;
import com.q.ai.mvc.service.BaseDataValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

@Component("mysql")
public class MySqlSessionUtil implements SessionUtil {

    @Resource
    private SessionDao sessionDao;
    @Autowired
    IntentService intentService;

    @Autowired
    IntentionService intentionService;
    @Autowired
    WordSlotService wordSlotService;
    @Autowired
    BaseDataValueService baseDataValueService;

    public Session getById(String id) {
        SessionPO sessionPO = sessionDao.getById(id);
        if (null == sessionPO) {
            sessionPO = new SessionPO(id);
            sessionDao.insert(sessionPO);
        }
        return JSON.parseObject(sessionPO.getContent(), Session.class);
    }

    @Override
    public int delById(String id) {
        return sessionDao.delById(id);
    }

    @Override
    public void save(Session session) {
        SessionPO sessionPO = new SessionPO(session.getId());
        //sessionPO.setCreateTime(baseSession.getCreateTime());dao不会更新创建时间
        String content = JSON.toJSONString(session, SerializerFeature.DisableCircularReferenceDetect);
        if (content.length() >= 5000) {
            throw new RsException("session长度过长");
        }
        sessionPO.setContent(content);
        sessionDao.update(sessionPO);
    }


    /**
     * 1.词槽覆盖逻辑：如果新给词槽之前校验通过了，新提取的没有校验通过，则不覆盖
     * <p>
     * 2.保留上一个意图，和当前意图，切换后保存现场
     *
     * @return
     */
    @Override
    public Session buildIntentAndFillSlot(Session session, String nlpIntentNumber, Map<String, Object> slot2ValueMap, String userOriginString) {
        //  非词槽澄清过程中可能会没意图：
        //  1.首次可能会无意图
        // 2.上轮意图执行完后新的对话可能会无意图
        ChatIntent currentChatIntent = session.getCurrentChatIntent();
        if (null == nlpIntentNumber) {
            if (null != currentChatIntent) {//2.上轮意图执行完后新的对话可能会无意图
                session.setBeforeChatIntent(currentChatIntent);
                session.setCurrentChatIntent(null);
            }
            return session;
        }

        Intent nlpIntent = intentService.getByNumber(nlpIntentNumber);
        if (null == nlpIntent) {
            throw new RsException("NLP识别的意图已经不存在，可能需要重新训练");
        }


        ChatIntent beforeChatIntent = session.getBeforeChatIntent();
        if (null == currentChatIntent) {
            if (null == beforeChatIntent) { //1.全新,第一次填入（当前意图为空&&上次的意图为空）
                return newCurrentIntent(session, nlpIntent, slot2ValueMap);
            } else {
                if (beforeChatIntent.getNumber().equals(nlpIntentNumber)) {//2.nlp提取到了上轮意图，恢复上轮意图
                    session.setCurrentChatIntent(session.getBeforeChatIntent());
                    session.setBeforeChatIntent(null);
                    nlpSlotFillIntoCurrentIntent(session, slot2ValueMap, userOriginString);
                } else {// 3.不是恢复意图，全新填充currentIntent
                    return newCurrentIntent(session, nlpIntent, slot2ValueMap);
                }
            }
        } else {
            if (currentChatIntent.getNumber().equals(nlpIntentNumber)) {//4.当前意图与nlp意图一致，则合并填充词槽
                nlpSlotFillIntoCurrentIntent(session, slot2ValueMap, userOriginString);
            } else {//5.当前意图与nlp不一致，则当前意图保存为上轮意图，仍全新填充currentIntent
                session.setBeforeChatIntent(currentChatIntent);
                return newCurrentIntent(session, nlpIntent, slot2ValueMap);
            }
        }

        return session;
    }


    /**
     * 1.全新,第一次填入（当前意图为空&&上次的意图为空）
     */
    private Session newCurrentIntent(Session session, Intent nlpIntent, Map<String, Object> slot2ValueMap) {
        ChatIntent chatIntent = new ChatIntent(nlpIntent);

        List<Slot> slotList = wordSlotService.getListByIntentId(nlpIntent.getId(), new Page(1, 100));

        List<ChatSlot> chatSlotList = new ArrayList<>();

        for (Slot sLot : slotList) {
            ChatSlot chatSlot = new ChatSlot(sLot);
            String slotNumber = sLot.getNumber();
            String originValue = slot2ValueMap.containsKey(slotNumber) ? String.valueOf(slot2ValueMap.get(slotNumber)) : null;
            if (StringUtils.isNullOrEmpty(originValue)) {
                chatSlot.setSlotState(SLOT_STATE.UN_FILL);
            } else {
                chatSlot.setOriginString(originValue);
                chatSlot.setSlotState(SLOT_STATE.UN_VERIFY);
            }
            chatSlotList.add(chatSlot);
        }

        chatIntent.setChatSlotList(chatSlotList);
        session.setCurrentChatIntent(chatIntent);
        return session;
    }

    /**
     * nlp提取的词槽合并入
     * 词槽覆盖逻辑：如果词槽之前校验通过了，新提取的没有校验通过，则不覆盖；其他的都覆盖
     *
     * @param session
     * @param slot2ValueMap
     * @return
     */
    private Session nlpSlotFillIntoCurrentIntent(Session session, Map<String, Object> slot2ValueMap, String userOriginString) {
        assert session.getCurrentChatIntent() != null;
        ChatIntent currentChatIntent = session.getCurrentChatIntent();
        List<ChatSlot> chatSlotList = new ArrayList<>();
        for (ChatSlot chatSlot : currentChatIntent.getChatSlotList()) {
            String slotNumber = chatSlot.getNumber();
            String originValue = slot2ValueMap.containsKey(slotNumber) ? String.valueOf(slot2ValueMap.get(slotNumber)) : null;
            if (!StringUtils.isNullOrEmpty(originValue)) {
                //!(词槽之前校验通过，新给的没校验通过[这种不覆盖])
                boolean need2Replace = !(SLOT_STATE.VERIFY_SUCCESS.equals(chatSlot.getSlotState()) &&
                        baseDataValueService.isVerifySlot(chatSlot.getId(), chatSlot.getType(), originValue));
                if (need2Replace) {
                    chatSlot.setOriginString(originValue);
                    if (SLOT_STATE.VERIFY_CHOOSE.equals(chatSlot.getSlotState())) {
                        chatSlot.setOriginString(userOriginString);
                    }
                    chatSlot.setSlotState(SLOT_STATE.UN_VERIFY);
                }
            }
            chatSlotList.add(chatSlot);
        }
        currentChatIntent.setChatSlotList(chatSlotList);
        session.setCurrentChatIntent(currentChatIntent);
        return session;
    }

    /*-----------------------------------------------------------------------------------------------------------------*/
    @Override
    public Session buildIntentionAndFillWordSlot(Session session, String nlpIntentNumber, Map<String, Object> slot2ValueMap, String userOriginString) {
        //  非词槽澄清过程中可能会没意图：
        //  1.首次可能会无意图
        // 2.上轮意图执行完后新的对话可能会无意图
        ChatIntention chatIntention = session.getCurrentChatIntention();
        if (null == nlpIntentNumber) {
            if (null != chatIntention) {//2.上轮意图执行完后新的对话可能会无意图
                session.setBeforeChatIntention(chatIntention);
                session.setCurrentChatIntent(null);
            }
            return session;
        }

        Intention nlpIntent = intentionService.getByNumber(nlpIntentNumber);
        if (null == nlpIntent) {
            throw new RsException("NLP识别的意图已经不存在，可能需要重新训练");
        }

        ChatIntention beforeChatIntent = session.getBeforeChatIntention();
        if (null == chatIntention) {
            if (null == beforeChatIntent) { //1.全新,第一次填入（当前意图为空&&上次的意图为空）
                return newCurrentIntention(session, nlpIntent, slot2ValueMap);
            } else {
                if (beforeChatIntent.getNumber().equals(nlpIntentNumber)) {//2.nlp提取到了上轮意图，恢复上轮意图
                    session.setCurrentChatIntention(session.getBeforeChatIntention());
                    session.setBeforeChatIntent(null);
                    nlpSlotFillIntoCurrentIntent(session, slot2ValueMap, userOriginString);
                } else {// 3.不是恢复意图，全新填充currentIntent
                    return newCurrentIntention(session, nlpIntent, slot2ValueMap);
                }
            }
        } else {
            if (chatIntention.getNumber().equals(nlpIntentNumber)) {//4.当前意图与nlp意图一致，则合并填充词槽
                nlpSlotFillIntoCurrentIntent(session, slot2ValueMap, userOriginString);
            } else {//5.当前意图与nlp不一致，则当前意图保存为上轮意图，仍全新填充currentIntent
                session.setBeforeChatIntention(chatIntention);
                return newCurrentIntention(session, nlpIntent, slot2ValueMap);
            }
        }

        return session;
    }

    /**
     * 全新,第一次填入（当前意图为空&&上次的意图为空）(新的方法)
     * @param session
     * @param nlpIntent
     * @param slot2ValueMap
     * @return
     */
    private Session newCurrentIntention(Session session, Intention nlpIntent, Map<String, Object> slot2ValueMap) {
        ChatIntention chatIntention = new ChatIntention(nlpIntent);

        List<WordSlot> slotList = wordSlotService.getWordSlotByIntentionId(nlpIntent.getId());

        List<ChatWordSlot> chatSlotList = new ArrayList<>();

        for (WordSlot sLot : slotList) {
            ChatWordSlot chatSlot = new ChatWordSlot(sLot);
            String slotNumber = sLot.getNumber();
            String originValue = slot2ValueMap.containsKey(slotNumber) ? String.valueOf(slot2ValueMap.get(slotNumber)) : null;
            if (StringUtils.isNullOrEmpty(originValue)) {
                chatSlot.setSlotState(SLOT_STATE.UN_FILL);
            } else {
                chatSlot.setOriginString(originValue);
                chatSlot.setSlotState(SLOT_STATE.UN_VERIFY);
            }
            chatSlotList.add(chatSlot);
        }

        chatIntention.setChatSlotList(chatSlotList);
        session.setCurrentChatIntention(chatIntention);
        return session;
    }

    private Session nlpWordSlotFillIntoCurrentIntention(Session session, Map<String, Object> slot2ValueMap, String userOriginString) {
        assert session.getBeforeChatIntention() != null;
        ChatIntention chatIntention = session.getCurrentChatIntention();
        List<ChatWordSlot> chatSlotList = new ArrayList<>();
        for (ChatWordSlot chatSlot : chatIntention.getChatSlotList()) {
            String slotNumber = chatSlot.getNumber();
            String originValue = slot2ValueMap.containsKey(slotNumber) ? String.valueOf(slot2ValueMap.get(slotNumber)) : null;
            if (!StringUtils.isNullOrEmpty(originValue)) {
                //!(词槽之前校验通过，新给的没校验通过[这种不覆盖])
                boolean need2Replace = !(SLOT_STATE.VERIFY_SUCCESS.equals(chatSlot.getSlotState()) &&
                        baseDataValueService.isVerifySlot(Integer.valueOf(String.valueOf(chatSlot.getId())), chatSlot.getType(), originValue));
                if (need2Replace) {
                    chatSlot.setOriginString(originValue);
                    if (SLOT_STATE.VERIFY_CHOOSE.equals(chatSlot.getSlotState())) {
                        chatSlot.setOriginString(userOriginString);
                    }
                    chatSlot.setSlotState(SLOT_STATE.UN_VERIFY);
                }
            }
            chatSlotList.add(chatSlot);
        }
        chatIntention.setChatSlotList(chatSlotList);
        session.setCurrentChatIntention(chatIntention);
        return session;
    }


}

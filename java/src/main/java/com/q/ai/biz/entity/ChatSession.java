package com.q.ai.biz.entity;

import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.Robot;
import com.q.ai.mvc.dao.po.Slot;

import java.util.LinkedList;
import java.util.List;

public class ChatSession {
    private String sessionId;
    //机器人
    private Robot robot;
    //当前意图
    private Intent intent;
    //多轮意图
    private LinkedList<Intent> intentList;
    //当前意图的词槽列表
    private List<Slot> slotList;
    //当前用户输入
    private String chatMsg;
    //
    private List<String> chatMsgList;


}

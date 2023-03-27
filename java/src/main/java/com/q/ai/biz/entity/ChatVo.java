package com.q.ai.biz.entity;

import com.q.ai.component.enuz.CHAT_STATE;
import com.q.ai.mvc.dao.po.Intent;

public class ChatVo {
    private CHAT_STATE state;
    private ChatIntent intent;
    private String msg;
    private ChatSlot currentSlot;


    public CHAT_STATE getState() {
        return state;
    }

    public void setState(CHAT_STATE state) {
        this.state = state;
    }

    public ChatIntent getIntent() {
        return intent;
    }

    public void setIntent(ChatIntent intent) {
        this.intent = intent;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public ChatSlot getCurrentSlot() {
        return currentSlot;
    }

    public void setCurrentSlot(ChatSlot currentSlot) {
        this.currentSlot = currentSlot;
    }
}

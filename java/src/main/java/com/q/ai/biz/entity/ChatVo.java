package com.q.ai.biz.entity;

import com.q.ai.component.enuz.CHAT_STATE;

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


    private ChatIntention intention;
    private ChatWordSlot currentWordSlot;

    public ChatIntention getIntention() {
        return intention;
    }

    public ChatVo setIntention(ChatIntention intention) {
        this.intention = intention;
        return this;
    }

    public ChatWordSlot getCurrentWordSlot() {
        return currentWordSlot;
    }

    public ChatVo setCurrentWordSlot(ChatWordSlot currentWordSlot) {
        this.currentWordSlot = currentWordSlot;
        return this;
    }
}

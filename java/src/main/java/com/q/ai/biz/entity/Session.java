package com.q.ai.biz.entity;


import com.q.ai.component.enuz.APP;
import com.q.ai.component.enuz.CHAT_STATE;
import com.q.ai.component.enuz.SLOT_STATE;

import java.time.LocalDateTime;

public class Session {

    private String id;
    private int userId;
    private APP app;
    private ChatIntent currentChatIntent;
    private ChatIntent beforeChatIntent;

    private LocalDateTime createTime;

    public Session() {
    }

    /////////工具方法///////

    /**
     *
     * @return null or string
     */
    public String getCurrentIntentNumber(){
        if(null == currentChatIntent){
            return null;
        }
        return currentChatIntent.getNumber();
    }
    public String getBeforeIntentNumber(){
        if(null == beforeChatIntent){
            return null;
        }
        return beforeChatIntent.getNumber();
    }


    /**
     *
     * @return null if all complete or currentChatSlot
     */
    public ChatSlot getChatSlot2Fill(){
        if(null != currentChatIntent)
        for (ChatSlot chatSlot:currentChatIntent.getChatSlotList()){
            if (chatSlot.isMust() && !SLOT_STATE.VERIFY_SUCCESS.equals(chatSlot.getSlotState())) {
                return chatSlot;
            }
        }
        return null;
    }



    ////////////////

    public Session(String id) {
        this.id = id;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public int getUserId() {
        return userId;
    }

    public void setUserId(int userId) {
        this.userId = userId;
    }

    public APP getApp() {
        return app;
    }

    public void setApp(APP app) {
        this.app = app;
    }

    public CHAT_STATE getChatState() {
        if (null == currentChatIntent) {
            return CHAT_STATE.NO_INTENT;
        }
        return currentChatIntent.getChatState();
    }

    public ChatIntent getCurrentChatIntent() {
        return currentChatIntent;
    }

    public void setCurrentChatIntent(ChatIntent currentChatIntent) {
        this.currentChatIntent = currentChatIntent;
    }

    public ChatIntent getBeforeChatIntent() {
        return beforeChatIntent;
    }

    public void setBeforeChatIntent(ChatIntent beforeChatIntent) {
        this.beforeChatIntent = beforeChatIntent;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    @Override
    public String toString() {
        return "Session{" +
                "id='" + id + '\'' +
                ", userId=" + userId +
                ", app=" + app +
                ", currentChatIntent=" + currentChatIntent +
                ", beforeChatIntent=" + beforeChatIntent +
                ", createTime=" + createTime +
                '}';
    }
}

package com.q.ai.biz.entity;

import com.q.ai.component.enuz.CHAT_STATE;
import com.q.ai.mvc.dao.po.Intent;
import lombok.Builder;
import org.springframework.beans.BeanUtils;

import java.util.List;
@Builder
public class ChatIntent extends Intent {

    private CHAT_STATE chatState;

    private List<ChatSlot> chatSlotList;


    public ChatIntent() {
    }
    public ChatIntent(Intent intent) {
        BeanUtils.copyProperties(intent,this);
    }

    public CHAT_STATE getChatState() {
        return chatState;
    }

    public void setChatState(CHAT_STATE chatState) {
        this.chatState = chatState;
    }

    public List<ChatSlot> getChatSlotList() {
        return chatSlotList;
    }

    public void setChatSlotList(List<ChatSlot> chatSlotList) {
        this.chatSlotList = chatSlotList;
    }

    @Override
    public String toString() {
        return "ChatIntent{" +
                "chatState=" + chatState +
                ", chatSlotList=" + chatSlotList +
                '}';
    }
}

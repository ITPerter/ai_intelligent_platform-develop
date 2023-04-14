package com.q.ai.biz.entity;

import com.q.ai.component.enuz.CHAT_STATE;
import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.Intention;
import lombok.Builder;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Builder
public class ChatIntention extends Intention {

    private CHAT_STATE chatState;

    private List<ChatWordSlot> chatSlotList;


    public ChatIntention() {
    }
    public ChatIntention(Intention intent) {
        BeanUtils.copyProperties(intent,this);
    }

    public CHAT_STATE getChatState() {
        return chatState;
    }

    public void setChatState(CHAT_STATE chatState) {
        this.chatState = chatState;
    }

    public List<ChatWordSlot> getChatSlotList() {
        return chatSlotList;
    }

    public void setChatSlotList(List<ChatWordSlot> chatSlotList) {
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

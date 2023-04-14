package com.q.ai.biz.entity;

import com.q.ai.component.enuz.SLOT_STATE;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.mvc.dao.po.WordSlot;
import lombok.Builder;
import org.springframework.beans.BeanUtils;

import java.util.List;

@Builder
public class ChatWordSlot extends WordSlot {

    private SLOT_STATE slotState;

    private String originString;

    private List<String> verifyValueList;

    public ChatWordSlot() {
    }

    public ChatWordSlot(WordSlot slot) {
        BeanUtils.copyProperties(slot, this);
    }

    public String getOriginString() {
        return originString;
    }

    public void setOriginString(String originString) {
        this.originString = originString;
    }

    public List<String> getVerifyValueList() {
        return verifyValueList;
    }

    public void setVerifyValueList(List<String> verifyValueList) {
        this.verifyValueList = verifyValueList;
    }

    public SLOT_STATE getSlotState() {
        return slotState;
    }

    public void setSlotState(SLOT_STATE slotState) {
        this.slotState = slotState;
    }


    @Override
    public String toString() {
        return "ChatSlot{" +
                "slotState=" + slotState +
                ", originString='" + originString + '\'' +
                ", verifyValueList=" + verifyValueList +
                '}';
    }
}

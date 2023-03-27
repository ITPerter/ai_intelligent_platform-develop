package com.q.ai.biz.entity;

import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.Slot;
import java.util.List;

public class Current {

    //当前意图
    private Intent intent;
    //当前意图的词槽列表
    private List<Slot> slotList;

    public Intent getIntent() {
        return intent;
    }

    public void setIntent(Intent intent) {
        this.intent = intent;
    }

    public List<Slot> getSlotList() {
        return slotList;
    }

    public void setSlotList(List<Slot> slotList) {
        this.slotList = slotList;
    }
}

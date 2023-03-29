package com.q.ai.biz.entity;


import com.q.ai.mvc.dao.po.WordSlot;

/**
 * 意图、词槽合并数据实体类
 */
public class IntentionWordSlot extends WordSlot {
    private String intentionName;
    private String intentionNumber;
    private String intentionComment;

    public String getIntentionComment() {
        return intentionComment;
    }

    public IntentionWordSlot setIntentionComment(String intentionComment) {
        this.intentionComment = intentionComment;
        return this;
    }

    public String getIntentionName() {
        return intentionName;
    }

    public IntentionWordSlot setIntentionName(String intentionName) {
        this.intentionName = intentionName;
        return this;
    }

    public String getIntentionNumber() {
        return intentionNumber;
    }

    public IntentionWordSlot setIntentionNumber(String intentionNumber) {
        this.intentionNumber = intentionNumber;
        return this;
    }

}

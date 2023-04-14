package com.q.ai.mvc.dao.po;

import com.q.ai.component.enuz.SLOT_TYPE;

import java.time.LocalDateTime;

public class WordSlot {
    private Long id;
    private String number;
    private String name;
    private Long intentionId;
    private SLOT_TYPE type;
    private int isSpec;
    private Long  queryConfigID;
    private String voiceTemplet;
    private int isMust;
    private int useDefaultLoc;
    private int isFromParent;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    @Override
    public String toString() {
        return "WordSlot{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", intentionId=" + intentionId +
                ", type=" + type +
                ", isSpec=" + isSpec +
                ", queryConfigID=" + queryConfigID +
                ", voiceTemplet='" + voiceTemplet + '\'' +
                ", isMust=" + isMust +
                ", useDefaultLoc=" + useDefaultLoc +
                ", isFromParent=" + isFromParent +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }

    public Long getId() {
        return id;
    }

    public WordSlot setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public WordSlot setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public WordSlot setName(String name) {
        this.name = name;
        return this;
    }

    public Long getIntentionId() {
        return intentionId;
    }

    public WordSlot setIntentionId(Long intentionId) {
        this.intentionId = intentionId;
        return this;
    }

    public SLOT_TYPE getType() {
        return type;
    }

    public WordSlot setType(SLOT_TYPE type) {
        this.type = type;
        return this;
    }

    public int getIsSpec() {
        return isSpec;
    }

    public WordSlot setIsSpec(int isSpec) {
        this.isSpec = isSpec;
        return this;
    }

    public Long getQueryConfigID() {
        return queryConfigID;
    }

    public WordSlot setQueryConfigID(Long queryConfigID) {
        this.queryConfigID = queryConfigID;
        return this;
    }

    public String getVoiceTemplet() {
        return voiceTemplet;
    }

    public WordSlot setVoiceTemplet(String voiceTemplet) {
        this.voiceTemplet = voiceTemplet;
        return this;
    }

    public int getIsMust() {
        return isMust;
    }

    public WordSlot setIsMust(int isMust) {
        this.isMust = isMust;
        return this;
    }

    public int getUseDefaultLoc() {
        return useDefaultLoc;
    }

    public WordSlot setUseDefaultLoc(int useDefaultLoc) {
        this.useDefaultLoc = useDefaultLoc;
        return this;
    }

    public int getIsFromParent() {
        return isFromParent;
    }

    public WordSlot setIsFromParent(int isFromParent) {
        this.isFromParent = isFromParent;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public WordSlot setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public WordSlot setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}

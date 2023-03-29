package com.q.ai.mvc.dao.po;

import java.sql.Timestamp;

public class WordSlot {
    private Long id;
    private String number;
    private String name;
    private Long intentionId;
    private Long typeId;
    private int isSpec;
    private Long  queryConfigID;
    private String voiceTemplet;
    private int isMust;
    private int useDefaultLoc;
    private int isFromParent;
    private Timestamp createTime;
    private Timestamp updateTime;

    @Override
    public String toString() {
        return "WordSlot{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", intentionId=" + intentionId +
                ", typeId=" + typeId +
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

    public Long getTypeId() {
        return typeId;
    }

    public WordSlot setTypeId(Long typeId) {
        this.typeId = typeId;
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

    public Timestamp getCreateTime() {
        return createTime;
    }

    public WordSlot setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public Timestamp getUpdateTime() {
        return updateTime;
    }

    public WordSlot setUpdateTime(Timestamp updateTime) {
        this.updateTime = updateTime;
        return this;
    }
}

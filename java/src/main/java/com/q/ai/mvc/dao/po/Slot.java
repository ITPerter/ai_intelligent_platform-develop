package com.q.ai.mvc.dao.po;


import com.q.ai.component.enuz.SLOT_TYPE;

import java.time.LocalDateTime;

/**
 *  词槽实体类
 */
public class Slot {

    private int id;
    private String name;
    private String number;
    private String des;
    //词槽类型：1基础类 2候选集
    private SLOT_TYPE type;
    private String baseDataNumber;
    private boolean must;
    private String defaultValue;
    private String clarification;
    private int creator;
    private int updater;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
    }

    public SLOT_TYPE getType() {
        return type;
    }

    public void setType(SLOT_TYPE type) {
        this.type = type;
    }

    public String getBaseDataNumber() {
        return baseDataNumber;
    }

    public void setBaseDataNumber(String baseDataNumber) {
        this.baseDataNumber = baseDataNumber;
    }

    public boolean isMust() {
        return must;
    }

    public void setMust(boolean must) {
        this.must = must;
    }

    public String getDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(String defaultValue) {
        this.defaultValue = defaultValue;
    }

    public String getClarification() {
        return clarification;
    }

    public void setClarification(String clarification) {
        this.clarification = clarification;
    }

    public int getCreator() {
        return creator;
    }

    public void setCreator(int creator) {
        this.creator = creator;
    }

    public int getUpdater() {
        return updater;
    }

    public void setUpdater(int updater) {
        this.updater = updater;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public void setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
    }

    @Override
    public String toString() {
        return "Slot{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", number='" + number + '\'' +
                ", des='" + des + '\'' +
                ", type=" + type +
                ", baseDataNumber='" + baseDataNumber + '\'' +
                ", must=" + must +
                ", defaultValue='" + defaultValue + '\'' +
                ", clarification='" + clarification + '\'' +
                ", creator=" + creator +
                ", updater=" + updater +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                '}';
    }
}

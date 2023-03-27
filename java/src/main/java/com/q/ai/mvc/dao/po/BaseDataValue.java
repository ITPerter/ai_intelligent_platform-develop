package com.q.ai.mvc.dao.po;


import java.time.LocalDateTime;

public class BaseDataValue extends ESBaseData {
    private String baseDataNumber;
    private String value;
    private String number;
    private int creator;
    private int updater;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;

    public BaseDataValue() {
    }

    public BaseDataValue(String baseDataNumber, String value, String number) {
        this.baseDataNumber = baseDataNumber;
        this.value = value;
        this.number = number;
    }


    public String getBaseDataNumber() {
        return baseDataNumber;
    }

    public void setBaseDataNumber(String baseDataNumber) {
        this.baseDataNumber = baseDataNumber;
    }

    public String getValue() {
        return value;
    }

    public void setValue(String value) {
        this.value = value;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
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
}

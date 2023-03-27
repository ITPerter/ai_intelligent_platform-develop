package com.q.ai.mvc.dao.po;


import com.q.ai.component.util.Utils;
import com.alibaba.fastjson.annotation.JSONField;
import org.springframework.util.StringUtils;

import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Robot {

    private int id;
    private String name;
    private String number;
    //0未训练，1训练中，2训练成功，3训练失败
    private int trainState;
    private String des;
    private int creator;
    private int updater;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;


    @JSONField(serialize = false)
    private final String separator = "◉";

    @JSONField(serialize = false)
    private String exceptionRs = "";

    private List<String> exceptionList;

    @JSONField(serialize = false)
    private String noIntentRs = "";

    private List<String> noIntentList;

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

    public int getTrainState() {
        return trainState;
    }

    public void setTrainState(int trainState) {
        this.trainState = trainState;
    }

    public String getDes() {
        return des;
    }

    public void setDes(String des) {
        this.des = des;
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

    public String getExceptionRs() {
        return exceptionRs;
    }

    public void setExceptionRs(String exceptionRs) {
        this.exceptionRs = exceptionRs;
        if(!StringUtils.isEmpty(exceptionRs)){
            this.exceptionList = Arrays.asList(exceptionRs.split(separator));
        }
    }

    public String getNoIntentRs() {
        return noIntentRs;
    }

    public void setNoIntentRs(String noIntentRs) {
        this.noIntentRs = noIntentRs;
        if(!StringUtils.isEmpty(noIntentRs)){
            this.noIntentList = Arrays.asList(noIntentRs.split(separator));
        }
    }

    public List<String> getExceptionList() {
        if(!StringUtils.isEmpty(exceptionRs)){
            return Arrays.asList(exceptionRs.split(separator));
        }
        return new ArrayList<>();
    }

    public void setExceptionList(List<String> exceptionList) {
        this.exceptionList = exceptionList;
        this.exceptionRs = Utils.joinString(exceptionList, separator);
    }

    public List<String> getNoIntentList() {
        if(!StringUtils.isEmpty(noIntentRs)){
            return Arrays.asList(noIntentRs.split(separator));
        }
        return new ArrayList<>();
    }

    public void setNoIntentList(List<String> noIntentList) {
        this.noIntentList = noIntentList;
        this.noIntentRs =Utils.joinString(noIntentList, separator);
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

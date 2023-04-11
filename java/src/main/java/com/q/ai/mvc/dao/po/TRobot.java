package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;

public class TRobot {
    private Long id;
    private String number;
    private String name;
    private Integer version;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private Long creatorId;
    private Long tenantId;
    private String sessionInitUrl;
    private String appKey;
    private String appSecret;
    private int train;
//    0未训练，1训练中，2训练成功，3训练失败


    @Override
    public String toString() {
        return "TRobot{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", version=" + version +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creatorId=" + creatorId +
                ", tenantId=" + tenantId +
                ", sessionInitUrl='" + sessionInitUrl + '\'' +
                ", appKey='" + appKey + '\'' +
                ", appSecret='" + appSecret + '\'' +
                ", train=" + train +
                '}';
    }

    public int getTrain() {
        return train;
    }

    public TRobot setTrain(int train) {
        this.train = train;
        return this;
    }

    public Long getId() {
        return id;
    }

    public TRobot setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public TRobot setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public TRobot setName(String name) {
        this.name = name;
        return this;
    }

    public Integer getVersion() {
        return version;
    }

    public TRobot setVersion(Integer version) {
        this.version = version;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TRobot setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public TRobot setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public TRobot setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public TRobot setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Long getTenantId() {
        return tenantId;
    }

    public TRobot setTenantId(Long tenantId) {
        this.tenantId = tenantId;
        return this;
    }

    public String getSessionInitUrl() {
        return sessionInitUrl;
    }

    public TRobot setSessionInitUrl(String sessionInitUrl) {
        this.sessionInitUrl = sessionInitUrl;
        return this;
    }

    public String getAppKey() {
        return appKey;
    }

    public TRobot setAppKey(String appKey) {
        this.appKey = appKey;
        return this;
    }

    public String getAppSecret() {
        return appSecret;
    }

    public TRobot setAppSecret(String appSecret) {
        this.appSecret = appSecret;
        return this;
    }
}

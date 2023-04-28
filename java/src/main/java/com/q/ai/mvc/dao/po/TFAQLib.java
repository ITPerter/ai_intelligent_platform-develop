package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;

public class TFAQLib {
    private Long FID;
    private String FNumber;
    private String FName;
    private Character FStatus;
    private Long FUserID;
    private LocalDateTime FCreateTime;
    private Long FTenantID;
    private Character FLibType;
    
    public Long getFID() {
        return FID;
    }

    public TFAQLib setFID(Long FID) {
        this.FID = FID;
        return this;
    }

    public String getFNumber() {
        return FNumber;
    }

    public TFAQLib setFNumber(String FNumber) {
        this.FNumber = FNumber;
        return this;
    }

    public String getFName() {
        return FName;
    }

    public TFAQLib setFName(String FName) {
        this.FName = FName;
        return this;
    }

    public Character getFStatus() {
        return FStatus;
    }

    public TFAQLib setFStatus(Character FStatus) {
        this.FStatus = FStatus;
        return this;
    }

    public Long getFUserID() {
        return FUserID;
    }

    public TFAQLib setFUserID(Long FUserID) {
        this.FUserID = FUserID;
        return this;
    }

    public LocalDateTime getFCreateTime() {
        return FCreateTime;
    }

    public TFAQLib setFCreateTime(LocalDateTime FCreateTime) {
        this.FCreateTime = FCreateTime;
        return this;
    }

    public Long getFTenantID() {
        return FTenantID;
    }

    public TFAQLib setFTenantID(Long FTenantID) {
        this.FTenantID = FTenantID;
        return this;
    }

    public Character getFLibType() {
        return FLibType;
    }

    public TFAQLib setFLibType(Character FLibType) {
        this.FLibType = FLibType;
        return this;
    }

    @Override
    public String toString() {
        return "TFAQLib{" +
                "FID=" + FID +
                ", FNumber='" + FNumber + '\'' +
                ", FName='" + FName + '\'' +
                ", FStatus=" + FStatus +
                ", FUserID=" + FUserID +
                ", FCreateTime=" + FCreateTime +
                ", FTenantID=" + FTenantID +
                ", FLibType=" + FLibType +
                '}';
    }
}

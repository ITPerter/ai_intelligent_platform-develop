package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;

/**
 * 领域
 */
public class TFAQDomain {
    private Long FID;
    private Long FQAID;
    private String FNumber;
    private String FName;
    private String FDesc;
    private Long FUserID;
    private LocalDateTime FCreateTime;
    private Boolean FIsEnabled;
    private Long FTemplateLibID;
    private Long FTemplateDomainID;
    private Integer FRefCount;

    public Long getFID() {
        return FID;
    }

    public TFAQDomain setFID(Long FID) {
        this.FID = FID;
        return this;
    }

    public Long getFQAID() {
        return FQAID;
    }

    public TFAQDomain setFQAID(Long FQAID) {
        this.FQAID = FQAID;
        return this;
    }

    public String getFNumber() {
        return FNumber;
    }

    public TFAQDomain setFNumber(String FNumber) {
        this.FNumber = FNumber;
        return this;
    }

    public String getFName() {
        return FName;
    }

    public TFAQDomain setFName(String FName) {
        this.FName = FName;
        return this;
    }

    public String getFDesc() {
        return FDesc;
    }

    public TFAQDomain setFDesc(String FDesc) {
        this.FDesc = FDesc;
        return this;
    }

    public Long getFUserID() {
        return FUserID;
    }

    public TFAQDomain setFUserID(Long FUserID) {
        this.FUserID = FUserID;
        return this;
    }

    public LocalDateTime getFCreateTime() {
        return FCreateTime;
    }

    public TFAQDomain setFCreateTime(LocalDateTime FCreateTime) {
        this.FCreateTime = FCreateTime;
        return this;
    }

    public Boolean getFIsEnabled() {
        return FIsEnabled;
    }

    public TFAQDomain setFIsEnabled(Boolean FIsEnabled) {
        this.FIsEnabled = FIsEnabled;
        return this;
    }

    public Long getFTemplateLibID() {
        return FTemplateLibID;
    }

    public TFAQDomain setFTemplateLibID(Long FTemplateLibID) {
        this.FTemplateLibID = FTemplateLibID;
        return this;
    }

    public Long getFTemplateDomainID() {
        return FTemplateDomainID;
    }

    public TFAQDomain setFTemplateDomainID(Long FTemplateDomainID) {
        this.FTemplateDomainID = FTemplateDomainID;
        return this;
    }

    public Integer getFRefCount() {
        return FRefCount;
    }

    public TFAQDomain setFRefCount(Integer FRefCount) {
        this.FRefCount = FRefCount;
        return this;
    }

    @Override
    public String toString() {
        return "TFAQDomain{" +
                "FID=" + FID +
                ", FQAID=" + FQAID +
                ", FNumber='" + FNumber + '\'' +
                ", FName='" + FName + '\'' +
                ", FDesc='" + FDesc + '\'' +
                ", FUserID=" + FUserID +
                ", FCreateTime=" + FCreateTime +
                ", FIsEnabled=" + FIsEnabled +
                ", FTemplateLibID=" + FTemplateLibID +
                ", FTemplateDomainID=" + FTemplateDomainID +
                ", FRefCount=" + FRefCount +
                '}';
    }

}

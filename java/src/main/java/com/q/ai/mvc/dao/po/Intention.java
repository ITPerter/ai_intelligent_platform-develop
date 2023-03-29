package com.q.ai.mvc.dao.po;

import java.sql.Timestamp;

/**
 * 任务型技能意图表
 */
public class Intention {
    private Long id;
    private String number;
    private String name;
    /**
     * 0：未训练
     * 1：训练中
     * 2：已训练
     */
    private int starts;
    private Timestamp createTime;
    private int isConfirm;
    private String confirmTemplate;
    private Long parentId;
    private String type;
    private String comment;
    private Long creator;
    private int isShowCard;
    private String cardType;
    private int autoReply;

    @Override
    public String toString() {
        return "Intention{" +
                "id=" + id +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", starts=" + starts +
                ", createTime=" + createTime +
                ", isConfirm=" + isConfirm +
                ", confirmTemplate='" + confirmTemplate + '\'' +
                ", parentId=" + parentId +
                ", type='" + type + '\'' +
                ", comment='" + comment + '\'' +
                ", creator=" + creator +
                ", isShowCard=" + isShowCard +
                ", cardType='" + cardType + '\'' +
                ", autoReply=" + autoReply +
                '}';
    }

    public Long getId() {
        return id;
    }

    public Intention setId(Long id) {
        this.id = id;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public Intention setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public Intention setName(String name) {
        this.name = name;
        return this;
    }

    public int getStarts() {
        return starts;
    }

    public Intention setStarts(int starts) {
        this.starts = starts;
        return this;
    }

    public Timestamp getCreateTime() {
        return createTime;
    }

    public Intention setCreateTime(Timestamp createTime) {
        this.createTime = createTime;
        return this;
    }

    public int getIsConfirm() {
        return isConfirm;
    }

    public Intention setIsConfirm(int isConfirm) {
        this.isConfirm = isConfirm;
        return this;
    }

    public String getConfirmTemplate() {
        return confirmTemplate;
    }

    public Intention setConfirmTemplate(String confirmTemplate) {
        this.confirmTemplate = confirmTemplate;
        return this;
    }

    public Long getParentId() {
        return parentId;
    }

    public Intention setParentId(Long parentId) {
        this.parentId = parentId;
        return this;
    }

    public String getType() {
        return type;
    }

    public Intention setType(String type) {
        this.type = type;
        return this;
    }

    public String getComment() {
        return comment;
    }

    public Intention setComment(String comment) {
        this.comment = comment;
        return this;
    }

    public Long getCreator() {
        return creator;
    }

    public Intention setCreator(Long creator) {
        this.creator = creator;
        return this;
    }

    public int isShowCard() {
        return isShowCard;
    }

    public Intention setShowCard(int showCard) {
        isShowCard = showCard;
        return this;
    }

    public String getCardType() {
        return cardType;
    }

    public Intention setCardType(String cardType) {
        this.cardType = cardType;
        return this;
    }

    public int getAutoReply() {
        return autoReply;
    }

    public Intention setAutoReply(int autoReply) {
        this.autoReply = autoReply;
        return this;
    }
}

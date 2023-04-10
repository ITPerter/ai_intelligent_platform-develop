package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;
import java.util.List;

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
    private LocalDateTime createTime;
    private int isConfirm;
    private String confirmTemplate;
    private Long parentId;
    private String type;
    private String comment;
    private int creator;
    private int isShowCard;
    private String cardType;
    private int autoReply;

    public List<String> getSlotList() {
        return slotList;
    }
    public Intention setSlotList(List<String> slotList) {
        this.slotList = slotList;
        return this;
    }
    // 意图对应词槽列表
    private List<String> slotList;

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

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public Intention setCreateTime(LocalDateTime createTime) {
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

    public int getCreator() {
        return creator;
    }

    public Intention setCreator(int creator) {
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

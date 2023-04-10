package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;

/**
 * 技能列表实体类
 */
public class TSkill extends SkillIntention {
    private Long id;
    private int version;
    private String number;
    private String name;
    private int type;
    private String description;
    private LocalDateTime createTime;
    private LocalDateTime updateTime;
    private int creatorId;
    private Long prodLineId;
    private int status;
    private Long initInterface;
    private String iconPath;
    private boolean enabledTaskFlow;

    @Override
    public String toString() {
        return "TSkill{" +
                "id=" + id +
                ", version=" + version +
                ", number='" + number + '\'' +
                ", name='" + name + '\'' +
                ", type=" + type +
                ", description='" + description + '\'' +
                ", createTime=" + createTime +
                ", updateTime=" + updateTime +
                ", creatorId=" + creatorId +
                ", prodLineId=" + prodLineId +
                ", status=" + status +
                ", initInterface=" + initInterface +
                ", iconPath='" + iconPath + '\'' +
                ", enabledTaskFlow=" + enabledTaskFlow +
                '}';
    }

    public Long getId() {
        return id;
    }

    public TSkill setId(Long id) {
        this.id = id;
        return this;
    }

    public int getVersion() {
        return version;
    }

    public TSkill setVersion(int version) {
        this.version = version;
        return this;
    }

    public String getNumber() {
        return number;
    }

    public TSkill setNumber(String number) {
        this.number = number;
        return this;
    }

    public String getName() {
        return name;
    }

    public TSkill setName(String name) {
        this.name = name;
        return this;
    }

    public int getType() {
        return type;
    }

    public TSkill setType(int type) {
        this.type = type;
        return this;
    }

    public String getDescription() {
        return description;
    }

    public TSkill setDescription(String description) {
        this.description = description;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public TSkill setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public LocalDateTime getUpdateTime() {
        return updateTime;
    }

    public TSkill setUpdateTime(LocalDateTime updateTime) {
        this.updateTime = updateTime;
        return this;
    }

    public int getCreatorId() {
        return creatorId;
    }

    public TSkill setCreatorId(int creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public Long getProdLineId() {
        return prodLineId;
    }

    public TSkill setProdLineId(Long prodLineId) {
        this.prodLineId = prodLineId;
        return this;
    }

    public int getStatus() {
        return status;
    }

    public TSkill setStatus(int status) {
        this.status = status;
        return this;
    }

    public Long getInitInterface() {
        return initInterface;
    }

    public TSkill setInitInterface(Long initInterface) {
        this.initInterface = initInterface;
        return this;
    }

    public String getIconPath() {
        return iconPath;
    }

    public TSkill setIconPath(String iconPath) {
        this.iconPath = iconPath;
        return this;
    }

    public boolean isEnabledTaskFlow() {
        return enabledTaskFlow;
    }

    public TSkill setEnabledTaskFlow(boolean enabledTaskFlow) {
        this.enabledTaskFlow = enabledTaskFlow;
        return this;
    }
}

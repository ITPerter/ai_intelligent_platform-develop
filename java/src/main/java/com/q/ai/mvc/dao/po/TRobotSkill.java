package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;

public class TRobotSkill {
    private Long id;
    private Long robotId;
    private Long skillId;
    private Long creatorId;
    private LocalDateTime createTime;
    private Long bizSysId;
    private Integer status;
    private Integer seq;

    public Long getId() {
        return id;
    }

    public TRobotSkill setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getRobotId() {
        return robotId;
    }

    public TRobotSkill setRobotId(Long robotId) {
        this.robotId = robotId;
        return this;
    }

    public Long getSkillId() {
        return skillId;
    }

    public TRobotSkill setSkillId(Long skillId) {
        this.skillId = skillId;
        return this;
    }

    public Long getCreatorId() {
        return creatorId;
    }

    public TRobotSkill setCreatorId(Long creatorId) {
        this.creatorId = creatorId;
        return this;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public TRobotSkill setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
        return this;
    }

    public Long getBizSysId() {
        return bizSysId;
    }

    public TRobotSkill setBizSysId(Long bizSysId) {
        this.bizSysId = bizSysId;
        return this;
    }

    public Integer getStatus() {
        return status;
    }

    public TRobotSkill setStatus(Integer status) {
        this.status = status;
        return this;
    }

    public Integer getSeq() {
        return seq;
    }

    public TRobotSkill setSeq(Integer seq) {
        this.seq = seq;
        return this;
    }

    @Override
    public String toString() {
        return "TRobotSkill{" +
                "id=" + id +
                ", robotId=" + robotId +
                ", skillId=" + skillId +
                ", creatorId=" + creatorId +
                ", createTime=" + createTime +
                ", bizSysId=" + bizSysId +
                ", status=" + status +
                ", seq=" + seq +
                '}';
    }
}

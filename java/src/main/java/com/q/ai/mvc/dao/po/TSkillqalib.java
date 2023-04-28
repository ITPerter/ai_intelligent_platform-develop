package com.q.ai.mvc.dao.po;

/**
 * 技能知识库
 */
public class TSkillqalib {
    private Long FID;
    private Long FSkillID;
    private Long FQALibID;

    public Long getFID() {
        return FID;
    }

    public TSkillqalib setFID(Long FID) {
        this.FID = FID;
        return this;
    }

    public Long getFSkillID() {
        return FSkillID;
    }

    public TSkillqalib setFSkillID(Long FSkillID) {
        this.FSkillID = FSkillID;
        return this;
    }

    public Long getFQALibID() {
        return FQALibID;
    }

    public TSkillqalib setFQALibID(Long FQALibID) {
        this.FQALibID = FQALibID;
        return this;
    }

    @Override
    public String  toString() {
        return "TSkillqalib{" +
                "FID=" + FID +
                ", FSkillID=" + FSkillID +
                ", FQALibID=" + FQALibID +
                '}';
    }
}

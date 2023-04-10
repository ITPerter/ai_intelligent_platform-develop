package com.q.ai.mvc.dao.po;

/**
 * 技能意图关联表
 */
public class SkillIntention {
    private Long id;
    private Long skillId;
    private Long intentionId;
    private Long interfaceId;
    private Long UIStyleId;
    private String cardTemplate;

    @Override
    public String toString() {
        return "SkillLintention{" +
                "id=" + id +
                ", skillId=" + skillId +
                ", intentionId=" + intentionId +
                ", interfaceId=" + interfaceId +
                ", UIStyleId=" + UIStyleId +
                ", cardTemplate='" + cardTemplate + '\'' +
                '}';
    }

    public Long getId() {
        return id;
    }

    public SkillIntention setId(Long id) {
        this.id = id;
        return this;
    }

    public Long getSkillId() {
        return skillId;
    }

    public SkillIntention setSkillId(Long skillId) {
        this.skillId = skillId;
        return this;
    }

    public Long getIntentionId() {
        return intentionId;
    }

    public SkillIntention setIntentionId(Long intentionId) {
        this.intentionId = intentionId;
        return this;
    }

    public Long getInterfaceId() {
        return interfaceId;
    }

    public SkillIntention setInterfaceId(Long interfaceId) {
        this.interfaceId = interfaceId;
        return this;
    }

    public Long getUIStyleId() {
        return UIStyleId;
    }

    public SkillIntention setUIStyleId(Long UIStyleId) {
        this.UIStyleId = UIStyleId;
        return this;
    }

    public String getCardTemplate() {
        return cardTemplate;
    }

    public SkillIntention setCardTemplate(String cardTemplate) {
        this.cardTemplate = cardTemplate;
        return this;
    }
}

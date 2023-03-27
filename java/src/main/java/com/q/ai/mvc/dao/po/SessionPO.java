package com.q.ai.mvc.dao.po;

import java.time.LocalDateTime;

public class SessionPO {
    private String id;
    private String content;
    private LocalDateTime createTime;

    public SessionPO() {
    }

    public SessionPO(String id) {
        this.id = id;
        this.createTime = LocalDateTime.now();
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public LocalDateTime getCreateTime() {
        return createTime;
    }

    public void setCreateTime(LocalDateTime createTime) {
        this.createTime = createTime;
    }
}

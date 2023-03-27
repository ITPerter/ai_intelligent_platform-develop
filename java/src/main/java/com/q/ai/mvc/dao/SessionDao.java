package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.SessionPO;
import org.apache.ibatis.annotations.Param;

import java.time.LocalDateTime;
import java.util.List;


public interface SessionDao {

    SessionPO getById(@Param("id") String id);

    int insert(SessionPO sessionPO);

    int update(SessionPO sessionPO);

    int delById(@Param("id") String id);

    int delByTime(@Param("createTime") LocalDateTime createTime);
}
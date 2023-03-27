package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intent;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IntentDao {

    Intent getById(@Param("id") int id);

    Intent getByNumber(@Param("number") String number);

    int getCount();

    List<Intent> getList(@Param("offset") int offset, @Param("limit") int limit);

    List<Intent> getListByRobotId(@Param("id") int robotId,@Param("offset") int offset, @Param("limit") int limit);

    int getListByRobotIdCount(@Param("id") int robotId);

    int insert(Intent intent);

    int update(Intent intent);

    int delByIdList(@Param("list") List<Integer> idList);

    int delByRobotId(@Param("id") int robotId);
}
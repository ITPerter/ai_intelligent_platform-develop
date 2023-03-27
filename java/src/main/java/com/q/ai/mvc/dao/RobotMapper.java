package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Robot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RobotMapper {

    Robot getById(@Param("id") int id);

    int getCount();

    List<Robot> getList(@Param("offset") int offset,@Param("limit") int limit);

    int insert(Robot robot);

    int update(Robot robot);

    int delByIdList(@Param("list") List<Integer> idList);
}
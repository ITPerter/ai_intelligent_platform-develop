package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Robot;
import com.q.ai.mvc.dao.po.TRobot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRobotDao {
    List<TRobot> getRobotList(@Param("number") int number,@Param("size") int size);

    int getRobotCount();

    TRobot getRobotById(int id);

    int updateRobot(TRobot tRobot);

    int insertRobot(TRobot tRobot);

    int deleteRobotIds(List<Long> robotIdList);
}

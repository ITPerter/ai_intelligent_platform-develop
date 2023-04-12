package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.TRobot;
import com.q.ai.mvc.dao.po.TSkill;
import org.apache.ibatis.annotations.Param;

import java.util.List;

public interface TRobotDao {
    List<TRobot> getRobotList(@Param("number") int number,@Param("size") int size);

    int getRobotCount();

    TRobot getRobotById(int id);

    int updateRobot(TRobot tRobot);

    int insertRobot(TRobot tRobot);

    int deleteRobotIds(List<Long> robotIdList);

    List<TRobot> getRobotByLIke(String name);

    List<TSkill> getSkillByRobotId(Long id);
}

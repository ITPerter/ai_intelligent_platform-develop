package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Robot;
import com.q.ai.mvc.dao.po.Robot2Intent;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface Robot2IntentDao {

    int getCountByRobotId(@Param("robotId") int robotId);

    List<Robot> getIntentsByRobotId(@Param("robotId") int robotId, @Param("offset") int offset, @Param("limit") int limit);

    int inserts(@Param("list") List<Robot2Intent> robot2Intents);

    int delByIdList(@Param("list") List<Integer> idList);

    int delByRobotIdList(@Param("list") List<Integer> robotIdList);

    int delByIntentIdListAndRobotId(@Param("list") List<Integer> intentIdList,@Param("robotId") int robotId);
}
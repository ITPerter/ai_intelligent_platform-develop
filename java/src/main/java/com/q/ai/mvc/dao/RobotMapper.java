package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Robot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface RobotMapper {

    Robot getById(@Param("id") int id);

    /**
     * 获取机器人数量
     * @return
     */
    int getCount();

    /**
     * 机器人分页数据
     * @param offset
     * @param limit
     * @return
     */
    List<Robot> getList(@Param("offset") int offset,@Param("limit") int limit);

    /**
     * 添加机器人
     * @param robot
     * @return
     */
    int insert(Robot robot);

    /**
     * 更新机器人数据
     * @param robot
     * @return
     */
    int update(Robot robot);

    /**
     * 通过is删除机器人
     * @param idList
     * @return
     */
    int delByIdList(@Param("list") List<Integer> idList);
}
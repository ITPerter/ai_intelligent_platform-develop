package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.mvc.dao.po.Intention;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface IntentDao {

    /**
     * 通过意图id获取意图
     * @param id
     * @return
     */
    Intent getById(@Param("id") int id);

    /**
     * 通过编码获取意图
     * @param number
     * @return
     */
    Intent getByNumber(@Param("number") String number);

    /**
     * 获取意图总数
     * @return
     */
    int getCount();

    /**
     * 获取意图表分页数据
     * @param offset
     * @param limit
     * @return
     */
    List<Intent> getList(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过机器人id获取 机器人意图 分页数据
     * @param robotId
     * @param offset
     * @param limit
     * @return
     */
    List<Intent> getListByRobotId(@Param("id") int robotId,@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 通过机器人id获取 机器人意图数据 总条数
     * @param robotId
     * @return
     */
    int getListByRobotIdCount(@Param("id") int robotId);

    /**
     * 添加一条意图数据
     * @param intent
     * @return
     */
    int insert(Intent intent);

    /**
     * 更新一条意图数据（通过意图id）
     * @param intent
     * @return
     */
    int update(Intent intent);

    /**
     * 通过意图id删除意图数据（可连续删除多条数据，带循环）
     * @param idList
     * @return
     */
    int delByIdList(@Param("list") List<Integer> idList);

    /**
     * 通过机器人id删除该机器人的所有意图
     * @param robotId
     * @return
     */
    int delByRobotId(@Param("id") int robotId);

    /**
     * 添加意图
     * @param intention
     * @return
     */
    int addIntention(Intention intention);
}
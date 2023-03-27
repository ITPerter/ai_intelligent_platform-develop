package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intent2Slot;
import org.apache.ibatis.annotations.Param;

import java.util.List;

/**
 * 意图、词槽关联表dao
 */
public interface Intent2SlotDao {

//  Intent2Slot getById(@Param("id") int intent2SlotId);

    /**
     * @param intentId  意图id(intentId)
     * @param slotId    词槽id(slotId)
     * @return      意图词槽关联表数据
     */
    Intent2Slot getByIntentIdAndSlotId(@Param("intentId") int intentId, @Param("slotId") int slotId);

    /**
     * 获取升序排序第一个数据
     * @param intentId      意图id
     * @param seq       顺序
     * @return      返回意图、关系关联数据
     */
    Intent2Slot getGreaterSeq(@Param("intentId") int intentId,@Param("seq") int seq);

    /**
     * 获取降序排序第一个数据
     * @param intentId      意图id
     * @param seq       顺序
     * @return      返回意图、关系关联数据
     */
    Intent2Slot getLessSeq(@Param("intentId") int intentId,@Param("seq") int seq);

    int getCountByIntentId(@Param("intentId") int intentId);

    /**
     * 获取意图、词槽关联表中排序最高的数据
     * @param intentId      意图id
     * @return      意图、词槽关联表数据
     */
    Intent2Slot getSlotMaxSeqByIntentId(@Param("intentId") int intentId);

    /**
     *  插入意图、词槽关联对象
     * @param intent2Slots      意图、词槽关联实例对象
     * @return      插入成功结果
     */
    int inserts(@Param("list") List<Intent2Slot> intent2Slots);

    int updates(@Param("list") List<Intent2Slot> intent2Slots);

    int delByIdList(@Param("list") List<Integer> idList);

    int delByIntentIdList(@Param("list") List<Integer> intentIdList);

    /**
     *  删除意图、词槽关联表中的同一意图下的多个对应词槽关系
     * @param intentIdList
     * @param intentId
     * @return
     */
    int delBySlotIdListAndIntentId(@Param("list") List<Integer> intentIdList,@Param("intentId") int intentId);
}
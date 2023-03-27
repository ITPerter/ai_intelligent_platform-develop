package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Intent2Slot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface Intent2SlotDao {

//  Intent2Slot getById(@Param("id") int intent2SlotId);
    Intent2Slot getByIntentIdAndSlotId(@Param("intentId") int intentId, @Param("slotId") int slotId);

    Intent2Slot getGreaterSeq(@Param("intentId") int intentId,@Param("seq") int seq);

    Intent2Slot getLessSeq(@Param("intentId") int intentId,@Param("seq") int seq);

    int getCountByIntentId(@Param("intentId") int intentId);

    Intent2Slot getSlotMaxSeqByIntentId(@Param("intentId") int intentId);

    int inserts(@Param("list") List<Intent2Slot> intent2Slots);

    int updates(@Param("list") List<Intent2Slot> intent2Slots);

    int delByIdList(@Param("list") List<Integer> idList);

    int delByIntentIdList(@Param("list") List<Integer> intentIdList);

    int delBySlotIdListAndIntentId(@Param("list") List<Integer> intentIdList,@Param("intentId") int intentId);
}
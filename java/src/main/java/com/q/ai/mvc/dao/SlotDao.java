package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Slot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SlotDao {

    Slot getById(@Param("id") int id);

    Slot getByNumber(@Param("number") String id);

    int getCount();

    List<Slot> getList(@Param("offset") int offset, @Param("limit") int limit);

    List<Slot> getListByIntentId(@Param("id") int intentId, @Param("offset") int offset, @Param("limit") int limit);

    int getListByIntentIdCount(@Param("id") int intentId);

    int insert(Slot slot);

    int update(Slot slot);

    int delByIdList(@Param("list") List<Integer> idList);
}
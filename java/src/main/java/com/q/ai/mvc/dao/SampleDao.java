package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Sample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SampleDao {

    /**
     * 通过意图id获取意图对应文本条数
     * @param intentId
     * @return
     */
    int getListByIntentIdCount(@Param("id") int intentId);

    /**
     * 通过意图id获取意图（文本）分页数据
     * @param intentId
     * @param offset
     * @param limit
     * @return
     */
    List<Sample> getListByIntentId(@Param("id") int intentId, @Param("offset") int offset, @Param("limit") int limit);

    int inserts(@Param("list") List<Sample> samples);

    int update(Sample sample);

    int delByIdList(@Param("list") List<Integer> idList);
}
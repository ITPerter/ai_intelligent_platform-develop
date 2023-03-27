package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Sample;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SampleDao {


    int getListByIntentIdCount(@Param("id") int intentId);

    List<Sample> getListByIntentId(@Param("id") int intentId, @Param("offset") int offset, @Param("limit") int limit);

    int inserts(@Param("list") List<Sample> samples);

    int update(Sample sample);

    int delByIdList(@Param("list") List<Integer> idList);
}
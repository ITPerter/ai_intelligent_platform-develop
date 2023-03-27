package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.SampleItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SampleItemDao {

    int insert(SampleItem sampleItem);

    int update(SampleItem sampleItem);

    int delByIdList(@Param("list") List<Integer> sampleItemIdList);

    int delBySampleId(@Param("sampleId") Integer sampleId);

    List<SampleItem> getListByIdList(@Param("list") List<Integer> idList);

}
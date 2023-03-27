package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.SampleItem;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface SampleItemDao {

    int insert(SampleItem sampleItem);

    int update(SampleItem sampleItem);

    int delByIdList(@Param("list") List<Integer> sampleItemIdList);

    int delBySampleId(@Param("sampleId") Integer sampleId);

    /**
     * 通过意图文本id集获取每个文本的答案（答案可以有多个）
     * @param idList
     * @return  文本对应的问题集
     */
    List<SampleItem> getListByIdList(@Param("list") List<Integer> idList);

}
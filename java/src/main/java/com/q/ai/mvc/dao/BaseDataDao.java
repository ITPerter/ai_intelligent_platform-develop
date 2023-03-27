package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.BaseData;
import com.q.ai.mvc.dao.po.Slot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BaseDataDao {

    BaseData getById(@Param("id") int id);


    BaseData getByNumber(@Param("number") String number);

    int getListCount();

    List<BaseData> getList(@Param("offset") int offset, @Param("limit") int limit);

    int insert(BaseData baseData);

    int update(BaseData baseData);

    int delByNumberList(@Param("list") List<String> numberList);
}
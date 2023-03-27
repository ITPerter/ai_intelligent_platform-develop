package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.BaseData;
import com.q.ai.mvc.dao.po.Slot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface BaseDataDao {

    /**
     * 通过id查询基础资料数据
     * @param id
     * @return
     */
    BaseData getById(@Param("id") int id);

    /**
     * 通过基础数据编码查询
     * @param number
     * @return
     */
    BaseData getByNumber(@Param("number") String number);

    /**
     * 获取基础数据总条数
     * @return
     */
    int getListCount();

    /**
     * 对基础数据分页
     * @param offset
     * @param limit
     * @return
     */
    List<BaseData> getList(@Param("offset") int offset, @Param("limit") int limit);

    /**
     * 添加一条基础数据
     * @param baseData  基础数据对象
     * @return
     */
    int insert(BaseData baseData);

    /**
     * 更新基础数据
     * @param baseData
     * @return
     */
    int update(BaseData baseData);

    /**
     * 通过基础数据集删除数据
     * @param numberList
     * @return
     */
    int delByNumberList(@Param("list") List<String> numberList);
}
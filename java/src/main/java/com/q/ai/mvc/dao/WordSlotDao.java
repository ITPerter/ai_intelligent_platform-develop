package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.mvc.dao.po.WordSlot;
import org.apache.ibatis.annotations.Param;

import java.util.List;


public interface WordSlotDao {

    Slot getById(@Param("id") int id);

    Slot getByNumber(@Param("number") String id);

    int getCount();

    List<Slot> getList(@Param("offset") int offset, @Param("limit") int limit);

    List<Slot> getListByIntentId(@Param("id") int intentId, @Param("offset") int offset, @Param("limit") int limit);

    int getListByIntentIdCount(@Param("id") int intentId);

    int insert(Slot slot);

    int update(Slot slot);

    /**
     * 通过词槽id列表删除删除一些词槽数据
     * @param idList
     * @return
     */
    int delByIdList(@Param("list") List<Integer> idList);

    /**
     * 通过意图id获取与意图有关的词槽
     * @param id
     * @return
     */
    List<WordSlot> getWordSlotByIntentionId(@Param("id") Long id);

    List<WordSlot> getWordSlotByIntentionId2(@Param("id") Long id);

    /**
     * 我把意图id集获取词槽列表
     * @param intentionIds
     * @return
     */
    List<WordSlot> getWordSlotByIntentionIds(List<Long> intentionIds);

    /**
     * 通过意图编码获取词槽信息
     * @param number
     * @return
     */
    List<WordSlot> getSlotByNumber(String number);

    /**
     * 通过id获取词槽信息
     * @param id
     * @return
     */
    WordSlot getWordSlotById(Long id);

    /**
     * 更新词槽信息
     * @param wordSlot
     * @return
     */
    int updateSlot(WordSlot wordSlot);

    int addSlot(WordSlot wordSlot);

    int deleteSlot(List<Long> ids);
}
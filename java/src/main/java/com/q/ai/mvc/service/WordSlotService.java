package com.q.ai.mvc.service;

import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.po.Intent2Slot;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.Intent2SlotDao;
import com.q.ai.mvc.dao.WordSlotDao;
import com.q.ai.mvc.dao.po.WordSlot;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 *      des: 词槽业务
 */
@Service
public class WordSlotService {

    @Resource
    private WordSlotDao wordSlotDao;
    @Resource
    private Intent2SlotDao intent2SlotDao;
    @Resource
    private RequestContext requestContext;

    /**
     * 通过意图id获取与意图有关的词槽
     * @param id
     * @return
     */
    public List<WordSlot> getWordSlotByIntentionId(Long id) {
        return wordSlotDao.getWordSlotByIntentionId(id);
    }

    /**
     * 通过id查询词槽
     * @param id    词槽id
     * @return      词槽表中数据
     */
    public Slot getById(int id) {
        return wordSlotDao.getById(id);
    }

    /**
     * 通过编码查询词槽
     * @param number    词槽编码（FNumber）
     * @return      词槽表中数据
     */
    public Slot getByNumber(String number) {
        return wordSlotDao.getByNumber(number);
    }

    /**
     * 获取词槽列表
     * @param page      分页数据
     * @return     返回设置条数的词槽表数据
     */
    public List<Slot> getList(Page page) {
        page.setTotal(wordSlotDao.getCount());
        return wordSlotDao.getList(page.getOffset(), page.getLimit());
    }

    /**
     * 通过意图id查询词槽信息
     * @param intentId      意图id
     * @param page   分页对象
     * @return  返回有限条数的词槽表数据
     */
    public List<Slot> getListByIntentId(int intentId, Page page) {
        page.setTotal(wordSlotDao.getListByIntentIdCount(intentId));
        return wordSlotDao.getListByIntentId(intentId, page.getOffset(), page.getLimit());
    }


    /**
     * 只在新建时去匹配关联关系
     * @param slot      词槽对象
     * @param intentId      意图对象
     * @return      返回一个经过加工的词槽对象
     */
    @Transient
    public Slot saveWithIntentId(Slot slot,Integer intentId) {
        if(StringUtils.isEmpty(slot.getType())){
            slot.setType(SLOT_TYPE.TEXT);
        }
        if (slot.getId() != 0) {
            // 更新
            slot.setUpdateTime(LocalDateTime.now());
            wordSlotDao.update(slot);
        } else {
            // 新增
            slot.setCreator(requestContext.getSession().getUserId());
            slot.setCreateTime(LocalDateTime.now());
            slot.setUpdateTime(LocalDateTime.now());
            wordSlotDao.insert(slot);
            if(intentId != null){
                // 判断意图id是否为空
                // 建立词槽（slot）和意图（intent）对应关系
                add2Intent(Collections.singletonList(slot.getId()),intentId);
            }
        }
        return slot;
    }

    /**
     *  添加意图
     * @param slotIdList    词槽列表
     * @param intentId      意图id
     * @return      返回插入结果
     */
    public int add2Intent(List<Integer> slotIdList,int intentId){
        // 查询意图（intent）下词槽的最大序号
        Intent2Slot maxSeqIntent2Slot = intent2SlotDao.getSlotMaxSeqByIntentId(intentId);
        int seq = 0;
        if(null != maxSeqIntent2Slot){
            seq = maxSeqIntent2Slot.getSeq()+1;
        }
        List<Intent2Slot> intent2Slots = new ArrayList<>();
        for (int slotId:slotIdList){
            Intent2Slot intent2Slot = new Intent2Slot();
            intent2Slot.setCreateTime(LocalDateTime.now());
            intent2Slot.setIntentId(intentId);
            intent2Slot.setSlotId(slotId);
            intent2Slot.setSeq(seq++);
            intent2Slot.setCreator(requestContext.getSession().getUserId());
            intent2Slot.setCreateTime(LocalDateTime.now());
            intent2Slots.add(intent2Slot);
        }

        return intent2SlotDao.inserts(intent2Slots);

    }

    /**
     *  交换词槽的顺序，并重新获取新的词槽
     * @param intentId      意图id
     * @param slotId        词槽id
     * @param upOrDown      上移 or 下移
     * @param page      分页数据
     * @return
     */
    public List<Slot> changSeqAndGetList(int intentId,int slotId,int upOrDown,Page page){
        if(Math.abs(upOrDown) != 1){
            throw new RsException("顺序调整取值必须是-1 或 1");
        }
        Intent2Slot intent2Slot = intent2SlotDao.getByIntentIdAndSlotId(intentId, slotId);
        if(intent2Slot == null){
            throw new RsException("要调整的词槽关系不存在");
        }
        int seq = intent2Slot.getSeq();
        Intent2Slot intent2Slot2Change;
        if(upOrDown == 1) {
            intent2Slot2Change  = intent2SlotDao.getGreaterSeq(intentId, seq);
        }else {
            intent2Slot2Change = intent2SlotDao.getLessSeq(intentId, seq);
        }

        if(intent2Slot2Change == null){
            // 与最前或者最后交换
            throw new RsException("已经到顶/底了");
        }

        //交换顺序，所以只要一个参数,避免了深拷贝
        intent2Slot.setSeq(intent2Slot2Change.getSeq());
        intent2Slot2Change.setSeq(seq);

        List<Intent2Slot> intent2Slots = new ArrayList<>();
        intent2Slots.add(intent2Slot);
        intent2Slots.add(intent2Slot2Change);

        intent2SlotDao.updates(intent2Slots);
        return getListByIntentId(intentId,page);

    }

    /**
     * 删除意图、词槽关联表中的同一意图下的多个对应词槽关系
     * @param intentIdList
     * @param intentId
     * @return
     */
    public int removeIntentIdsFromRobot(List<Integer> intentIdList,int intentId){
        return intent2SlotDao.delBySlotIdListAndIntentId(intentIdList,intentId);
    }

    /**
     * 通过词槽id列表删除删除一些词槽数据
     * @param idList
     * @return
     */
    public int delByIdList(List<Integer> idList){
        return wordSlotDao.delByIdList(idList);
    }


}

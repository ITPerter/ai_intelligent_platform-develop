package com.q.ai.mvc.service;

import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.po.Intent2Slot;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.Intent2SlotDao;
import com.q.ai.mvc.dao.SlotDao;
import com.time.util.StringUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class SlotService {


    @Autowired
    SlotDao slotDao;
    @Autowired
    Intent2SlotDao intent2SlotDao;
    @Autowired
    RequestContext requestContext;


    public Slot getById(int id) {
        return slotDao.getById(id);
    }

    public Slot getByNumber(String number) {
        return slotDao.getByNumber(number);
    }

    public List<Slot> getList(Page page) {
        page.setTotal(slotDao.getCount());
        return slotDao.getList(page.getOffset(), page.getLimit());
    }

    public List<Slot> getListByIntentId(int intentId, Page page) {
        page.setTotal(slotDao.getListByIntentIdCount(intentId));
        return slotDao.getListByIntentId(intentId, page.getOffset(), page.getLimit());
    }


    /**
     * 只在新建时去匹配关联关系
     * @param slot
     * @param intentId
     * @return
     */

    @Transient
    public Slot saveWithIntentId(Slot slot,Integer intentId) {
        if(StringUtils.isEmpty(slot.getType())){
            slot.setType(SLOT_TYPE.TEXT);
        }
        if (slot.getId() != 0) {//编辑
            slot.setUpdateTime(LocalDateTime.now());
            slotDao.update(slot);
        } else {//新增
            slot.setCreator(requestContext.getSession().getUserId());
            slot.setCreateTime(LocalDateTime.now());
            slot.setUpdateTime(LocalDateTime.now());
            slotDao.insert(slot);
            if(intentId != null){//
                add2Intent(Collections.singletonList(slot.getId()),intentId);
            }
        }

        return slot;
    }

    /**
     *
     * 词槽添加到意图
     */
    public int add2Intent(List<Integer> slotIdList,int intentId){
        //查询intent下词槽的最大序号
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

    public List<Slot> changSeqAndGetList(int intentId,int slotId,int upOrDown,Page page){
        if(Math.abs(upOrDown)!=1){
            throw new RsException("顺序调整取值必须是-1 或 1");
        }
        Intent2Slot intent2Slot = intent2SlotDao.getByIntentIdAndSlotId(intentId,slotId);
        if(intent2Slot == null){
            throw new RsException("要调整的词槽关系不存在");
        }
        int seq = intent2Slot.getSeq();

        Intent2Slot intent2Slot2Change;
        if(upOrDown == 1){
            intent2Slot2Change  = intent2SlotDao.getGreaterSeq(intentId, seq);
        }else {
            intent2Slot2Change = intent2SlotDao.getLessSeq(intentId,seq);
        }

        if(intent2Slot2Change == null){//与最前或者最后交换
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

    public int removeIntentIdsFromRobot(List<Integer> intentIdList,int intentId){
        return intent2SlotDao.delBySlotIdListAndIntentId(intentIdList,intentId);
    }



    public int delByIdList(List<Integer> idList){
        return slotDao.delByIdList(idList);
    }


}

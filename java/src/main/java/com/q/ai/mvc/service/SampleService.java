package com.q.ai.mvc.service;

import com.alibaba.fastjson.JSON;
import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.po.Sample;
import com.q.ai.mvc.dao.po.SampleItem;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.vo.SampleVo;
import com.q.ai.mvc.dao.SampleDao;
import com.q.ai.mvc.dao.SampleItemDao;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.*;

@Service
public class SampleService {


    @Autowired
    SampleDao sampleDao;

    @Autowired
    SampleItemDao sampleItemDao;
    @Autowired
    RequestContext requestContext;


    public List<Sample> getListByIntentId(int intentId, Page page) {
        page.setTotal(sampleDao.getListByIntentIdCount(intentId));
        return sampleDao.getListByIntentId(intentId,page.getOffset(), page.getLimit());
    }

    /**
     * vo 给前端包装了sampleItemList
     * @param intentId
     * @param page
     * @return
     */
    public List<SampleVo> getVoListByIntentId(int intentId, Page page) {
        page.setTotal(sampleDao.getListByIntentIdCount(intentId));
        List<Sample> sampleList = sampleDao.getListByIntentId(intentId, page.getOffset(), page.getLimit());
        Map<Integer, List<SampleItem>> sampleId2SampleSlotMap = getSampleItemListBySampleList(sampleList);

        List<SampleVo> sampleVoList = new ArrayList<>();
        for (Sample sample:sampleList){
            //todo try BeanUtils.copyProperties()
            SampleVo sampleVo = JSON.toJavaObject(JSON.parseObject(JSON.toJSONString(sample)), SampleVo.class);
            sampleVo.setSampleItemList(sampleId2SampleSlotMap.get(sample.getId()));
            sampleVoList.add(sampleVo);
        }
        return sampleVoList;
    }


    /**
     * 只在新建时去匹配关联关系
     * @param sample
     * @return
     */

    @Transient
    public Sample save(Sample sample) {
        if (sample.getId() != 0) {//编辑
            sample.setUpdateTime(LocalDateTime.now());
            sampleDao.update(sample);
            //编辑移除标注
            sampleItemDao.delBySampleId(sample.getId());

        } else {//新增
            sample.setCreator(requestContext.getSession().getUserId());
            sample.setCreateTime(LocalDateTime.now());
            sample.setUpdateTime(LocalDateTime.now());
            sampleDao.inserts(Collections.singletonList(sample));

        }

        return sample;
    }



    public int delByIdList(List<Integer> idList){
        return sampleDao.delByIdList(idList);
    }



    //////////////////**
    //**********下面是样本标注操作
    //////////////////

    /**
     * 移除标注
     *
     * @param sampleItemId
     * @return
     */
    public int delSampleItemById(int sampleItemId) {
        return sampleItemDao.delByIdList(Collections.singletonList(sampleItemId));
    }

    /**
     * 插入标注
     *
     * @param sampleItem
     * @return
     */
    public int saveSampleItem(SampleItem sampleItem) {
        if(sampleItem.getId() == 0){
            return sampleItemDao.insert(sampleItem);
        }else{
            return sampleItemDao.update(sampleItem);
        }
    }

    public Map<Integer, List<SampleItem>> getSampleItemListBySampleList(List<Sample> sampleList) {
        List<Integer> sampleIdList = new ArrayList<>();
        for (Sample sample : sampleList) {
            sampleIdList.add(sample.getId());
        }

        Map<Integer, List<SampleItem>> sampleId2SampleItemMap = new HashMap<>();
        if (sampleIdList.size() == 0) {
            return sampleId2SampleItemMap;
        }
        List<SampleItem> sampleItemList = sampleItemDao.getListByIdList(sampleIdList);

        for (SampleItem sampleItem : sampleItemList) {
            int sampleId = sampleItem.getSampleId();
            List<SampleItem> sampleItemListTem = sampleId2SampleItemMap.getOrDefault(sampleId, new ArrayList<>());
            sampleItemListTem.add(sampleItem);
            sampleId2SampleItemMap.put(sampleId, sampleItemListTem);
        }

        return sampleId2SampleItemMap;

    }


}

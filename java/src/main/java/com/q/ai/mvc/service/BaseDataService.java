package com.q.ai.mvc.service;

import com.q.ai.component.io.Page;
import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.BaseDataDao;
import com.q.ai.mvc.dao.po.BaseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BaseDataService {


    @Autowired
    BaseDataDao baseDataDao;
    @Autowired
    BaseDataValueService baseDataValueService;
    @Autowired()
    RequestContext requestContext;

    public BaseData getById(int id) {
        return baseDataDao.getById(id);
    }
    public BaseData getByNumber(String number) {
        return baseDataDao.getByNumber(number);
    }


    public List<BaseData> getList(Page page) {
        page.setTotal(baseDataDao.getListCount());
        return baseDataDao.getList(page.getOffset(), page.getLimit());
    }


    @Transient
    public BaseData save(BaseData baseData) {
        if (baseData.getId() != 0) {//编辑
            baseDataDao.update(baseData);
        } else {//新增
            baseData.setCreator(requestContext.getSession().getUserId());
            baseData.setCreateTime(LocalDateTime.now());
            baseDataDao.insert(baseData);
        }

        return baseData;
    }


    @Transient
    public int delByNumberList(List<String> baseDataNumberList) {
        baseDataValueService.delByBaseDataNumber(baseDataNumberList);
        return baseDataDao.delByNumberList(baseDataNumberList);
    }

    /////////////////////


}

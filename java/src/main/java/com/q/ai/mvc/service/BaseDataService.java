package com.q.ai.mvc.service;

import com.q.ai.component.io.Page;
import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.dao.BaseDataDao;
import com.q.ai.mvc.dao.po.BaseData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.beans.Transient;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class BaseDataService {


    @Resource
    BaseDataDao baseDataDao;
    @Resource
    BaseDataValueService baseDataValueService;
    @Resource
    RequestContext requestContext;

    /**
     * 通过id查询基础数据
     * @param id
     * @return
     */
    public BaseData getById(int id) {
        return baseDataDao.getById(id);
    }

    /**
     * 通过基础数据编码获取基础数据
     * @param number
     * @return
     */
    public BaseData getByNumber(String number) {
        return baseDataDao.getByNumber(number);
    }

    /**
     * 获取分页的基础数据
     * @param page
     * @return
     */
    public List<BaseData> getList(Page page) {
        page.setTotal(baseDataDao.getListCount());
        return baseDataDao.getList(page.getOffset(), page.getLimit());
    }

    /**
     * 接受一个baseData对象，判断对象是否存在，存在就更新，不存在就添加
     * @param baseData
     * @return
     */
    @Transient
    public BaseData save(BaseData baseData) {
        if (baseData.getId() != 0) {    //编辑
            baseDataDao.update(baseData);
        } else {//新增
            baseData.setCreator(requestContext.getSession().getUserId());
            baseData.setCreateTime(LocalDateTime.now());
            baseDataDao.insert(baseData);
        }

        return baseData;
    }

    /**
     * 通过基础数据编码集删除数据
     * @param baseDataNumberList
     * @return
     */
    @Transient
    public int delByNumberList(List<String> baseDataNumberList) {
        baseDataValueService.delByBaseDataNumber(baseDataNumberList);
        return baseDataDao.delByNumberList(baseDataNumberList);
    }

}

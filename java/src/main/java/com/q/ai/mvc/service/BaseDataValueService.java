package com.q.ai.mvc.service;

import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.component.io.RsException;
import com.q.ai.component.session.RequestContext;
import com.q.ai.component.util.NormalizationUtil;
import com.q.ai.mvc.dao.po.BaseDataValue;
import com.q.ai.component.io.Page;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.mvc.esDao.BaseDataValueDao;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.StringUtils;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

@Service
public class BaseDataValueService {

    @Resource
    BaseDataValueDao baseDataValueDao;
    @Resource
    WordSlotService wordSlotService;
    @Resource
    RequestContext requestContext;

    public BaseDataValue getByNumber(String number) {
        return baseDataValueDao.getByNumber(number);
    }

    public List<BaseDataValue> search(String baseDataNumber, String value, Page page) {
        return baseDataValueDao.searchByBaseDataNumber(baseDataNumber, value, page);
    }

    public List<BaseDataValue> getListByBaseDataNumber(String baseDataNumber, Page page) {
        return baseDataValueDao.getListByBaseDataNumber(baseDataNumber, page);
    }

    /**
     * number字段唯一，重复则编辑
     * @param baseDataValue
     * @return
     */
    public BaseDataValue save(BaseDataValue baseDataValue) {
        baseDataValue.setCreator(requestContext.getSession().getUserId());
        baseDataValue.set_id(baseDataValue.getNumber());
        baseDataValue.setCreateTime(LocalDateTime.now());
        baseDataValue.setUpdateTime(LocalDateTime.now());
        baseDataValueDao.add(baseDataValue);
        return baseDataValue;
    }


    @Transactional
    public boolean replaceAll(String baseDataNumber, List<BaseDataValue> baseDataValueList) {
        delByBaseDataNumber(Collections.singletonList(baseDataNumber));
        return addList(baseDataNumber, baseDataValueList);
    }

    public boolean addList(String baseDataNumber, List<BaseDataValue> baseDataValueList) {
        LocalDateTime now = LocalDateTime.now();
        List<BaseDataValue> baseDataValueListNew = new ArrayList<>();
        for (BaseDataValue baseDataValue : baseDataValueList) {
            baseDataValue.setBaseDataNumber(baseDataNumber);
            baseDataValue.setCreator(1);
            //编码为空，value 设置成编码
            if (StringUtils.isEmpty(baseDataValue.getNumber())) {
                baseDataValue.setNumber(baseDataValue.getValue());
            }
            //编码设置成_id
            if (StringUtils.isEmpty(baseDataValue.get_id())) {
                baseDataValue.set_id(baseDataValue.getNumber());
            }
            baseDataValue.setCreateTime(now);
            baseDataValue.setUpdater(1);
            baseDataValue.setUpdateTime(now);
            baseDataValueListNew.add(baseDataValue);
        }
        return baseDataValueDao.inserts(baseDataValueListNew);
    }


    /**
     * 匹配失败判断，true标识成功和待选
     *
     * @param slotId
     * @param slotType
     * @param origin
     * @return
     */
    public boolean isVerifySlot(int slotId, SLOT_TYPE slotType, String origin) {
        Slot slot = wordSlotService.getById(slotId);
        if (null == slot) {
            throw new RsException("词槽不存在");
        }

        switch (slotType) {
            case BASE_DATA:
                String baseDataNumber = slot.getBaseDataNumber();
                List<BaseDataValue> baseDataValueList = baseDataValueDao.searchByBaseDataNumber(baseDataNumber, origin, new Page(1, 5));

                if (baseDataValueList == null || baseDataValueList.isEmpty()) {//没有校验通过
                    return false;
                }

            case TIME:
                LocalDateTime time = NormalizationUtil.getNormalizationTime(origin);
                if (null == time) {
                    return false;
                }

            case TEXT:

                break;
            default:


        }
        return true;

    }


    public int delByNumberList(List<String> numberList) {
        return baseDataValueDao.delByNumberList(numberList);
    }

    public int delByBaseDataNumber(List<String> baseDataNumberList) {
        return baseDataValueDao.delByBaseDataNumberList(baseDataNumberList);
    }

}

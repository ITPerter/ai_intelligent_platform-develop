package com.q.ai.mvc.service;

import com.q.ai.component.session.RequestContext;
import com.q.ai.component.util.IdGenerator;
import com.q.ai.mvc.dao.TFAQLibDao;
import com.q.ai.mvc.dao.po.TFAQLib;

import com.q.ai.mvc.dao.po.TSkillqalib;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

/**
 * 操作知识库
 */
@Service
public class TFAQLibService {
    @Resource
    private RequestContext requestContext;
    @Resource
    private TFAQLibDao tfaqLibDao;

    /**
     * 获取知识库列表
     * @return
     */
    public List<TFAQLib> getTFAQLibList() {
        List<TFAQLib> tfaqLibList = tfaqLibDao.getTFAQLibList();
        return tfaqLibList;
    }

    public List<TFAQLib> getFAQLibIdBySkillId(Long id){
        List<TFAQLib> faqLibIdBySkillId = tfaqLibDao.getFAQLibIdBySkillId(id);
        return faqLibIdBySkillId;
    }

    public int addLib(TFAQLib tfaqLib) {
        Long id = IdGenerator.generateId();
        System.out.println(id);
        tfaqLib.setFID(id);
        tfaqLib.setFCreateTime(LocalDateTime.now());
        tfaqLib.setFUserID(Long.valueOf(requestContext.getSession().getUserId()));
        int i = tfaqLibDao.addLib(tfaqLib);
        return i;
    }
}

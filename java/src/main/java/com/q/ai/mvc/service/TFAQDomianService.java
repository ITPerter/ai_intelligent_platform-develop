package com.q.ai.mvc.service;

import com.q.ai.component.session.RequestContext;
import com.q.ai.component.util.IdGenerator;
import com.q.ai.mvc.dao.TFAQDomainDao;
import com.q.ai.mvc.dao.po.TFAQDomain;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.time.LocalDateTime;
import java.util.List;

@Service
public class TFAQDomianService {
    @Resource
    private TFAQDomainDao tfaqDomainDao;
    @Resource
    private RequestContext requestContext;
    public List<TFAQDomain> getFAQDomainBySkillNumber(String number) {
        List<TFAQDomain> faqDomainBySkillNumber = tfaqDomainDao.getFAQDomainBySkillNumber(number);
        return faqDomainBySkillNumber;
    }

    public int addFAQDomain(TFAQDomain tfaqDomain) {
        tfaqDomain.setFUserID(Long.valueOf(requestContext.getSession().getUserId()));
        tfaqDomain.setFID(IdGenerator.generateId());
        tfaqDomain.setFCreateTime(LocalDateTime.now());
        int i = tfaqDomainDao.addFAQDomain(tfaqDomain);
        return i;
    }

    public TFAQDomain getDomainByNumber(String number) {
        TFAQDomain tfaqDomain = tfaqDomainDao.getDomainByNumber(number);
        return tfaqDomain;
    }
}

package com.q.ai.mvc.service;

import com.q.ai.mvc.dao.TFAQDomainDao;
import com.q.ai.mvc.dao.po.TFAQDomain;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

@Service
public class TFAQDomianService {
    @Resource
    private TFAQDomainDao tfaqDomainDao;
    public List<TFAQDomain> getFAQDomainBySkillNumber(String number) {
        List<TFAQDomain> faqDomainBySkillNumber = tfaqDomainDao.getFAQDomainBySkillNumber(number);
        return faqDomainBySkillNumber;
    }

    public int addFAQDomain(TFAQDomain tfaqDomain) {
        int i = tfaqDomainDao.addFAQDomain(tfaqDomain);
        return i;
    }

    public TFAQDomain getDomainByNumber(String number) {
        TFAQDomain tfaqDomain = tfaqDomainDao.getDomainByNumber(number);
        return tfaqDomain;
    }
}

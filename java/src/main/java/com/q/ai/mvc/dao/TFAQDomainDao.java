package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.TFAQDomain;

import java.util.List;

public interface TFAQDomainDao {
    public List<TFAQDomain> getFAQDomainBySkillNumber(String number);

    int addFAQDomain(TFAQDomain tfaqDomain);

    TFAQDomain getDomainByNumber(String number);
}

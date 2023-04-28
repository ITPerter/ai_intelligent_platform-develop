package com.q.ai.mvc.dao;

import com.q.ai.mvc.dao.po.TFAQLib;
import com.q.ai.mvc.dao.po.TSkillqalib;

import java.util.List;

public interface TFAQLibDao {
    /**
     * 获取知识库列表
     * @return
     */
    List<TFAQLib> getTFAQLibList();

    /**
     * 通过技能id获取知识库
     * @param id
     * @return
     */
    List<TFAQLib> getFAQLibIdBySkillId(Long id);

    int addLib(TFAQLib tfaqLib);
}

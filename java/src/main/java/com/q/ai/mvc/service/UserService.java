package com.q.ai.mvc.service;

import com.mysql.cj.util.StringUtils;
import com.q.ai.biz.entity.Session;
import com.q.ai.component.session.SessionUtil;
import org.apache.http.HttpResponse;
import org.springframework.stereotype.Service;
import javax.annotation.Resource;

@Service
public class UserService {

    @Resource(name = "mysql")
    private SessionUtil sessionUtil;

    // 登录业务
    public String logIn(String sessionId, HttpResponse httpResponse, String userName, String passwd) {

        return "";
    }

    // 退出业务
    public void logout(Session session) {
        // 判断
        if(null != session && !StringUtils.isNullOrEmpty(session.getId())){
            sessionUtil.delById(session.getId());
        }
    }
}

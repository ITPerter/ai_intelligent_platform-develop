package com.q.ai.mvc.controller;

/**
 * 词槽更新接口+运行时接口
 */

import com.q.ai.biz.entity.Session;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.q.ai.component.session.RequestContext;
import com.q.ai.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;


@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @Autowired
    RequestContext requestContext;


    @Value("#{${user_map}}")
    private Map<String, String> userMap;

    /**
     * 外部系统同步词槽值的接口
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/login", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Auth(AUTH_TYPE.OFF)
    public Rs login(@RequestBody ParamJSON param) {
        String userName = param.getMustString("userName");
        String password = param.getMustString("password");
        Integer userId = param.getInteger("userId");

        if (!(userMap.containsKey(userName) && userMap.get(userName).equals(password))) {
            throw new RsException("用户名密码错误，登录失败", 403);
        }

        Session session = requestContext.getSession();
        session.setUserId(userId == null || userId == 0 ? 100000 : userId);
        // 存储session
        requestContext.save(session);
        Map<String, Object> user = new HashMap<>();
        user.put("userName", userName);
        user.put("userId", session.getUserId());
        return Rs.buildData(user);
    }

    @GetMapping(value = "/logout")
    @Auth(AUTH_TYPE.OFF)
    public Rs logout() {

        userService.logout(requestContext.getSession());
        return Rs.buildOK("退出登录成功");

    }


}

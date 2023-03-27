package com.q.ai.mvc.controller;

import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
import com.mysql.cj.util.StringUtils;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.APP;
import com.q.ai.component.session.RequestContext;
import com.q.ai.biz.entity.Session;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.mvc.service.SlotService;
import com.q.ai.mvc.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;
import java.util.UUID;


/**
 * 这个controller下的接口不写入cookie,其他的接口必须带?token才不写cookie
 */
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/auth")
public class AuthController {

    @Autowired
    private UserService userService;

    @Autowired
    private RequestContext requestContext;

    @Autowired
    SlotService slotService;

    @Value("#{${user_map}}")
    private Map<String, String> userMap;

    @Autowired
    @Value("{sync_base_data_secret:8c10c8bb76f948b388dbe797d3cbefbb}")
    private String baseDataSecret;

    @RequestMapping(value = "/getUserToken", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Auth(AUTH_TYPE.OFF)
    public Rs getUserToken(@RequestBody ParamJSON param) {
        String userName = param.getMustString("userName");
        String password = param.getMustString("password");

        if (!(userMap.containsKey(userName) && userMap.get(userName).equals(password))) {
            throw new RsException("用户名密码错误，登录失败", 403);
        }

        Session session = requestContext.getSession();
        session.setUserId(30000);
        requestContext.save(session);
        Map<String, Object> user = new HashMap<>();
        user.put("userName", userName);
        user.put("userId", session.getUserId());
        user.put("chatToken", session.getId());
        return Rs.buildData(user);
    }


    @RequestMapping(value = "/getAppToken", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Auth(AUTH_TYPE.OFF)
    public Rs getAppToken(@RequestBody ParamJSON param) {
        APP app = APP.valueOf(param.getMustString("app"));
        String nonce = param.getMustString("nonce");
        long timestamp = param.getMustLong("timestamp");
        //sha256(appType+secret+timestamp);

        if (APP.BASE_DATA.equals(app)) {
            String secret = baseDataSecret;

            if (StringUtils.isNullOrEmpty(secret)) {
                throw new RsException("要操作的app(" + app + ")尚未启用token授权。");
            }

            String nonceOrigin = app + secret + timestamp;
            String nonceRight = Hashing.sha256().hashString(nonceOrigin, Charsets.UTF_8).toString();
            if (!nonceRight.equals(nonce)) {//正确，生成token
                String token = UUID.randomUUID().toString();
                requestContext.init(token);
                Session session = requestContext.getSession();
                session.setApp(app);
                Map<String, Object> dataMap = new HashMap<>();
                dataMap.put("appToken", token);
                return Rs.buildData(dataMap);
            }
        }
        throw new RsException("获取app token失败");
    }

    @GetMapping(value = "/getNonce")
    @Auth(AUTH_TYPE.OFF)
    public Rs getNonce(@RequestBody ParamJSON param) {
        String app = param.getMustString("app");
        String secret = param.getMustString("secret");
        long timestamp = param.getMustLong("timestamp");
        String nonceOrigin = app + secret + timestamp;
        return Rs.buildData(Hashing.sha256().hashString(nonceOrigin, Charsets.UTF_8).toString());
    }


}

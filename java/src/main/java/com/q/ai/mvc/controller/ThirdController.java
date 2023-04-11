package com.q.ai.mvc.controller;

/**
 * 词槽更新接口+运行时接口
 */

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.biz.entity.Session;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.APP;
import com.q.ai.component.session.RequestContext;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.po.BaseData;
import com.q.ai.mvc.dao.po.BaseDataValue;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.service.BaseDataService;
import com.q.ai.mvc.service.RobotService;
import com.q.ai.mvc.service.WordSlotService;
import com.q.ai.mvc.service.BaseDataValueService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.*;

import java.io.UnsupportedEncodingException;
import java.net.URLDecoder;
import java.util.List;


//@Api(tags = "三方交互相关")
//@ApiResponses({@ApiResponse(code = 200, message = "响应成功", response = Rs.class)})
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/third")
public class ThirdController {

    @Autowired
    private RobotService robotService;
    @Autowired
    private WordSlotService wordSlotService;
    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BaseDataValueService baseDataValueService;
    @Autowired
    private RequestContext requestContext;

    @Value("{sync_base_data_secret:8c10c8bb76f948b388dbe797d3cbefbb}")
    private String baseDataSecret;

    /**
     * chat接口
     *
     * @param param
     * @return
     */
    //@ApiOperation(value = "对话接口", notes = "对话接口", httpMethod = "POST",response = Rs.class)
    //example暂时没有被knife4j支持,使用value临时替用
    @ApiImplicitParam(name = "param", value = "{\"robotId\":13,\"chatMsg\":\"新优流\"}", required = true, paramType = "body", dataType = "json", example = "{\"robotId\":14,\"chatMsg\":\"新优流\"}")
    @RequestMapping(value = "/chat", produces = "application/json;charset=UTF-8")
    @ApiResponses(@ApiResponse(code = 200, message = ""))
    @ResponseBody
//    @Auth(AUTH_TYPE.USER_TOKEN)
    public Rs chat(@RequestBody ParamJSON param) {
        int robotId = param.getMustInteger("robotId");
        String chatMsg = param.getMustString("chatMsg");
        if (StringUtils.isEmpty(chatMsg)) {
            throw new RsException("说点啥吧？");
        }
        Object object = robotService.chat(robotId, chatMsg);
        return Rs.buildData(object);
    }

    /**
     * chat接口2
     *
     * @param param
     * @return
     */
    //@ApiOperation(value = "对话接口", notes = "对话接口", httpMethod = "POST",response = Rs.class)
    //example暂时没有被knife4j支持,使用value临时替用
    @ApiImplicitParam(name = "param", value = "{\"robotId\":13,\"chatMsg\":\"新优流\"}", required = true, paramType = "body", dataType = "json", example = "{\"robotId\":14,\"chatMsg\":\"新优流\"}")
    @RequestMapping(value = "/chat_v2", produces = "application/json;charset=UTF-8")
    @ApiResponses(@ApiResponse(code = 200, message = ""))
    @ResponseBody
//    @Auth(AUTH_TYPE.USER_TOKEN)
    public Rs chat_v2(@RequestBody ParamJSON param) {
        int robotId = param.getMustInteger("robotId");
        String chatMsg = param.getMustString("chatMsg");
        String userId = param.getMustString("userId");
        String token = param.getMustString("token");
        JSONObject data = param.getJsonObject("data");
        if (StringUtils.isEmpty(chatMsg)) {
            throw new RsException("说点啥吧？");
        }

        Object object = robotService.chat2(robotId, chatMsg,userId,token,data);
        return Rs.buildData(object);
    }

    /**
     * 外部系统同步词槽值的接口
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/addBaseDataValueList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Auth(AUTH_TYPE.APP_TOKEN)
    public Rs addBaseDataValueList(@RequestBody ParamJSON param) {
        JSONArray baseDataValueJson = param.getMustJsonArray("baseDataValueList");
        String baseDataNumber = param.getMustString("baseDataNumber");
        //取值：1.删除旧的插入新的：replaceAll
        String operator = param.getMustString("operator");

        Session session = requestContext.getSession();
        APP app = session.getApp();
        if (!session.getApp().equals(APP.BASE_DATA)) {
            throw new RsException("该接口只支持BASE_DATA类型Token.");
        }

        switch (operator) {
            case "replaceAll":
                BaseData baseData = baseDataService.getByNumber(baseDataNumber);
                if (null == baseData) {
                    throw new RsException("应用（baseData基础资料）不存在");
                }

                List<BaseDataValue> baseDataValueList = JSONObject.parseArray(baseDataValueJson.toJSONString(), BaseDataValue.class);
                if (null == baseDataValueList || baseDataValueList.size() == 0) {
                    throw new RsException("baseDataValueList的值为空");
                }
                baseDataValueService.replaceAll(baseDataNumber, baseDataValueList);
                return Rs.buildOK("成功（" + operator + "）" + baseDataValueList.size() + "条");

            case "addIncrement":
                throw new RsException("需要就加此功能，暂不支持的操作");
                // break;
            default:

        }
        throw new RsException("暂不支持的操作");


    }


    /**
     * 外部系统同步词槽值的接口
     *
     * @param param
     * @return
     */
    @RequestMapping(value = "/addBaseDataValueListByUrlEncode", produces = "application/json;charset=UTF-8")
    @ResponseBody
    @Auth(AUTH_TYPE.APP_TOKEN)
    public Rs addBaseDataValueListByUrlEncode(@RequestBody String param) throws UnsupportedEncodingException {

        ParamJSON paramJson = JSONObject.parseObject(URLDecoder.decode(param, "UTF-8"), ParamJSON.class);
        JSONArray baseDataValueJson = paramJson.getMustJsonArray("baseDataValueList");
        String baseDataNumber = paramJson.getMustString("baseDataNumber");
        Session session = requestContext.getSession();
        if (session == null || !session.getApp().equals(APP.BASE_DATA)) {
            throw new RsException("该接口只支持BASE_DATA类型Token.");
        }

        BaseData baseData = baseDataService.getByNumber(baseDataNumber);
        if (null == baseData) {
            throw new RsException("应用（baseData基础资料）不存在");
        }

        List<BaseDataValue> baseDataValueList = JSONObject.parseArray(baseDataValueJson.toJSONString(), BaseDataValue.class);
        if (null == baseDataValueList || baseDataValueList.size() == 0) {
            throw new RsException("baseDataValueList的值为空");
        }
        baseDataValueService.replaceAll(baseDataNumber, baseDataValueList);
        return Rs.buildOK("成功" + baseDataValueList.size() + "条");
    }


    /**
     * @param param {"createTime":"2020-10-24T15:07:55","creator":0,"des":"受雇佣的员工","name":"员工","number":"employee"}
     * @return {"code":0,"msg":"基础资料新建成功","trackId":"1603728258779"}
     */
    //@ApiOperation(value = "添加基础资料",notes = "给系统中添加基础资料",httpMethod = "POST")
    @PostMapping("/addBaseData")
    @ResponseBody
    @Auth(AUTH_TYPE.APP_TOKEN)
    public Rs addBaseData(@RequestBody ParamJSON param) {
        BaseData baseData = param.toJavaObject(BaseData.class);
        baseData.setId(0);
        baseDataService.save(baseData);
        return Rs.buildOK("基础资料新建成功");

    }


}

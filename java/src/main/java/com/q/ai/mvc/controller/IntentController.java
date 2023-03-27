package com.q.ai.mvc.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.mvc.dao.po.Intent;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.service.IntentService;
import com.q.ai.mvc.service.RobotService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Auth(AUTH_TYPE.SESSION)
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/intent")
public class IntentController {

    @Autowired
    private IntentService intentService;
    @Autowired
    private RobotService robotService;


    @GetMapping("/getById")
    public Rs getById(@RequestParam int id) {
        Intent intent = intentService.getById(id);
        if (null != intent) {
            return Rs.buildData(intent);
        }
        return Rs.buildErr("意图不存在");
    }


    /**
     * 获取意图列表数据
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getList")
    public Rs getList(@RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Intent> intents = intentService.getList(page);
        return Rs.buildList(intents, page);
    }

    @GetMapping("/getListByRobotId")
    public Rs getListByRobotId(@RequestParam int robotId, @RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Intent> intents = intentService.getListByRobotId(robotId, page);
        return Rs.buildList(intents, page);
    }


    @RequestMapping(value = "/addIntentIdList2Robot", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs addIntentIdList2Robot(@RequestBody ParamJSON param) {
        JSONArray intentJson = param.getMustJsonArray("intentIdList");
        Integer robotId = param.getMustInteger("robotId");

        if (null == robotService.getById(robotId)) {
            throw new RsException("意图不存在或已被删除");
        }
        List<Integer> intentIdList = JSONObject.parseArray(intentJson.toJSONString(), Integer.class);

        intentService.add2Robot(intentIdList, robotId);
        return Rs.buildOK("添加成功");
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs save(@RequestBody ParamJSON param) {
        Intent intent = param.getMustJavaObject("intent", Intent.class);
        Integer robotId = param.getInteger("robotId");
        intentService.saveWithRobotId(intent, robotId);
        return Rs.buildData(intent);
    }

    @RequestMapping(value = "/removeIntentIdsFromRobot", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs removeIntentIdsFromRobot(@RequestBody ParamJSON param) {
        JSONArray intentJson = param.getMustJsonArray("intentIdList");
        Integer robotId = param.getMustInteger("robotId");

        List<Integer> intentIdList = JSONObject.parseArray(intentJson.toJSONString(), Integer.class);

        intentService.removeIntentIdsFromRobot(intentIdList, robotId);
        return Rs.buildOK("移除成功");
    }

    @GetMapping(value = "/delByIds")
    public Rs delByIds(@RequestParam String ids) {
        String[] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idArray) {
            idList.add(Integer.valueOf(s));
        }
        intentService.delByIdList(idList);
        return Rs.buildOK("删除成功");
    }


}

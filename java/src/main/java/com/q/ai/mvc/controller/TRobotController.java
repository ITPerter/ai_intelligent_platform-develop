package com.q.ai.mvc.controller;


import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.po.Robot;
import com.q.ai.mvc.dao.po.TRobot;
import com.q.ai.mvc.service.NlpService;
import com.q.ai.mvc.service.TRobotService;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/tRobot")
public class TRobotController {

    @Resource
    private TRobotService tRobotService;
    @Autowired
    private NlpService nlpService;

    /**
     * 通过机器人id获取机器人信息
     * @param id
     * @return
     */
    @GetMapping("/getRobotById")
    public Rs getRobotById(@RequestParam int id) {
        TRobot tRobot = tRobotService.getRobotById(id);
        if (null != tRobot) {
            return Rs.buildData(tRobot);
        }
        return Rs.buildErr("机器人不存在");
    }

    /**
     * 获取机器人列表
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getRobotList")
    public Rs getRobotList(@RequestParam int number,@RequestParam int size) {
        List<TRobot> robotList = tRobotService.getRobotList(number, size);
        int total = tRobotService.getRobotCount();
        Page page = new Page();
        page.setTotal(total);
        page.setNumber(number);
        page.setSize(size);
        return Rs.buildList(robotList, page);
    }

    /**
     * 更新/添加机器人
     * @param paramJSON
     * @return
     */
    @PostMapping("/save")
    public Rs save(@RequestBody ParamJSON paramJSON){
        TRobot tRobot = paramJSON.toJavaObject(TRobot.class);
        System.out.println(tRobot.toString());
        int i = tRobotService.save(tRobot);
        System.out.println("------------------------------" + i);
        if (i == 0){
            return Rs.buildErr("操作失败");
        }
        return Rs.buildData(i,"操作成功");
    }

    /**
     * 通过机器人id训练
     * @param id    机器人id
     * @return
     */
    @GetMapping("/trainRobot")
    public Rs train(@RequestParam int id) {
        boolean success = nlpService.train(id);
        // 这样子是不行的，有待修改
        if (!success) {
            return Rs.buildErr("训练失败");
        }
        return Rs.buildOK("训练成功");
    }

    /**
     * 通过ids删除机器人
     * @param ids
     * @return
     */
    @DeleteMapping("/deleteRobotIds")
    public Rs deleteRobotIds(@RequestBody String ids){
        String[] idArray = ids.split(",");
        List<Long> robotIdList = new ArrayList<>();
        for (String s : idArray) {
            robotIdList.add(Long.valueOf(s));
        }
        int count = tRobotService.deleteRobotIds(robotIdList);
        if (count == 0){
            return Rs.buildErr("删除机器人失败");
        }
        return Rs.buildData(count,"删除了"+count+"条机器人数据");
    }
}

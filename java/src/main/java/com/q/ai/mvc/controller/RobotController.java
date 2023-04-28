package com.q.ai.mvc.controller;


import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.mvc.dao.po.Robot;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.po.TRobot;
import com.q.ai.mvc.service.NlpService;
import com.q.ai.mvc.service.RobotService;
import io.swagger.annotations.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

//@Api(tags="机器人接口")
//@ApiResponses({@ApiResponse(code = 200,message="响应成功",response=Rs.class)})
//@Auth(AUTH_TYPE.SESSION)
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/robot")
public class RobotController {

    @Autowired
    private RobotService robotService;
    @Autowired
    private NlpService nlpService;


    @ApiOperation(value = "获取机器人id", httpMethod = "GET")
    @ApiResponses(
            @ApiResponse(code = 200, message = "return",
                    response = Rs.class,
                    reference = "C:/hello.json",
                    responseContainer = "list",
                    examples = @Example({@ExampleProperty(mediaType = "id", value = "机器人id")})))
    @GetMapping("/getById")
    public Rs getById(@ApiParam("机器人id") @RequestParam int id) {
        Robot robot = robotService.getById(id);
        if (null != robot) {
            return Rs.buildData(robot);
        }
        return Rs.buildErr("机器人不存在");
    }


    @GetMapping("/getList")
    public Rs getList(@RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Robot> robots = robotService.getList(page);
        page = robotService.getPage(page);
        return Rs.buildList(robots, page);
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs save(@RequestBody ParamJSON param) {
        Robot robot = param.toJavaObject(Robot.class);
        robotService.save(robot);
        return Rs.buildData(robot);
    }

    @GetMapping(value = "/train")
    public Rs train(@RequestParam int id) {
        boolean success = nlpService.train(id);
        if (!success) {
            return Rs.buildErr("训练失败");
        }
        return Rs.buildOK("训练成功");
    }

    @GetMapping(value = "/delByIds")
    public Rs delByIds(@RequestParam String ids) {
        String[] idArray = ids.split(",");
        List<Integer> robotIdList = new ArrayList<>();
        for (String s : idArray) {
            robotIdList.add(Integer.valueOf(s));
        }
        int count = robotService.delByIdList(robotIdList);

        return Rs.buildOK("成功删除" + count + "条");
    }


}

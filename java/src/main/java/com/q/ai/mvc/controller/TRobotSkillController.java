package com.q.ai.mvc.controller;

import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.po.TSkill;
import com.q.ai.mvc.service.TRobotService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/robotSkill")
public class TRobotSkillController {

    @Resource
    private TRobotService tRobotService;

    /**
     * 通过机器人id获取机器人对应的技能
     * @param id
     * @return
     */
    @GetMapping("getIntentionByRobotId")
    public Rs getIntentionByRobotId(@RequestParam Long id){
        List<TSkill> tSkillList = tRobotService.getSkillByRobotId(id);
        return Rs.buildData(tSkillList,"查询成功");
    }
}

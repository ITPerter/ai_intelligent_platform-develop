package com.q.ai.mvc.controller;

import com.q.ai.component.io.Page;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.IntentionDao;
import com.q.ai.mvc.dao.TSkillDao;
import com.q.ai.mvc.dao.po.Intention;
import com.q.ai.mvc.dao.po.TSkill;
import com.q.ai.mvc.dao.po.WordSlot;
import com.q.ai.mvc.service.IntentionService;
import com.q.ai.mvc.service.TSkillService;
import com.q.ai.mvc.service.WordSlotService;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import java.util.List;

/**
 * 技能控制层
 */
@RestController
@RequestMapping("/tSkill")
public class TSkillController {

    @Resource
    private WordSlotService wordSlotService;

    @Resource
    private TSkillService tSkillService;

    @Resource
    private IntentionService intentionService;

    @GetMapping("/getWordSlotByIntentionId")
    public Rs getWordSlotByIntentionId(@RequestParam Long id){
        List<WordSlot> wordSlots = wordSlotService.getWordSlotByIntentionId(id);
        return Rs.buildData(wordSlots);
    }

    /**
     * 通过id获取任务技能
     * @param id
     * @return
     */
    @GetMapping("/getTaskById")
    public Rs getById(@RequestParam int id){
        TSkill tSkill = tSkillService.getTaskById(id);
        return Rs.buildData(tSkill);
    }

    /**
     * 通过分页数据获取数据
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getTaskList")
    public Rs getTaskList(@RequestParam int number, @RequestParam int size) {
        List<TSkill> taskList = tSkillService.getTaskList(number, size);
        int count = tSkillService.getTaskCount();
        Page page = new Page();
        page.setNumber(number);
        page.setSize(size);
        page.setTotal(count);
        return Rs.buildList(taskList,page);
    }

    /**
     * 通过任务id获取意图列表分页数据
     * @param id
     * @param number
     * @param limit
     * @return
     */
    @GetMapping("/getIntentionByTaskId")
    public Rs getIntentionByTaskId(@RequestParam("id") int id,@RequestParam("number") int number,@RequestParam("limit") int limit) {
        Page page = new Page();
        page.setNumber(number);
        page.setSize(limit);
        int total = intentionService.getCountByTaskId(id);
        page.setTotal(total);
        List<Intention> intentionList = intentionService.getIntentionByTaskId(id, number, limit);
        return Rs.buildList(intentionList,page);
    }
}

package com.q.ai.mvc.controller;

import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.po.Intention;
import com.q.ai.mvc.dao.po.TSkill;
import com.q.ai.mvc.dao.po.WordSlot;
import com.q.ai.mvc.service.IntentionService;
import com.q.ai.mvc.service.SkillIntentionSlotService;
import com.q.ai.mvc.service.TSkillService;
import com.q.ai.mvc.service.WordSlotService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 技能控制层
 */
@RestController
@RequestMapping("/tSkill")
public class TSkillController {

    @Resource
    private SkillIntentionSlotService skillIntentionSlotService;

    @Resource
    private WordSlotService wordSlotService;

    @Resource
    private TSkillService tSkillService;

    @Resource
    private IntentionService intentionService;


    /**
     * 更新问答型技能
     * @param paramJSON
     * @return
     */
    @PostMapping("updateQuestionAnsweringSkill")
    public Rs updateQuestionAnsweringSkill(@RequestBody ParamJSON paramJSON){
        TSkill tSkill = paramJSON.toJavaObject(TSkill.class);
        int i = tSkillService.updateQuestionAnsweringSkill(tSkill);
        if (i != 0){
            return Rs.buildData(1,"问答型数据修改成功");
        }
        return Rs.buildErr("添加失败");
    }

    /**
     * 通过id集合删除问答型技能数据
     * @param idList
     * @return
     */
    @DeleteMapping("/deleteQuestionAnsweringSkill")
    public Rs deleteQuestionAnsweringSkills(@RequestBody String idList){
        String[] idArray = idList.split(",");
        List<Long> skillIdList = new ArrayList<>();
        for (String s : idArray) {
            skillIdList.add(Long.valueOf(s));
        }
        int i = tSkillService.deleteQuestionAnsweringSkills(skillIdList);
        if (i == 0) {
            return Rs.buildErr("删除失败");
        }
        return Rs.buildData(i,"删除成功");
    }

    /**
     * 添加问答技能
     * @param tSkill
     * @return
     */
    @PutMapping("/addQuestionAnsweringSkill")
    public Rs addQuestionAnsweringSkill(@RequestBody ParamJSON tSkill){
        TSkill tSkill1 = tSkill.toJavaObject(TSkill.class);
        int i = tSkillService.addQuestionAnsweringSkill(tSkill1);
        if (i != 0){
            return Rs.buildData(i,"问答型添加成功");
        }else {
            return Rs.buildErr("问答型添加失败");
        }
    }

    /**
     * 获取问大型技能列表
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getQuestionAnsweringSkills")
    public Rs getQuestionAnsweringSkills(@RequestParam int number, @RequestParam int size) {
        List<TSkill> QuestionAnsweringSkills = tSkillService.getQuestionAnsweringSkills(number, size);
        int count = tSkillService.getQuestionAnsweringCount();
        Page page = new Page();
        page.setNumber(number);
        page.setSize(size);
        page.setTotal(count);
        return Rs.buildList(QuestionAnsweringSkills,page);
    }


    @PostMapping("/insertTaskSkill")
    public Rs insertTaskSkill(@RequestBody ParamJSON paramJSON) {
        TSkill tSkill = paramJSON.toJavaObject(TSkill.class);
        int i = tSkillService.insertTaskSkill(tSkill);
        if (i == 0) {
            return Rs.buildErr("添加数据失败");
        }
        return Rs.buildOK("添加数据成功");
    }


    /**
     * 更新任务技能
     * @param param
     * @return
     */
    @PutMapping("updateTaskById")
    public Rs updateTaskById(@RequestBody ParamJSON param) {
        TSkill tSkill = param.toJavaObject(TSkill.class);
        System.out.println(tSkill.toString());
        int i = tSkillService.save(tSkill);
        if (i == 0) {
            return Rs.buildErr("修改数据失败");
        }else {
            return Rs.buildOK("成功修改数据");
        }
    }

    /**
     * 通过意图id获取对应词槽
     * @param id    意图id
     * @return
     */
    @GetMapping("/getWordSlotByIntentionId")
    public Rs getWordSlotByIntentionId(@RequestParam Long id){
        List<WordSlot> wordSlots = wordSlotService.getWordSlotByIntentionId(id);
        return Rs.buildData(wordSlots);
    }

    /**
     * 通过技能编号获取意图及意图对应的词槽
     * @param number
     * @return
     */
    @GetMapping("/getIntentionAndSlotByNumber")
    public Rs getTaskByNumber(@RequestParam String number) {
        Long id = tSkillService.getSkillId(number);
        List<Intention> intentions = skillIntentionSlotService.getIntentions(id);
        for (Intention intention: intentions) {
            List<WordSlot> wordSlots = wordSlotService.getWordSlotByIntentionId(intention.getId());
            List<String> slots = new ArrayList<>();
            for (WordSlot wordSlot:wordSlots) {
                slots.add(wordSlot.getName());
            }
            intention.setSlotList(slots);
        }
        return Rs.buildData(intentions);
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
     * @param number    第几页
     * @param size      每页多少数据
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
     * @param id    技能id
     * @param number    第几页
     * @param limit     每页多少数据
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

    /**
     * 通过id列表删除数据
     * @param idList
     * @return
     */
    @DeleteMapping("/deleteTaskByIdList")
    public Rs deleteTaskById(@RequestBody String idList) {
        String[] idArray = idList.split(",");
        List<Integer> skillIdList = new ArrayList<>();
        for (String s : idArray) {
            skillIdList.add(Integer.valueOf(s));
        }
        int i = tSkillService.deleteTaskById(skillIdList);
        if (i == 0) {
            return Rs.buildOK("删除失败");
        }
        return Rs.buildOK("删除成功");
    }
}

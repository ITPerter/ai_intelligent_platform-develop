package com.q.ai.mvc.controller;

import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.po.TFAQLib;
import com.q.ai.mvc.service.TFAQLibService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/lib")
public class TFAQLibController {
    @Resource
    private TFAQLibService tfaqLibService;

    @GetMapping("/getTFAQLibList")
    public Rs getTFAQLibList(){
        List<TFAQLib> tfaqLibList = tfaqLibService.getTFAQLibList();
        return Rs.buildData(tfaqLibList);
    }

    @GetMapping("/getFAQLibIdBySkillId")
    public Rs getFAQLibIdBySkillId(@RequestParam Long id){
        List<TFAQLib> faqLibIdBySkillId = tfaqLibService.getFAQLibIdBySkillId(id);
        return Rs.buildData(faqLibIdBySkillId,"查询成功");
    }

    @PostMapping("/addLib")
    public Rs addLib(@RequestBody ParamJSON paramJSON){
        TFAQLib tfaqLib = paramJSON.toJavaObject(TFAQLib.class);
        int i = tfaqLibService.addLib(tfaqLib);
        return i > 0 ? Rs.buildData(i,"添加成功"): Rs.buildData(i,"添加失败");
    }

}

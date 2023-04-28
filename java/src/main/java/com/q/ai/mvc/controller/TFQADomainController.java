package com.q.ai.mvc.controller;

import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.mvc.dao.po.TFAQDomain;
import com.q.ai.mvc.service.TFAQDomianService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.List;

@RestController
@RequestMapping("/domain")
public class TFQADomainController {

    @Resource
    private TFAQDomianService tfaqDomianService;

    /**
     * 通过问答技能编码查看领域数据
     * @param number
     * @return
     */
    @GetMapping("/getFAQDomainBySkillNumber")
    public Rs getFAQDomainBySkillNumber(@RequestParam String number){
        System.out.println(number);
        List<TFAQDomain> tfaqDomainList = tfaqDomianService.getFAQDomainBySkillNumber(number);
        return Rs.buildData(tfaqDomainList);
    }

    @PutMapping("/")
    public Rs addFAQDomain(@RequestBody ParamJSON paramJSON){
        TFAQDomain tfaqDomain = paramJSON.toJavaObject(TFAQDomain.class);
        int i = tfaqDomianService.addFAQDomain(tfaqDomain);
        return i <= 0 ? Rs.buildData(i,"添加成功") : Rs.buildData(1,"添加失败");
    }

    @GetMapping("/getDomainByNumber")
    public Rs getDomainByNumber(@RequestParam String number){
        TFAQDomain tfaqDomain = tfaqDomianService.getDomainByNumber(number);
        return Rs.buildData(tfaqDomain);
    }
}

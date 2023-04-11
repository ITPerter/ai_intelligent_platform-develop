package com.q.ai.mvc.controller;


import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.mvc.dao.po.Sample;
import com.q.ai.mvc.dao.po.SampleItem;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.vo.SampleVo;
import com.q.ai.mvc.service.SampleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Auth(AUTH_TYPE.SESSION)
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/sample")
public class SampleController {


    @Autowired
    private SampleService sampleService;

    /**
     * 个人觉得更标准，更优雅的做法，前端去填充对应的数据
     *
     * @param intentId
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getListByIntentId2")
    public Rs getListByIntentId2(@RequestParam int intentId, @RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Sample> sampleList = sampleService.getListByIntentId(intentId, page);
        Map<Integer, List<SampleItem>> sampleId2SampleSlotMap = sampleService.getSampleItemListBySampleList(sampleList);
        Map<String, Object> rsMap = new HashMap<>();
        rsMap.put("sampleList", sampleList);
        rsMap.put("sampleListPage", page);
        rsMap.put("sampleId2SampleSlotMap", sampleId2SampleSlotMap);
        return Rs.buildData(rsMap);
    }

    /**
     * 应前端要求，使用VO将数据组装在一起
     *
     * @param intentId
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getListByIntentId")
    public Rs getListByIntentId(@RequestParam int intentId, @RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<SampleVo> sampleVoList = sampleService.getVoListByIntentId(intentId, page);
        return Rs.buildList(sampleVoList, page);
    }


    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs save(@RequestBody ParamJSON param) {
        Sample sample = param.toJavaObject(Sample.class);
        sampleService.save(sample);
        return Rs.buildData(sample);
    }

    @GetMapping(value = "/delByIds")
    public Rs delByIds(@RequestParam String ids) {
        String[] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idArray) {
            idList.add(Integer.valueOf(s));
        }
        sampleService.delByIdList(idList);
        return Rs.buildOK("删除成功");
    }

    ///////////////////////标注的接口


    @RequestMapping(value = "/saveSampleItem", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs saveSampleItem(@RequestBody ParamJSON param) {
        SampleItem sampleItem = param.toJavaObject(SampleItem.class);
        sampleService.saveSampleItem(sampleItem);
        return Rs.buildOK("标注成功");
    }

    @GetMapping(value = "/delBySampleItemId")
    public Rs delBySampleItemId(@RequestParam int sampleItemId) {
        sampleService.delSampleItemById(sampleItemId);
        return Rs.buildOK("移除标注成功");
    }


}

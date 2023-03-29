package com.q.ai.mvc.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.mvc.service.WordSlotService;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.service.IntentService;
import com.q.ai.mvc.service.BaseDataValueService;
import org.springframework.web.bind.annotation.*;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * 词槽相关接口
 */
@Auth(AUTH_TYPE.SESSION)
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/slot")
public class WordSlotController {

    @Resource
    private WordSlotService wordSlotService;
    @Resource
    private IntentService intentService;
    @Resource
    private BaseDataValueService baseDataValueService;

    /**
     * 通过id获取词槽数据
     * @param id
     * @return
     */
    @GetMapping("/getById")
    public Rs getById(@RequestParam int id) {
        Slot slot = wordSlotService.getById(id);
        if (null != slot) {
            return Rs.buildData(slot);
        }
        return Rs.buildErr("词槽不存在");
    }

    /**
     * 获取分页词槽数据
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getList")
    public Rs getList(@RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Slot> slots = wordSlotService.getList(page);
        return Rs.buildList(slots, page);
    }

    /**
     * 通过意图id获取词槽分页数据
     * @param intentId
     * @param number
     * @param size
     * @return
     */
    @GetMapping("/getListByIntentId")
    public Rs getListByIntentId(@RequestParam int intentId, @RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Slot> slots = wordSlotService.getListByIntentId(intentId, page);
        return Rs.buildList(slots, page);
    }

    /**
     *
     * @return
     */
    @GetMapping("/getSlotType")
    public Rs getSlotType() {
        return Rs.buildData(SLOT_TYPE.values());

    }

    @RequestMapping(value = "/addSlotIdList2Intent", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs addSlotIdList2Intent(@RequestBody ParamJSON param) {
        JSONArray slotJson = param.getMustJsonArray("slotIdList");
        Integer intentId = param.getMustInteger("intentId");

        if (null == intentService.getById(intentId)) {
            throw new RsException("意图不存在或已被删除");
        }

        List<Integer> slotIdList = JSONObject.parseArray(slotJson.toJSONString(), Integer.class);

        wordSlotService.add2Intent(slotIdList, intentId);
        return Rs.buildOK("添加成功");
    }

    /**
     * 交换顺序值（特殊情况，到最开始，到最后）
     * @param param
     * @return
     */
    @RequestMapping(value = "/changeSlotSeqAndGetList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs changeSlotSeqAndGetList(@RequestBody ParamJSON param) {
        Integer intentId = param.getMustInteger("intentId");
        Integer slotId = param.getMustInteger("slotId");
        Integer upOrDown = param.getMustInteger("upOrDown");
        Page page = param.getPage();
        List<Slot> slotList = wordSlotService.changSeqAndGetList(intentId, slotId, upOrDown, page);
        return Rs.buildList(slotList, page);
    }


    @RequestMapping(value = "/removeSlotIdsFromIntent", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs removeIntentIdsFromRobot(@RequestBody ParamJSON param) {
        JSONArray slotJson = param.getMustJsonArray("slotIdList");
        Integer intentId = param.getMustInteger("intentId");

        List<Integer> slotIdList = JSONObject.parseArray(slotJson.toJSONString(), Integer.class);

        wordSlotService.removeIntentIdsFromRobot(slotIdList, intentId);
        return Rs.buildOK("移除成功");
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs save(@RequestBody ParamJSON param) {
        Slot slot = param.getMustJavaObject("slot", Slot.class);
        Integer intentId = param.getInteger("intentId");
        wordSlotService.saveWithIntentId(slot, intentId);
        return Rs.buildData(slot);
    }

    @GetMapping(value = "/delByIds")
    public Rs delByIds(@RequestParam String ids) {
        String[] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idArray) {
            idList.add(Integer.valueOf(s));
        }
        wordSlotService.delByIdList(idList);
        return Rs.buildOK("删除成功");
    }



}

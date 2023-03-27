package com.q.ai.mvc.controller;


import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.enuz.SLOT_TYPE;
import com.q.ai.mvc.dao.po.BaseDataValue;
import com.q.ai.mvc.service.SlotService;
import com.q.ai.mvc.dao.po.Slot;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.service.IntentService;
import com.q.ai.mvc.service.BaseDataValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.ArrayList;
import java.util.List;

@Auth(AUTH_TYPE.SESSION)
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/slot")
public class SlotController {

    @Autowired
    private SlotService slotService;
    @Autowired
    private IntentService intentService;
    @Autowired
    private BaseDataValueService baseDataValueService;


    @GetMapping("/getById")
    public Rs getById(@RequestParam int id) {
        Slot slot = slotService.getById(id);
        if (null != slot) {
            return Rs.buildData(slot);
        }
        return Rs.buildErr("词槽不存在");
    }


    @GetMapping("/getList")
    public Rs getList(@RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Slot> slots = slotService.getList(page);
        return Rs.buildList(slots, page);
    }

    @GetMapping("/getListByIntentId")
    public Rs getListByIntentId(@RequestParam int intentId, @RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<Slot> slots = slotService.getListByIntentId(intentId, page);
        return Rs.buildList(slots, page);
    }

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

        slotService.add2Intent(slotIdList, intentId);
        return Rs.buildOK("添加成功");
    }

    /**
     * 交换顺序值（特殊情况，到最开始，到最后）
     *
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
        List<Slot> slotList = slotService.changSeqAndGetList(intentId, slotId, upOrDown, page);
        return Rs.buildList(slotList, page);
    }


    @RequestMapping(value = "/removeSlotIdsFromIntent", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs removeIntentIdsFromRobot(@RequestBody ParamJSON param) {
        JSONArray slotJson = param.getMustJsonArray("slotIdList");
        Integer intentId = param.getMustInteger("intentId");

        List<Integer> slotIdList = JSONObject.parseArray(slotJson.toJSONString(), Integer.class);

        slotService.removeIntentIdsFromRobot(slotIdList, intentId);
        return Rs.buildOK("移除成功");
    }

    @RequestMapping(value = "/save", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs save(@RequestBody ParamJSON param) {
        Slot slot = param.getMustJavaObject("slot", Slot.class);
        Integer intentId = param.getInteger("intentId");
        slotService.saveWithIntentId(slot, intentId);
        return Rs.buildData(slot);
    }

    @GetMapping(value = "/delByIds")
    public Rs delByIds(@RequestParam String ids) {
        String[] idArray = ids.split(",");
        List<Integer> idList = new ArrayList<>();
        for (String s : idArray) {
            idList.add(Integer.valueOf(s));
        }
        slotService.delByIdList(idList);
        return Rs.buildOK("删除成功");
    }



}

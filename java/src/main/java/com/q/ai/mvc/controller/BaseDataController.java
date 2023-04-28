package com.q.ai.mvc.controller;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.component.annotation.Auth;
import com.q.ai.component.enuz.AUTH_TYPE;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.po.BaseData;
import com.q.ai.mvc.dao.po.BaseDataValue;
import com.q.ai.mvc.service.BaseDataService;
import com.q.ai.mvc.service.BaseDataValueService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

//@Auth(AUTH_TYPE.SESSION)
@CrossOrigin(origins = {"*"})
@RestController
@RequestMapping("/baseData")
public class BaseDataController {

    @Autowired
    private BaseDataService baseDataService;
    @Autowired
    private BaseDataValueService baseDataValueService;

    @RequestMapping(value = "/saveBaseData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs saveBaseData(@RequestBody ParamJSON param) {
        BaseData baseData = param.toJavaObject(BaseData.class);
        baseDataService.save(baseData);
        return Rs.buildOK("基础资料保存成功");
    }

    @GetMapping("/getById")
    public Rs getById(@RequestParam int id) {
        BaseData baseData = baseDataService.getById(id);
        if (null != baseData) {
            return Rs.buildData(baseData);
        }
        return Rs.buildErr("基础资料不存在");
    }

    @GetMapping("/getByNumber")
    public Rs getByNumber(@RequestParam String number) {
        BaseData baseData = baseDataService.getByNumber(number);
        if (null != baseData) {
            return Rs.buildData(baseData);
        }
        return Rs.buildErr("基础资料不存在");
    }

    @RequestMapping(value = "/delByNumberList", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs delByNumberList(@RequestBody ParamJSON param) {
        JSONArray baseDataNumberListJson = param.getMustJsonArray("baseDataNumberList");

        List<String> baseDataIdList = JSONObject.parseArray(baseDataNumberListJson.toJSONString(), String.class);
        baseDataService.delByNumberList(baseDataIdList);

        return Rs.buildOK("删除基础资料成功");
    }


    @GetMapping("/getList")
    public Rs getList(@RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<BaseData> baseDataList = baseDataService.getList(page);
        return Rs.buildList(baseDataList, page);
    }

    /////////////////////////////值相关接口
    ////////////////////////////
    @GetMapping(value = {"/getValueListByDataBaseId", "/getValueListByDataBaseNumber"})
    public Rs getValueListByDataBaseId(@RequestParam String baseDataNumber, @RequestParam int number, @RequestParam int size) {
        Page page = new Page(number, size);
        List<BaseDataValue> baseDataValueList = baseDataValueService.getListByBaseDataNumber(baseDataNumber, page);
        return Rs.buildList(baseDataValueList, page);
    }

    @RequestMapping(value = "/saveValue2BaseData", produces = "application/json;charset=UTF-8")
    @ResponseBody
    public Rs saveValue2BaseData(@RequestBody ParamJSON param) {
        BaseDataValue baseDataValue = param.toJavaObject(BaseDataValue.class);
        String baseDataNumber = baseDataValue.getBaseDataNumber();
        if (null == baseDataService.getByNumber(baseDataNumber)) {
            throw new RsException("基础资料不存在或已被删除");
        }

        param.getMustString("number");
        param.getMustString("value");

        baseDataValueService.save(baseDataValue);
        return Rs.buildOK("保存成功");
    }


    @GetMapping(value = "/getValueByValueNumber")
    @ResponseBody
    public Rs getValueByValueNumber(@RequestParam String valueNumber) {
        return Rs.buildData(baseDataValueService.getByNumber(valueNumber));
    }

    /**
     * es的是文档型，分页逻辑与mysql不一致，这里暂时不需要分页，故不实现
     *
     * @param paramJson
     * @return
     */
    @RequestMapping(value = "/searchValue")
    @ResponseBody
    public Rs searchValue(@RequestBody ParamJSON paramJson) {
        String baseDataNumber = paramJson.getMustString("baseDataNumber");
        String value = paramJson.getMustString("value");
        Page page = paramJson.getPage();
        return Rs.buildList(baseDataValueService.search(baseDataNumber, value, page), page);
    }


    @RequestMapping(value = "/delValueByNumberList")
    @ResponseBody
    public Rs delValueByNumberList(@RequestBody ParamJSON param) {
        JSONArray baseDataValueNumberListJson = param.getMustJsonArray("numberList");

        List<String> baseDataValueIdList = JSONObject.parseArray(baseDataValueNumberListJson.toJSONString(), String.class);
        baseDataValueService.delByNumberList(baseDataValueIdList);

        return Rs.buildOK("删除基础资料值成功");
    }

}

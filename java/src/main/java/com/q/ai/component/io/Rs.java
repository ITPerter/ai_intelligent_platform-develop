package com.q.ai.component.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.q.ai.component.session.RequestContext;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;
import org.slf4j.MDC;

import java.util.ArrayList;
import java.util.Map;


/**
 *
 */
@ApiModel(value = "RsModel", description = "REST API返回的结果")
public class Rs {
    private static final int SUCCESS = 0;
    private static final int ERR = -1;


    @ApiModelProperty("请求追踪ID")
    private String trackId;
    @ApiModelProperty(name = "结果", value = "数据结果：" +
            "1.直接返回结果{\"name\":\"b1b299931\",\"time\":\"20200909\"} " +
            "2.返回列表 :{\"page\":{\"number\":2,\"size\":2,\"total\":9},\"list\":[{},{}]}")
    private Object data;
    @ApiModelProperty("错误码：0，成功")
    private int code;
    @ApiModelProperty("提示信息")
    private String msg;

    public Rs(int code, String msg, Object data) {
        this.code = code;
        this.msg = msg;
        this.data = data;
        this.trackId = MDC.get(RequestContext.TRACK_ID);
    }

    /**
     * 默认成功
     *
     * @return
     */
    public static Rs buildOK() {
        return new Rs(0, "成功", null);
    }

    public static Rs buildData() {
        return new Rs(SUCCESS, "成功", new Object());
    }

    /**
     * 成功：有说明的成功
     *
     * @param msg
     * @return
     */
    public static Rs buildOK(String msg) {
        return new Rs(SUCCESS, msg, null);
    }

    /**
     * 失败必须说明原因
     *
     * @param msg
     * @return
     */
    public static Rs buildErr(String msg) {
        return new Rs(ERR, msg, null);
    }

    public static Rs buildErr(Exception e) {
        return new Rs(ERR, e.getMessage(), null);
    }

    /**
     * 失败必须说明原因
     *
     * @param msg
     * @return
     */
    public static Rs buildErr(String msg, int code) {
        return new Rs(code, msg, null);
    }

    public static Rs buildErr(int code) {
        return new Rs(code, null, null);
    }

    /**
     * 成功：对象数据组装
     *
     * @param data
     * @return
     */
    public static Rs buildData(Object data) {
        return new Rs(SUCCESS, "成功", data);
    }


    /**
     * 成功：对象数据组装
     *
     * @param data
     * @return
     */
    public static Rs buildData(Object data, String msg) {
        return new Rs(SUCCESS, msg, data);
    }

    /**
     * 成功：对象数据组装
     *
     * @return
     */
    public static Rs buildEmptyList() {
        return buildList(new ArrayList<>(), new Page(1, 10, 0));
    }

    /**
     * 成功：分页列表数据组装
     * <p>
     * otherMap  list内如果是Map，其key必须是String类型的
     *
     * @param
     * @return
     */
    public static Rs buildList(Object list, Page page, Map<String, Object> otherMap) {
        JSONObject result = new JSONObject();

        if (otherMap != null)
            for (Map.Entry<String, Object> other : otherMap.entrySet()) {
                result.put(other.getKey(), other.getValue());
            }
        result.put("list", list);
        result.put("page", page);
        return new Rs(SUCCESS, "成功", result);
    }

    /**
     * 成功：分页列表数据组装
     *
     * @param
     * @return
     */
    public static Rs buildList(Object list, Page page) {
        return buildList(list, page, null);
    }



    public String getTrackId() {
        return trackId;
    }

    public void setTrackId(String trackId) {
        this.trackId = trackId;
    }

    public int getCode() {
        return this.code;
    }

    /**
     * 通过code找到RsEnum
     *
     * @param code
     */
    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return this.msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public Object getData() {
        return data;
    }

    public void setData(Object data) {
        this.data = data;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this, SerializerFeature.DisableCircularReferenceDetect);
    }
}
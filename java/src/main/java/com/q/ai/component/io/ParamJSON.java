package com.q.ai.component.io;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.List;

public class ParamJSON extends JSONObject {

    public void mustKey(List<String> keys){
        if(keys!=null)
        for (String key:keys){
            mustKey(key);
        }
    }
    public void mustKey(String key){
        if(!this.containsKey(key)){
            throw new RsException("入参缺少必填字段：" + key);
        }
    }


    public Integer getInteger(String key){
        if(!this.containsKey(key)){
            return 0;
        }
        return super.getInteger(key);
    }
    public Integer getMustInteger(String key){
        if(!this.containsKey(key)){
            throw new RsException("入参缺少必填字段(Long)：" + key);
        }
        return this.getInteger(key);
    }

    public Long getLong(String key){
        if(!this.containsKey(key)){
           return 0L;
        }
        return super.getLong(key);
    }
    public Long getMustLong(String key){
        if(!this.containsKey(key)){
            throw new RsException("入参缺少必填字段(Long)：" + key);
        }
        return super.getLong(key);
    }    /**
     * 获取大于0的正整数
     * @param key
     * @return
     */
    public Long getMustPlusLong(String key){
        if(!this.containsKey(key) || this.getLong(key)<=0){
            throw new RsException("入参缺少必填字段(Long)：" + key);
        }
        return this.getLong(key);
    }


    public String getString(String key){
        if(!this.containsKey(key)){
            return "";
        }
        return super.getString(key);
    }
    public String getMustString(String key){
        if(!this.containsKey(key)){
            throw new RsException("入参缺少必填字段(String)：" + key);
        }
        return this.getString(key);
    }

    public JSONObject getJsonObject(String key){
        if(!this.containsKey(key)){
            return new JSONObject();
        }
        return super.getJSONObject(key);
    }
    public JSONObject getMustJsonObject(String key){
        if(!this.containsKey(key)){
            throw new RsException("入参缺少必填字段(JSONObject)：" + key);
        }
        return this.getJSONObject(key);
    }
    public  <T> T  getJavaObject(String key, Class<T> clazz){
       return getJsonObject(key).toJavaObject(clazz);
    }
    public  <T> T  getMustJavaObject(String key,Class<T> clazz){
        return getMustJsonObject(key).toJavaObject(clazz);
    }

    public JSONArray getJsonArray(String key){
        if(!this.containsKey(key)){
            return new JSONArray();
        }
        return super.getJSONArray(key);
    }
    public JSONArray getMustJsonArray(String key){
        if(!this.containsKey(key)){
            throw new RsException("入参缺少必填字段(JSONArray)：" + key);
        }
        return this.getJSONArray(key);
    }



    public Page getPage(){
        if(this.containsKey("page")){//新版简版page
            return JSON.toJavaObject(this.getJSONObject("page"),Page.class);
        }
        return null;
    }


    public Page getMustPage(){
        Page page = getPage();
       if(null == page){
           throw new RsException("缺少Page信息");
       }
       return page;
    }


}

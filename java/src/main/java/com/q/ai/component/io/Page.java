package com.q.ai.component.io;


import com.alibaba.fastjson.JSON;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * limit (offset,limit)
 */
//@ApiModel(value = "PageModel", description = "请求结果为列表类型的接口入参及返回结果中的的分页信息")
public class Page {

    //limit 5,10
    //第一页开始

    @ApiModelProperty(value = "第几页", required = true, allowableValues = ">1")
    private int number;
    @ApiModelProperty(value = "每页大小", required = true, allowableValues = ">1")
    private int size;
    @ApiModelProperty(value = "一共多少条数据", allowableValues = ">0")
    private int total;

    @ApiModelProperty(hidden = true)
    private transient int offset;
    @ApiModelProperty(hidden = true)
    private transient int limit;

    public Page() {
    }

    public Page(int number, int size) {
        this.number = number;
        this.size = size;
    }
    public Page(int number, int size, int total) {
        this.number = number;
        this.size = size;
        this.total = total;
    }


    @Override
    public String toString() {
       return JSON.toJSONString(this);
    }

    /**
     * 偏移
     * @return
     */
    public int getOffset() {
        if(number <=0 || size <=0){
            throw new RsException("分页数据错误",-1);
        }
        return (number -1)* size;
    }

    /**
     * 条数
     * @return
     */
    public int getLimit(){
        return size;
    }
    public int getNumber() {
        return number;
    }

    public void setNumber(int number) {
        this.number = number;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public int getTotal() {
        return total;
    }

    public void setTotal(int total) {
        this.total = total;
    }
}
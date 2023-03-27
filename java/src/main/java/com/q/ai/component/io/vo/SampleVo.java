package com.q.ai.component.io.vo;


import com.q.ai.mvc.dao.po.Sample;
import com.q.ai.mvc.dao.po.SampleItem;

import java.util.List;

public class SampleVo extends Sample {
    List<SampleItem> sampleItemList;

    public SampleVo() {
    }


    public List<SampleItem> getSampleItemList() {
        return sampleItemList;
    }

    public void setSampleItemList(List<SampleItem> sampleItemList) {
        this.sampleItemList = sampleItemList;
    }
}

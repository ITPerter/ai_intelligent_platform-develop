package com.q.ai.mvc.esDao;

import com.alibaba.fastjson.JSON;
import com.q.ai.component.io.Page;
import com.q.ai.mvc.dao.po.BaseDataValue;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentFactory;
import org.elasticsearch.index.query.*;
import org.elasticsearch.search.SearchHit;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import javax.annotation.PostConstruct;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import static org.elasticsearch.index.query.QueryBuilders.*;

@Component
public class BaseDataValueDao extends EsBaseDao<BaseDataValue> {

    @Value("${baseDataValue.index}")
    private String indexString;
    @Value("${baseDataValue.alias_name}")
    private String aliasName;
    @Value("${baseDataValue.setting}")
    private String settingString;
    @Value("${baseDataValue.mapping}")
    private String mappingString;

    @PostConstruct
    public void init() throws IOException {
        createIndex();
    }


    /**
     * 查询一个，多个会被忽略
     *
     * @param number
     * @return
     */
    public BaseDataValue getByNumber(String number) {
        TermQueryBuilder query = termQuery("number", number);
        SearchResponse response = query(query, null, null, null);
        SearchHit[] searchHits = response.getHits().getHits();
        logger.info("ES getByNumber 查询到{}个", response.getHits().getTotalHits());
        if (searchHits.length == 0) {
            return null;
        }
        return JSON.parseObject(searchHits[0].getSourceAsString(), BaseDataValue.class);
    }


    /**
     * 模糊查询
     *
     * @param baseDataNumber
     * @param value
     * @return
     */
    public List<BaseDataValue> searchByBaseDataNumber(String baseDataNumber, String value, Page page) {
        MultiMatchQueryBuilder multiMatchQuery = QueryBuilders.multiMatchQuery(value, "value", "value.ik", "value.pinyin").operator(Operator.AND);

        BoolQueryBuilder boolQueryBuilder = boolQuery()
                .must(termQuery("baseDataNumber", baseDataNumber))
                .must(multiMatchQuery);
        SearchResponse response = query(boolQueryBuilder, page, null, null);
        SearchHit[] searchHits = response.getHits().getHits();
        logger.info("ES searchByBaseDataNumber 查询到({}):{}", searchHits.length, searchHits);
        List<BaseDataValue> rsList = new ArrayList<>();
        for (int i = 0; i < searchHits.length; i++) {
            rsList.add(JSON.parseObject(searchHits[i].getSourceAsString(), BaseDataValue.class));
        }
        return rsList;
    }


    public List<BaseDataValue> getListByBaseDataNumber(String baseDataNumber, Page page) {
        SearchResponse response = query(termQuery("baseDataNumber", baseDataNumber), page, null, null);
        SearchHit[] searchHits = response.getHits().getHits();
        long total = response.getHits().getTotalHits().value;
        page.setTotal((int) total);
        logger.info("ES getListByBaseDataNumber 查询到({}):{}", searchHits.length, searchHits);
        List<BaseDataValue> rsList = new ArrayList<>();
        for (int i = 0; i < searchHits.length; i++) {
            rsList.add(JSON.parseObject(searchHits[i].getSourceAsString(), BaseDataValue.class));
        }
        return rsList;
    }


    public boolean inserts(List<BaseDataValue> baseDataValueList) {
        try {
            return addList(baseDataValueList);
        } catch (IOException e) {
            e.printStackTrace();
        }
        return false;
    }

    //public int update(BaseDataValue baseDataValue) {return 0;}

    public int delByNumberList(List<String> numberList) {
        return (int) delete(termsQuery("number", numberList));
    }

    public int delByBaseDataNumberList(List<String> baseDataNumberList) {
        return (int) delete(termsQuery("baseDataNumber", baseDataNumberList));
    }


    @Override
    protected String getSettingString() {
        return settingString;
    }

    @Override
    protected String getMappingString() {
        return mappingString;
    }

    @Override
    protected String getAliasName() {
        return aliasName;
    }

    @Override
    protected String getIndexString() {
        return indexString;
    }

    @Override
    protected XContentBuilder t2Builder(BaseDataValue baseDataValue) throws IOException {
        return XContentFactory.jsonBuilder().startObject()
                .field("baseDataNumber", baseDataValue.getBaseDataNumber())
                .field("value", baseDataValue.getValue())
                .field("number", baseDataValue.getNumber())
                .field("creator", baseDataValue.getCreator())
                .field("updater", baseDataValue.getUpdater())
                .field("createTime", baseDataValue.getCreateTime())
                .field("updateTime", baseDataValue.getUpdateTime())
                .endObject();
    }
}
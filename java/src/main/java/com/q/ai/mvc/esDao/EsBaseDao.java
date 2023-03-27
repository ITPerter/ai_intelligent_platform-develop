package com.q.ai.mvc.esDao;

import com.q.ai.component.config.EsHighLevelConfig;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.RsException;
import com.q.ai.mvc.dao.po.ESBaseData;
import org.elasticsearch.action.admin.indices.alias.Alias;
import org.elasticsearch.action.bulk.BulkRequest;
import org.elasticsearch.action.index.IndexRequest;
import org.elasticsearch.action.index.IndexResponse;
import org.elasticsearch.action.search.SearchRequest;
import org.elasticsearch.action.search.SearchResponse;
import org.elasticsearch.client.IndicesClient;
import org.elasticsearch.client.RequestOptions;
import org.elasticsearch.client.RestHighLevelClient;
import org.elasticsearch.client.indices.CreateIndexRequest;
import org.elasticsearch.client.indices.GetIndexRequest;
import org.elasticsearch.common.xcontent.XContentBuilder;
import org.elasticsearch.common.xcontent.XContentType;
import org.elasticsearch.index.query.QueryBuilder;
import org.elasticsearch.index.query.QueryBuilders;
import org.elasticsearch.index.reindex.BulkByScrollResponse;
import org.elasticsearch.index.reindex.DeleteByQueryRequest;
import org.elasticsearch.rest.RestStatus;
import org.elasticsearch.search.builder.SearchSourceBuilder;
import org.elasticsearch.search.sort.SortOrder;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;

import java.io.IOException;
import java.util.List;

@Component
public abstract class EsBaseDao<T extends ESBaseData> {
    protected final Logger logger = LoggerFactory.getLogger(EsBaseDao.class);
    @Autowired
    private RestHighLevelClient client;

    @Autowired
    private EsHighLevelConfig esHighLevelConfig;

    protected abstract String getSettingString();

    protected abstract String getMappingString();

    protected abstract String getAliasName();

    protected abstract String getIndexString();

    protected abstract XContentBuilder t2Builder(T t) throws IOException;



    public IndicesClient getIndicesClient() {
        return client.indices();
    }

    public RestHighLevelClient getClient() {
        return client;
    }


    /**
     * 创建索引
     */
    public boolean createIndex() throws IOException {
        if (!esHighLevelConfig.isEsEnable()) {
            return false;
        }
        if (!existsIndex()) {
            CreateIndexRequest createIndexRequest = new CreateIndexRequest(getIndexString());
            if (!StringUtils.isEmpty(getSettingString())) {
                createIndexRequest.settings(getSettingString(), XContentType.JSON);
            }
            if (!StringUtils.isEmpty(getMappingString())) {
                createIndexRequest.mapping(getMappingString(), XContentType.JSON);
            }
            if (!StringUtils.isEmpty(getAliasName())) {
                Alias alias = new Alias(getAliasName());
                createIndexRequest.alias(alias);
            }
            logger.info("创建index:{}", getIndexString());
            return getIndicesClient().create(createIndexRequest, RequestOptions.DEFAULT).isAcknowledged();
        }
        return true;
    }


    /**
     * 索引是否存在
     */
    public boolean existsIndex() throws IOException {
        GetIndexRequest indexRequest = new GetIndexRequest(getIndexString());
        return getIndicesClient().exists(indexRequest, RequestOptions.DEFAULT);
    }


    /**
     * 1.删除失败，连接异常会抛出异常
     * 2.成功返回删除个数（包括没有删除项）
     *
     * @param queryBuilder
     * @return
     */
    public long delete(QueryBuilder queryBuilder) {
        logger.info("es delete query json:{}", queryBuilder.toString());
        DeleteByQueryRequest delRequest = new DeleteByQueryRequest(getIndexString());
        delRequest.setConflicts("proceed");//默认情况，在版本冲突时会终止进程，这里强制继续
        delRequest.setQuery(queryBuilder);
        delRequest.setSlices(2);
        delRequest.setRefresh(true);


        BulkByScrollResponse bulkByScrollResponse = null;
        try {
            bulkByScrollResponse = client.deleteByQuery(delRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RsException("ES queryAndDelete 出错");
        }

        return bulkByScrollResponse.getDeleted();
    }


    public SearchResponse query(QueryBuilder queryBuilder, Page page, String sortColumn, SortOrder sortOrder) {
        if (queryBuilder == null) {
            queryBuilder = QueryBuilders.matchAllQuery(); // 全文匹配
        }

        SearchRequest searchRequest = new SearchRequest(getIndexString());

        SearchSourceBuilder searchSourceBuilder = new SearchSourceBuilder();
        searchSourceBuilder.query(queryBuilder);
        if (null != page) {
            int from = (page.getNumber() - 1) * page.getSize();
            searchSourceBuilder.from(from).size(page.getSize());
        }
        if (StringUtils.hasText(sortColumn)) {
            if (sortOrder == null) {
                sortOrder = SortOrder.ASC;
            }
            searchSourceBuilder.sort(sortColumn, sortOrder);
        }

        logger.info("es json:{}", searchSourceBuilder);
        searchRequest.source(searchSourceBuilder);

        try {
            return client.search(searchRequest, RequestOptions.DEFAULT);
        } catch (IOException e) {
            e.printStackTrace();
            throw new RsException("ES query出错");
        }
    }


    public long queryCount(QueryBuilder queryBuilder) {
        SearchResponse searchResponse = query(queryBuilder, new Page(1, 0), null, null);
        return searchResponse.getHits().getTotalHits().value;

    }

    public boolean add(T t) {
        return add(t, t.get_id());
    }

    /**
     * 指定_id,_id用来去重
     *
     * @param t
     * @param _id
     * @return 成功，失败异常
     */
    public boolean add(T t, String _id) {
        try {
            IndexRequest indexRequest = new IndexRequest(getIndexString()).source(t2Builder(t));
            if (!StringUtils.isEmpty(_id)) {
                indexRequest = indexRequest.id(_id);
            }
            IndexResponse indexResponse = client.index(indexRequest, RequestOptions.DEFAULT);
            RestStatus restStatus = indexResponse.status();
            if (restStatus == RestStatus.CREATED) {//add
                return true;
            }
            if (restStatus == RestStatus.OK) {//update
                logger.info("add update _id{}", _id);
                return true;
            }
            throw new RsException(restStatus.toString());
        } catch (IOException e) {
            e.printStackTrace();
            throw new RsException(e.getMessage());
        }

    }


    public boolean addList(List<T> list) throws IOException {
        if (list == null || list.isEmpty()) {
            return true;
        }


        BulkRequest bulkRequest = new BulkRequest();
        for (T t : list) {
            XContentBuilder xContentBuilder = t2Builder(t);
            IndexRequest indexRequest = new IndexRequest(getIndexString()).source(xContentBuilder);
            if (!StringUtils.isEmpty(t.get_id())) {
                indexRequest.id(t.get_id());
            }
            bulkRequest.add(indexRequest);
        }
        try {
            return !client.bulk(bulkRequest, RequestOptions.DEFAULT).hasFailures();
        } catch (IOException e) {
            e.printStackTrace();
            throw new RsException("ES baseAddList出错");
        }

    }
}

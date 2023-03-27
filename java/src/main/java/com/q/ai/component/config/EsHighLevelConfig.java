package com.q.ai.component.config;

import jdk.nashorn.internal.objects.annotations.Constructor;
import org.apache.http.HttpHost;
import org.apache.http.auth.AuthScope;
import org.apache.http.auth.UsernamePasswordCredentials;
import org.apache.http.client.CredentialsProvider;
import org.apache.http.impl.client.BasicCredentialsProvider;
import org.elasticsearch.client.RestClient;
import org.elasticsearch.client.RestClientBuilder;
import org.elasticsearch.client.RestHighLevelClient;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import javax.annotation.PostConstruct;
import javax.annotation.PreDestroy;
import java.util.ArrayList;

@Configuration
public class EsHighLevelConfig {

    @Value("${elasticsearch.enable:true}")
    private Boolean esEnable;
    @Value("${elasticsearch.host:}")
    private String esHost;
    @Value("${elasticsearch.user:}")
    private String esUser;
    @Value("${elasticsearch.pwd:}")
    private String esPwd;

    private final Logger logger = LoggerFactory.getLogger(EsHighLevelConfig.class);

    public Boolean isEsEnable() {
        return esEnable;
    }

    @Bean
    public RestClientBuilder restClientBuilder() {

        if (!isEsEnable()) {
            logger.info("ES服务未启用,修改配置: elasticsearch.enable 为true可启用ES.");
        }
        /*用户认证对象*/
        final CredentialsProvider credentialsProvider = new BasicCredentialsProvider();
        /*设置账号密码*/
        credentialsProvider.setCredentials(AuthScope.ANY, new UsernamePasswordCredentials(esUser, esPwd));

        String[] hostArray = esHost.split(",");
        HttpHost[] httpHostArray = new HttpHost[hostArray.length];
        for (int i = 0; i < hostArray.length; i++) {
            String[] ip_port = hostArray[i].split(":");
            String ip = ip_port[0];
            int port = Integer.parseInt(ip_port[1]);
            httpHostArray[i] = new HttpHost(ip, port, "http");
        }


        return RestClient.builder(httpHostArray).setHttpClientConfigCallback(httpAsyncClientBuilder -> httpAsyncClientBuilder.setDefaultCredentialsProvider(credentialsProvider));
    }

    @Bean(name = "highLevelClient")
    public RestHighLevelClient highLevelClient(@Autowired RestClientBuilder restClientBuilder) {
        logger.info("注入bean:HighLevelClient");
        return new RestHighLevelClient(restClientBuilder);
    }

    @PreDestroy
    public void close() {
        logger.info("todo:断开ES");

    }

}

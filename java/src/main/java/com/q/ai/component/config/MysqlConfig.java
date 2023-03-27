package com.q.ai.component.config;

import com.q.ai.component.config.bean.DataSourceBean;
import org.apache.ibatis.session.SqlSessionFactory;
import org.mybatis.spring.SqlSessionFactoryBean;
import org.mybatis.spring.SqlSessionTemplate;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowire;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.jdbc.DataSourceBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.io.support.PathMatchingResourcePatternResolver;

import javax.annotation.PreDestroy;
import javax.sql.DataSource;

@Configuration
public class MysqlConfig {
    private final Logger logger = LoggerFactory.getLogger(this.getClass());

    @Value("${mybatis.mapper-locations}")
    private String mapperLocations;

    @Bean
    public DataSource dataSource(DataSourceBean dataSourceBean) {
        logger.info("注入bean:DataSource(for mybatis).");
        DataSourceBuilder dataSourceBuilder = DataSourceBuilder.create()
                .url(dataSourceBean.getUrl())
                .username(dataSourceBean.getUsername())
                .password(dataSourceBean.getPassword());
        return dataSourceBuilder.build();
    }


    /**
     * for mybatis
     * @param dataSource
     * @return
     * @throws Exception
     */
    @Bean()
    public SqlSessionFactory sqlSessionFactory(DataSource dataSource) throws Exception {
        logger.info("注入bean:SqlSessionFactory(for mybatis).");
        SqlSessionFactoryBean factoryBean = new SqlSessionFactoryBean();
        org.springframework.core.io.Resource[] mappers = new PathMatchingResourcePatternResolver().getResources(mapperLocations);
        factoryBean.setMapperLocations(mappers);
        factoryBean.setDataSource(dataSource);
        return factoryBean.getObject();
    }

    /**
     * for mybatis
     *
     * @param sqlSessionFactory
     * @return
     */
    @Bean
    public SqlSessionTemplate sqlSession(SqlSessionFactory sqlSessionFactory) {
        logger.info("注入bean:SqlSessionTemplate(for mybatis).");
        return new SqlSessionTemplate(sqlSessionFactory);
    }

    @PreDestroy
    public void close() {
        logger.info("todo:断开Mysql");

    }

}

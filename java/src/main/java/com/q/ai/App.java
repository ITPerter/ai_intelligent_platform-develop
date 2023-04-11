package com.q.ai;


import com.q.ai.component.config.bean.DataSourceBean;
import com.q.ai.component.util.OS;
import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import org.mybatis.spring.annotation.MapperScan;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.web.bind.annotation.CrossOrigin;

//@EnableAsync
@CrossOrigin(origins = {"*"})
@MapperScan(basePackages = "com.q.ai.mvc.dao")//可以避免在每个Mapper类上注解@Mapper
@SpringBootApplication
@EnableConfigurationProperties({DataSourceBean.class})
public class App {

    private static final Logger logger = LoggerFactory.getLogger(App.class);

    public static void main(String[] args) {

        OS.print("开始启动"+OS.getThread().getName());

        ApplicationContext app = SpringApplication.run(App.class, args);


        OS.addBuffer("主机名",OS.getHostName());
        String port = app.getEnvironment().getProperty("server.port");
        OS.addBuffer("IP",OS.getIp()+":"+port);
        OS.flushBuffer();

        OS.print("程序已成功启动………………");

        //fastjsonConfig();

    }


    public static void fastjsonConfig(){
        //是否输出值为null的字段,默认为false
        //JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteMapNullValue.getMask();
        //数值字段如果为null,输出为0,而非null
        //JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullNumberAsZero.getMask();
        //List字段如果为null,输出为[],而非null
        //JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullListAsEmpty.getMask();
        //字符类型字段如果为null,输出为 "",而非null
        //JSON.DEFAULT_GENERATE_FEATURE |= SerializerFeature.WriteNullStringAsEmpty.getMask();
    }









}

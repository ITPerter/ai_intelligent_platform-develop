package com.q.ai.component.config;

import com.alibaba.fastjson.JSONObject;
import com.fasterxml.classmate.TypeResolver;
import com.q.ai.component.io.ParamJSON;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;


@Configuration
public class SpringFoxConfig {
    @Bean
    public Docket createApi() {
        TypeResolver typeResolver = new TypeResolver();
        return new Docket(DocumentationType.SWAGGER_2)
                //swagger-ui右上角的分组
                .groupName("default")
                .apiInfo(apiInfo())
                //取消默认的几个状态码
                .useDefaultResponseMessages(false)
                //强制显示Model(ParamJSON继承了JSONOject不生效,猜测必须在项目包下才可以)，SwaggerModel的model会自动扫描入参出参及其中的实体。
                .additionalModels(typeResolver.resolve(ParamJSON.class))
                .additionalModels(typeResolver.resolve(JSONObject.class))
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.q.ai.mvc.controller"))
                //只扫描有注解：@Api 的类
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                //只扫描有注解：@ApiOperation 的方法
                .apis(RequestHandlerSelectors.withMethodAnnotation(ApiOperation.class))
                //过滤路径   PathSelectors.ant("/api*")
                .paths(PathSelectors.any())

                .build();
    }

    @Bean
    public Docket exampleApi() {
        return new Docket(DocumentationType.SWAGGER_2)
                .groupName("example")
                .apiInfo(exampleApiInfo())
                //取消默认的几个状态码
                .useDefaultResponseMessages(false)
                .select()
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
                .paths(PathSelectors.ant("/example/*"))
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("AI API接口")
                .description("springFox for ai.")
                .contact(new Contact("entropy", "https://ai.com", "ai@ai.com"))
                .version("v0.0.1")
                .build();
    }

    private ApiInfo exampleApiInfo() {
        return new ApiInfoBuilder()
                .title("Swagger 规范使用示例")
                .description("Swagger 规范使用示例。")
                .contact(new Contact("entropy", "https://swagger.io", "ai@ai.com"))
                .version("v1.0.1")
                .build();
    }
}

package com.q.ai.component.config;

import com.q.ai.component.io.Rs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.MediaType;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.config.annotation.ContentNegotiationConfigurer;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;


@Configuration
public class WebConfig implements WebMvcConfigurer {

    private final Logger logger = LoggerFactory.getLogger(this.getClass());
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SecurityInterceptor())
                //排除拦截
                //swagger
                .excludePathPatterns("/swagger*")
                //knife4j for swagger
                .excludePathPatterns("/doc*")
                //排除错误模块（报错被拦截会造成一种假象：拦截器排除不生效）
                .excludePathPatterns("/error")
                //拦截路径
                .addPathPatterns("/*");
    }


    public class SecurityInterceptor implements HandlerInterceptor {
        @Override
        public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws IOException {
            HttpSession session = request.getSession();
            if (session.getAttribute(session.getId()) != null){
                return true;
            }//直接输出会乱码
            response.setContentType(MediaType.APPLICATION_JSON_UTF8_VALUE);
            response.getWriter().write(Rs.buildErr("没有登录").toString());
            return false;
        }
    }

    @Override
    public void configureContentNegotiation(ContentNegotiationConfigurer configurer) {
        /* 是否通过请求Url的扩展名来决定media type */
        configurer.favorPathExtension(false)
                /* 不检查Accept请求头 */
                .ignoreAcceptHeader(true)
                .parameterName("mediaType")
                /* 设置默认的media type */
                .defaultContentType(MediaType.APPLICATION_JSON_UTF8)
                /* 请求以.html结尾的会被当成MediaType.TEXT_HTML*/
                .mediaType("html", MediaType.TEXT_HTML)
                /* 请求以.json结尾的会被当成MediaType.APPLICATION_JSON*/
                .mediaType("json", MediaType.APPLICATION_JSON);
    }








}

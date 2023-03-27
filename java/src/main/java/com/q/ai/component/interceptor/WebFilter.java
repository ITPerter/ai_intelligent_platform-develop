package com.q.ai.component.interceptor;

import com.q.ai.component.session.RequestContext;
import com.q.ai.component.io.Rs;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.core.annotation.Order;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import java.io.IOException;
import java.io.OutputStream;
import java.util.UUID;

/**
 * 1.Filter（过滤器）：
 * 可以获得Http原始的请求和响应信息，但是拿不到响应方法的信息
 * filter是属于Servlet规范的，不属于Spring
 */
@Configuration
@Order(0)
public class WebFilter implements Filter {


    private Logger logger = LoggerFactory.getLogger(WebFilter.class);

    @Autowired
    RequestContext requestContext;

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void doFilter(ServletRequest request, ServletResponse response, FilterChain chain) throws IOException, ServletException {

        HttpServletRequest httpServletRequest = (HttpServletRequest) request;
        HttpServletResponse httpServletResponse = (HttpServletResponse) response;

        String path = httpServletRequest.getServletPath();
        //token校验优先
        String token = request.getParameter("token");
        if (null != token) {
            requestContext.init(token);
        } else if (path.startsWith("/auth")) {
            String newToken = UUID.randomUUID().toString();
            newToken = newToken.replace("-", "");
            requestContext.init(newToken);
        } else {
            HttpSession httpSession = httpServletRequest.getSession(true);
            requestContext.init(httpSession.getId());
        }


        insertMDC();


        if (isFilterNoAllowUrl(path)) {

            httpServletResponse.setCharacterEncoding("UTF-8");
            httpServletResponse.setContentType("application/json");
            OutputStream outputStream = httpServletResponse.getOutputStream();
            //将字符转换成字节数组，指定以UTF-8编码进行转换
            byte[] rsByteArr = Rs.buildErr("这些地址不能访问呀", -400).toString().getBytes("UTF-8");
            //使用OutputStream流向客户端输出字节数组
            outputStream.write(rsByteArr);
        }
        chain.doFilter(request, response);
        //Cookie[] cookies = httpServletRequest.getCookies();
    }

    /**
     * 不允许访问的地址
     *
     * @param path
     * @return
     */
    private boolean isFilterNoAllowUrl(String path) {
        return path.startsWith("/admin")
                || path.startsWith("/admin");
    }


    private boolean insertMDC() {
        String trackId = System.currentTimeMillis() + "";
        MDC.put(RequestContext.TRACK_ID, trackId);
        return true;
    }

}

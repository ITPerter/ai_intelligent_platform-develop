package com.q.ai.component.controlleradvice;

import com.q.ai.component.io.Rs;
import com.q.ai.component.io.RsException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartException;


/**
 *
 */

@org.springframework.web.bind.annotation.ControllerAdvice
public class ControllerAdvice {

    private Logger logger = LoggerFactory.getLogger(getClass());

    public static int ERR_CODE = -1;
    public static int WARN_CODE = -2;

    /**
     * 全局controller异常处理类
     *
     * @param e 异常
     * @return 异常的描述返回
     */

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Rs exceptionHandler(Exception e) {
        logger.error("异常：", e);
        return Rs.buildErr("异常：" + e.getMessage(), ERR_CODE);
    }

    @ExceptionHandler({MultipartException.class})
    @ResponseBody
    public Rs multipartExceptionHandler(MultipartException e) {
        logger.error("multipart异常（一般是文件上传相关）：", e);
        return Rs.buildErr(" multipart异常（一般是文件上传相关）：" + e.getMessage(), ERR_CODE);
    }


    @ExceptionHandler(RsException.class)
    @ResponseBody
    public Rs rsExceptionHandler(RsException e) {
        logger.info("RSException:{},{}", e.getCode(), e.getCause());
        return Rs.buildErr(e.getMessage(), e.getCode() == -100 ? WARN_CODE : e.getCode());
    }

    @ExceptionHandler(DuplicateKeyException.class)
    @ResponseBody
    public Rs duplicateKeyExceptionHandler(DuplicateKeyException e) {
        String errMsg = e.getCause().getMessage();
        logger.info("DuplicateKeyException:{}", errMsg);
        return Rs.buildErr(errMsg, ERR_CODE);
    }


    @ExceptionHandler(NullPointerException.class)
    @ResponseBody
    public Rs nullExceptionHandler(NullPointerException e) {
        logger.error("空指针:", e);
        return Rs.buildErr("NullPointerException：" + e.getMessage(), ERR_CODE);
    }

}
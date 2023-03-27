package com.q.ai.component.util;

import java.util.HashMap;
import java.util.Map;

public enum HttpCodeEnum {

    CODE_200(200, "请求成功。"),


    CODE_301(301, "重定向，资源永久转移到新的url。"),
    CODE_302(302, "重定向，资源临时转移到新的url。"),

    CODE_403(403, "拒绝执行，Forbidden。"),
    CODE_404(404, "请求的资源(网页等)不存在，NOT FOUND。"),


    CODE_502(502, "网关错误  bad gateway。"),
    CODE_504(504, "网关超时  Gateway Time-out"),
    CODE_505(505, "http协议版本不对 HTTP Version Not Supported"),


    ;
    private int code;
    private String des;
    private Map<Integer, String> httpCodeMap = new HashMap<>();

    HttpCodeEnum(int code, String des) {
        this.code = code;
        this.des = des;
    }

    public int getCode() {
        return code;
    }

    public String getDes() {
        return des;
    }

    public String getByCode(int code) {

        if (httpCodeMap.size() == 0) {
            for (HttpCodeEnum httpCode : HttpCodeEnum.values()) {
                httpCodeMap.put(httpCode.getCode(), httpCode.getDes());
            }
        }

        if (httpCodeMap.containsKey(code)) {
            return httpCodeMap.get(code);
        } else {
            String errMsg = "httpCode:" + code;
            switch (code / 100) {
                case 1:
                    errMsg += "（1**）分类概念：需要更多信息来继续操作，不常用";
                    break;
                case 2:
                    errMsg += "（2**）成功";
                    break;
                case 3:
                    errMsg += "（3**）重定向";
                    break;
                case 4:
                    errMsg += "（4**）客户端错误";
                    break;
                case 5:
                    errMsg += "（5**）服务器错误";
                    break;
                default:
                    errMsg += "非http状态码";
            }
            return errMsg;
        }
    }


}

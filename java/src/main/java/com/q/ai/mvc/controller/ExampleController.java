package com.q.ai.mvc.controller;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import com.q.ai.component.io.Page;
import com.q.ai.component.io.ParamJSON;
import com.q.ai.component.io.Rs;
import io.swagger.annotations.*;
import org.springframework.web.bind.annotation.*;


@Api(tags = "Swagger标准示例接口写法")
@ApiResponses({@ApiResponse(code = 200, message = "响应成功")})

@RestController
@RequestMapping("/example")
public class ExampleController {

    @ApiOperation(value = "GET接口", notes = "标准GET接口")
    @ApiImplicitParam(name = "id", value = "入参", example = "14")
    @GetMapping("/getMsg")
    public String getMsg(@RequestParam int id) {
        return "msg";
    }



    @ApiOperation(value = "POST接口", notes = "标准POST接口")
    //dataType 指的是 @ApiModel注解过的Model的名字，或者基础类型
    @ApiImplicitParam(name = "param", value = "入参", required = true, paramType = "body", dataType = "ParamModel", example = "{\"token\":\"1234567\",\"domainId\":1531404752157184,\"page\":{\"size\":5,\"number\":1}}")
    @ApiResponses(@ApiResponse(code = 200, message = "请求成功",
            response = Rs.class, reference = "",
            responseContainer = "Map",
            examples = @Example({
                    @ExampleProperty(mediaType = "code", value = "错误码，0为成功，负数为ERR，>0为WARM"),
                    @ExampleProperty(mediaType = "data", value = "结果实体"),
            })))

    @PostMapping(value = "/postMsg",produces = "application/json;charset=UTF-8")
    public Rs postMsg(@RequestBody ParamJSON param){
        return Rs.buildList(param, param.getMustPage());
    }


}

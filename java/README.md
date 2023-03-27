# AI
AI智能对话



### 字段大小限制

字段大小限制一般取字段数据库长度的80%左右做限制，前端可向下取值。

名称类型(name,XXvalue,number)，一般40字符(0.8\*50)
描述类型(content,des)，一般160字符(0.8\*200)


机器人，意图，词槽
编码唯一，在内部系统以ID关联相关实体，词槽标注以编码关联，因此删除词槽后



//todoList

~~1.机器人意图关系相关接口   10.4~~

~~2.改造所有的delbyId?id->delbyIds?ids  10.4~~

~~3.一些insert需要改成inserts 10.5~~

~~4.机器人意图关系+意图词槽关系+词槽 10.5~~

~~5.词槽值 相关接口 ,词槽值重复校验 10.5~~

~~6.词槽样本10.10~~

~~7.样本标注10.11~~

~~8.训练接口10.12~~

~~9.词槽是否必须、默认值 10.6~~

~~10.词槽顺序 10.7 凌晨~~

~~11.词槽新建是否可直接关联意图+意图新建是否可直接关联词槽 10.6~~

~~12.意图的名称、词槽的名称不在唯一，增加意图编码，词槽编码来做唯一。10.7~~

~~13.session+token认证10.17~~

~~14.用户体系(session认证)~~

~~15.提供全量导入的接口（token认证）~~

~~16.chat(token认证)~~

17.jar代码混淆

~~18.词槽与基础资料新增实体~~

~~19.Time-NLP时间归一化 https://github.com/shinyke/Time-NLP~~


#### 词槽排序功能的实现

支持上下逐个调整的实现
1.插入的时候，取最大顺序逐个+1
2.删除的时候不做处理（排序整数会留空）
3.调整顺序，只支持上下一个调整，只需要对调两条数据的顺序seq记录

支持用户指定顺序
可以后端存以10递增的整数，前端展示为/10的保留一位小数的数，即用户输入在1，2之间可以输入一个1.x的排序。
第二种方法从算法上不高明，但非常实用，是个业务导向的解法。


#### mysql开启sql记录及查询历史sql

```
-- SET GLOBAL log_output = 'TABLE';SET GLOBAL gene
-- SET GLOBAL log_output = 'TABLE'; SET GLOBAL general_log = 'OFF'; //日志关闭
-- SELECT * from mysql.general_log ORDER BY event_time DESC;
//清空表（delete对于这个表，不允许使用，只能用truncate）
-- truncate table mysql.general_log;
```


## 对外接口

host:http://112.74.114.182/

### 一、基础资料导入接口

注意保密,后期密钥再java配置文件中修改`sync_base_data_secret`

```
app：BASE_DATA
secret：8c10c8bb76f948b388dbe797d3cbefbb
```

#### 1.1.获取新建基础资料及插入基础资料条目的token
生成nonce:
```
import com.google.common.base.Charsets;
import com.google.common.hash.Hashing;
String nonceOrigin = app + secret + timestamp;
String nonce = Hashing.sha256().hashString(nonceOrigin, Charsets.UTF_8).toString();
```

POST 'auth/getAppToken'
Content-Type: application/json
Input
```
{
    "app":"BASE_DATA",
    "nonce":"333afb544016d2f0a2f595a0ac7498b5078097130d6a6a8283d9919c1682793b",
    "timestamp":1499825149257
}
```
Output
```
{
    "code": 0,
    "data": {
        "appToken": "c6051295-9f52-4aaa-b8ac-f62c647025be"
    },
    "msg": "成功",
    "trackId": "1603727040019"
}
```

#### 1.2.新建基础资料
POST 'third/addBaseData?token=c6051295-9f52-4aaa-b8ac-f62c647025be'
Content-Type: application/json
Input:
```
{
    "createTime": "2020-10-24T15:07:55",
    "creator": 0,
    "des": "受雇佣的员工",
    "name": "员工",
    "number": "employee"
}
```
Output
本系统中，基础资料的编码不能重复，名称可以重复，请根据对接系统的实际需求传入编码和名称
```
{
    "code": -1,
    "msg": "Duplicate entry 'yinliao' for key 'idx_number'"
}
```

```
{
    "code": 0,
    "msg": "基础资料新建成功",
    "trackId": "1603728258779"
}
```

#### 1.3.给指定编码的基础资料添加取值条目

POST 'third/addBaseDataValueList?token=d05058ad-5eb9-4001-a53b-d031cf4f17e1'
Content-Type: application/json

Input
```
{
    "baseDataValueList": [
        {
            "value": "人民币2",
            "number": "RMB"
        },
        {
            "value": "美元2",
            "number": "USD"
        }
    ],
    "baseDataNumber": "begin_location",
    "operator": "replaceAll"
}
```



### 二、chat接口

注意保密，后期修改在java配置文件中增加修改`user_map`

```
用户名:admin  密码：Admin100&&
```



#### 2.1.获取chat接口授权

POST '/auth/getUserToken' 

Content-Type: application/json'

Input

```
{

  "userName":"{{参见上面提供的账号}}",
  "password":"{{参见上面提供的账号}}"

}
```



output

```
{
    "code": 0,
    "data": {
        "chatToken": "20091b79b9094b06ad51b6933f976089",
        "userName": "小明",
        "userId": 1000
    },
    "msg": "成功",
    "trackId": "1604305028574"
}
```



#### 2.2 Chat接口

此处token即2.1中获取的chatToken

POST '/third/chat?token={{chatToken}}' 

Content-Type: application/json

Input

```
{

   "robotId": 10,
   "chatMsg": "明天从深圳出差去北京开会"

}
```

Output

输出情况分为异常和正常，异常（code!=0），正常(code==0)

其中正常情况分为三种(根据state字段判断)：

##### 1.没有识别到意图（"state": "NO_INTENT"）

```
{
    "code": 0,
    "data": {
        "msg": "当前没有获得意图",
        "state": "NO_INTENT"
    },
    "msg": "成功",
    "trackId": "1604305998290"
}
```

##### 2.识别到意图，词槽澄清状态（"state": "CLARIFY"）

此时读取`currentSlot`字段的clarification来给用户提示，让用户输入相关词槽信息的语句。

```
{
    "code": 0,
    "data": {
        "currentSlot": {
            "baseDataNumber": "T_BD_QingDanLeiXing",
            "clarification": "你要从哪里出发？",
            "createTime": "2020-10-19T02:24:33",
            "creator": 0,
            "des": "",
            "id": 33,
            "must": true,
            "name": "出发地",
            "number": "begin_location",
            "originString": "我要从深圳出发去北京开会，后天回来",
            "slotState": "VERIFY_FAIL",
            "type": "BASE_DATA",
            "updateTime": "2020-11-10T02:02:02",
            "updater": 0,
            "verifyValueList": []
        },
        "intent": {
            "chatSlotList": [
                {
                    "baseDataNumber": "T_BD_QingDanLeiXing",
                    "clarification": "你要从哪里出发？",
                    "createTime": "2020-10-19T02:24:33",
                    "creator": 0,
                    "des": "",
                    "id": 33,
                    "must": true,
                    "name": "出发地",
                    "number": "begin_location",
                    "originString": "我要从深圳出发去北京开会，后天回来",
                    "slotState": "VERIFY_FAIL",
                    "type": "BASE_DATA",
                    "updateTime": "2020-11-10T02:02:02",
                    "updater": 0,
                    "verifyValueList": []
                },
                {
                    "clarification": "你要到哪里出差？",
                    "createTime": "2020-10-19T02:24:35",
                    "creator": 0,
                    "des": "",
                    "id": 34,
                    "must": true,
                    "name": "目的地",
                    "number": "distination_location",
                    "originString": "北京",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TEXT",
                    "updateTime": "2020-10-18T06:57:40",
                    "updater": 0,
                    "verifyValueList": [
                        "北京"
                    ]
                },
                {
                    "clarification": "你要什么时候出发",
                    "createTime": "2020-10-18T06:56:31",
                    "creator": 0,
                    "des": "",
                    "id": 35,
                    "must": true,
                    "name": "出发时间",
                    "number": "begin_time",
                    "slotState": "UN_FILL",
                    "type": "TIME",
                    "updateTime": "2020-10-18T06:56:31",
                    "updater": 0,
                    "verifyValueList": []
                },
                {
                    "clarification": "预计什么时候回来？",
                    "createTime": "2020-10-18T15:01:15",
                    "creator": 0,
                    "des": "",
                    "id": 36,
                    "must": true,
                    "name": "返回时间",
                    "number": "back_time",
                    "originString": "后天",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TIME",
                    "updateTime": "2020-10-18T07:01:15",
                    "updater": 0,
                    "verifyValueList": [
                        "2020-11-26T00:00"
                    ]
                },
                {
                    "clarification": "非必填项目的澄清语是没有用的。",
                    "createTime": "2020-10-18T06:58:38",
                    "creator": 0,
                    "des": "",
                    "id": 37,
                    "must": false,
                    "name": "出差事由",
                    "number": "reason",
                    "originString": "开会",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TEXT",
                    "updateTime": "2020-10-18T06:58:38",
                    "updater": 0,
                    "verifyValueList": [
                        "开会"
                    ]
                }
            ],
            "createTime": "2020-10-13T10:13:51",
            "creator": 0,
            "des": "演示专用",
            "id": 13,
            "name": "出差申请",
            "number": "chuchaishenqing",
            "updateTime": "2020-10-18T06:52:49",
            "updater": 0
        },
        "msg": "当前词槽需要澄清",
        "state": "CLARIFY"
    },
    "msg": "成功",
    "trackId": "1606212027692"
}
```

当需要澄清的词槽的状态是VERIFY_CHOOSE时，需要显示verifyValueList列表，供用户选择。

##### 3.业务提取完毕("state": "COMPLETE")

此时，读取`data.intent.id`来获取【意图，也就是配置的业务】

读取`data.intent.chatslotList`字段中的所有已经校验提取归一后的字段来填充意图对应的业务，由端调用业务。

`data.intent.chatslotList.slotState` == VERIFY_SUCCESS 后取 

【业务需要的参数名】：`data.intent.chatslotList.number`

【业务需要的参数的值】：`data.intent.chatslotList.verifyValue`



```
{
    "code": 0,
    "data": {
        "intent": {
            "chatSlotList": [
                {
                    "clarification": "你要从哪里出发？",
                    "createTime": "2020-10-19T02:24:33",
                    "creator": 0,
                    "des": "",
                    "id": 33,
                    "must": true,
                    "name": "出发地",
                    "number": "begin_location",
                    "originString": "深圳",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TEXT",
                    "updateTime": "2020-10-18T06:54:33",
                    "updater": 0,
                    "verifyValue": "深圳"
                },
                {
                    "clarification": "你要到哪里出差？",
                    "createTime": "2020-10-19T02:24:35",
                    "creator": 0,
                    "des": "",
                    "id": 34,
                    "must": true,
                    "name": "目的地",
                    "number": "distination_location",
                    "originString": "北京",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TEXT",
                    "updateTime": "2020-10-18T06:57:40",
                    "updater": 0,
                    "verifyValue": "北京"
                },
                {
                    "clarification": "你要什么时候出发",
                    "createTime": "2020-10-18T06:56:31",
                    "creator": 0,
                    "des": "",
                    "id": 35,
                    "must": true,
                    "name": "出发时间",
                    "number": "begin_time",
                    "originString": "明天",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TIME",
                    "updateTime": "2020-10-18T06:56:31",
                    "updater": 0,
                    "verifyValue": "2020-11-03T00:00"
                },
                {
                    "clarification": "预计什么时候回来？",
                    "createTime": "2020-10-18T15:01:15",
                    "creator": 0,
                    "des": "",
                    "id": 36,
                    "must": true,
                    "name": "返回时间",
                    "number": "back_time",
                    "originString": "后天",
                    "slotState": "VERIFY_SUCCESS",
                    "type": "TIME",
                    "updateTime": "2020-10-18T07:01:15",
                    "updater": 0,
                    "verifyValue": "2020-11-04T00:00"
                },
                {
                    "clarification": "非必填项目的澄清语是没有用的。",
                    "createTime": "2020-10-18T06:58:38",
                    "creator": 0,
                    "des": "",
                    "id": 37,
                    "must": false,
                    "name": "出差事由",
                    "number": "reason",
                    "slotState": "UN_FILL",
                    "type": "TEXT",
                    "updateTime": "2020-10-18T06:58:38",
                    "updater": 0
                }
            ],
            "createTime": "2020-10-13T10:13:51",
            "creator": 0,
            "des": "演示专用",
            "id": 13,
            "name": "出差申请",
            "number": "chuchaishenqing",
            "updateTime": "2020-10-18T06:52:49",
            "updater": 0
        },
        "msg": "当前意图已经完成",
        "state": "COMPLETE"
    },
    "msg": "成功",
    "trackId": "1604306254455"
}
```
新建gradle
#####java [clean]
#####java [build]
#####java [jar]
新建spring boot
main class:com.q.ai.App

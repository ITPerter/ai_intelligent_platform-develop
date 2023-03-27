package com.q.ai.component.util;

import ch.qos.logback.classic.LoggerContext;
import com.alibaba.fastjson.JSONObject;
import org.slf4j.LoggerFactory;

import java.lang.management.ManagementFactory;
import java.net.InetAddress;
import java.net.UnknownHostException;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

/**
 */
public class OS {

    private static Map<String,String> infoMap = new HashMap();

    private static final String ERR = "can not getOne now.";
    private static final String PID = "pid";
    private static final String HOST_NAME = "host_name";
    private static final String IP = "ip";

    private static ThreadLocal<HashMap<String,Object>> bufferMap = new ThreadLocal<>();

    public static int getPid(){

        if(!infoMap.containsKey(PID)){
            String name = ManagementFactory.getRuntimeMXBean().getName();
            infoMap.put(PID,name.split("@")[0]);
        }
        return Integer.parseInt(infoMap.get(PID));
    }

    /**
     * hostname
     */
    public static String getHostName() {
        if(!infoMap.containsKey(HOST_NAME)){
            try {
                infoMap.put(HOST_NAME,InetAddress.getLocalHost().getHostName());
            } catch (UnknownHostException e) {
                infoMap.put(HOST_NAME,ERR);
                e.printStackTrace();
            }
        }
        return  infoMap.get(HOST_NAME);
    }

    /**
     * 当前IP
     */
    public static String getIp(){

        if(!infoMap.containsKey(IP)){
            try {
                infoMap.put(IP,InetAddress.getLocalHost().getHostAddress());
            } catch (UnknownHostException e) {
                infoMap.put(IP,ERR);
                e.printStackTrace();
            }
        }
        return  infoMap.get(IP);
    }

    /**
     * 当前线程
     */
    public static Thread getThread(){
        return Thread.currentThread();
    }


    /**
     * 打印该返回参数可以查看logger内部状态
     */
    public static LoggerContext getLoggerContext(){
        return (LoggerContext) LoggerFactory.getILoggerFactory();
    }

    public static void print(Object o){
        if(o instanceof String){
            System.out.println(o);
        }else{
            System.out.println(JSONObject.toJSONString(o));
        }
    }
    public static void printName(Object o){
            System.out.println(o);
    }
    public static void printListName(Object[] objects){
        for(Object object:objects){
            System.out.println(object);
        }
    }


    public static void addBuffer(String key,Object value){

        HashMap<String,Object> hashMap = bufferMap.get();
        if(null == hashMap){
            hashMap = new LinkedHashMap<>();
        }

        hashMap.put(key,value);
        bufferMap.set(hashMap);
    }

    public static void flushBuffer(){
        HashMap<String,Object> hashMap = bufferMap.get();

        StringBuilder builder = new StringBuilder();

        for (Map.Entry<String, Object> entry:hashMap.entrySet()){
            builder.append(entry.getKey()).append(":").append(entry.getValue()).append("\n");
        }
        print(builder.toString());
        bufferMap.set(new LinkedHashMap<>());

    }



    public static String toString(Object o){
        return JSONObject.toJSONString(o);
    }
}

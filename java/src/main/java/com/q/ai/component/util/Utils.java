package com.q.ai.component.util;

import java.io.FileInputStream;
import java.io.IOException;
import java.net.URL;
import java.util.Iterator;
import java.util.List;
import java.util.Properties;

public class Utils {

    /**
     * Properties缺点是不能处理中文
     * 比自己读取文件的好处有：
     * 1.自动处理空格，空行，“=”前后的空格都会忽略
     * 2.自动处理注释，！#开头的行会被忽略
     *
     * @param propertiesFile
     * @return
     */
    public static Properties getProperties(String propertiesFile) {
        Properties properties = new Properties();
        FileInputStream inputStream = null;
        try {
            URL url = ClassLoader.getSystemResource(propertiesFile);
            inputStream = new FileInputStream(url.getFile());
            properties.load(inputStream);
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (inputStream != null) {
                try {
                    inputStream.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        return properties;
    }


    public static String joinString(List<String> list, String separator){
        if(null == list || list.size() == 0){
            return "";
        }
        StringBuilder builder = new StringBuilder();
        Iterator<String> iterator = list.iterator();
        while (iterator.hasNext()){
            builder.append(iterator.next());
            if(iterator.hasNext()){
                builder.append(separator);
            }
        }

        return builder.toString();
    }
}

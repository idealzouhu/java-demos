package com.zouhu.userservice.controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.context.config.annotation.RefreshScope;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 *  测试 nacos 配置中心
 *  <p>
 *      配置文件具体信息如下：            <br/>
 *      dataID：user-service.yaml     <br/>
 *      Group： DEFAULT_GROUP         <br/>
 *      Content：useLocalCache: true  <br/>
 *               cacheSize: 100       <br/>
 *  </p>
 *
 * @author zouhu
 * @data 2024-09-07 23:34
 */
@RestController
@RequestMapping("/config")
@RefreshScope   // 支持配置动态刷新
public class ConfigController {
    @Value("${useLocalCache:false}")    // 从配置文件中读取useLocalCache的值，默认值设置为false
    private boolean useLocalCache;

    @Value("${cacheSize:0}")
    private String cacheSize;       // 配置文件里面字段的内容默认为 String 类型

    /**
     * 获取配置信息
     * <p>
     *     <a href="http://localhost:8080/config/get"> get()调用路径 </a>
     *     <a href="http://localhost:8848/nacos/index.html"> Nacos 控制台 </a>
     * </p>
     *
     * @return
     */
    @RequestMapping("/get")
    public Map get() {
        Map<String, Object> configMap = new HashMap<>();
        configMap.put("useLocalCache", useLocalCache);
        configMap.put("cacheSize", cacheSize);
        return configMap;
    }
}



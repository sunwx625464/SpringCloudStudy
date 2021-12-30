package com.swx.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.client.RestTemplate;

/**
 * @Description:
 * @Author: sunweixin
 * @Date: 2021/12/30
 */
@Configuration
public class ApplicationContextConfig {

    /**
     * RestTemplate提供了多种便捷访问远程Http服务的方法，
     * 是一种简单便捷的访问restful服务模板类，是Spring提供的用于访问Rest服务的客户端模板工具集
     */
    @Bean
    public RestTemplate getRestTemplate(){
        return new RestTemplate();
    }
}

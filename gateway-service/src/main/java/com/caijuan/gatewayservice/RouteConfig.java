package com.caijuan.gatewayservice;

import org.springframework.cloud.gateway.route.Route;
import org.springframework.cloud.gateway.route.RouteLocator;
import org.springframework.cloud.gateway.route.builder.Buildable;
import org.springframework.cloud.gateway.route.builder.RouteLocatorBuilder;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.time.ZonedDateTime;

/**
 * @author cai juan
 */
// @Configuration
public class RouteConfig {

    // @Bean
    public RouteLocator declare(RouteLocatorBuilder builder) {
        return builder.routes()
                // 寻址谓词
                .route("id-001", route -> route
                                .path("/user")  // 路径
                                // .and().method(HttpMethod.GET, HttpMethod.POST) // 方法
                                .uri("/user")    // uri
                        // 请求参数谓词
                ).route("id-002", route -> (Buildable<Route>) route
                        .cookie("key", "value")
                        // 验证header
                        .and().header("myHeaderA")
                        .and().header("myHeaderB", "regex") // 验证param .and().query("paramA")
                        .and().query("paramB", "regex")
                        .and().remoteAddr("远程服务地址")
                        .and().host("pattern1", "pattern2")
                )
                .route("id-003", route -> (Buildable<Route>) route
                        // 在指定时间之前
                        .before(ZonedDateTime.parse("2022-12-25T14:33:47.789+08:00"))
                        // 在指定时间之后
                        .or().after(ZonedDateTime.parse("2022-12-25T14:33:47.789+08:00"))
                        // 或者在某个时间段以内
                        .or().between(ZonedDateTime.parse("起始时间"),
                                ZonedDateTime.parse("结束时间"))
                ).build();
    }
}
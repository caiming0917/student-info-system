package com.caijuan.gatewayservice;


import lombok.extern.slf4j.Slf4j;
import org.reactivestreams.Publisher;
import org.springframework.cloud.gateway.filter.GatewayFilterChain;
import org.springframework.cloud.gateway.filter.GlobalFilter;
import org.springframework.core.Ordered;
import org.springframework.core.io.buffer.DataBuffer;
import org.springframework.core.io.buffer.DataBufferFactory;
import org.springframework.core.io.buffer.DataBufferUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;
import org.springframework.http.server.reactive.ServerHttpResponseDecorator;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ServerWebExchange;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * 全局过滤
 *
 * @author cai juan
 */
@Slf4j
@Component
public class CustomGlobalFilter implements GlobalFilter, Ordered {


    private static final List<String> IP_WHITE_LIST = Arrays.asList("127.0.0.3", "127.0.0.2");

    @Override
    public Mono<Void> filter(ServerWebExchange exchange, GatewayFilterChain chain) {
        // 1. 请求日志
        ServerHttpRequest request = exchange.getRequest();
        String path = request.getPath().value();
        String method = Objects.requireNonNull(request.getMethod()).toString();
        log.info("请求唯一标识：" + request.getId());
        log.info("请求路径：" + path);
        log.info("请求方法：" + method);
        log.info("请求参数：" + request.getQueryParams());
        String sourceAddress = Objects.requireNonNull(request.getLocalAddress()).getHostString();
        log.info("请求目的地址：" + sourceAddress);
        log.info("请求来源地址：" + request.getRemoteAddress());


        // ServerHttpResponse response = exchange.getResponse();
        // // 2. 访问控制 - 黑白名单
        // if (!IP_WHITE_LIST.contains(sourceAddress)) {
        //     response.setStatusCode(HttpStatus.FORBIDDEN);
        //     return response.setComplete();
        // }
        // // 3. 用户鉴权（判断 token 是否合法）
        // HttpHeaders headers = request.getHeaders();
        // String nonce = headers.getFirst("nonce");
        // String timestamp = headers.getFirst("timestamp");
        // String token = headers.getFirst("token");
        // String invokeUser = getInvokeUser(token);
        //
        // // 时间和当前时间不能超过 5 分钟
        // long currentTime = System.currentTimeMillis() / 1000;
        // final long FIVE_MINUTES = 60 * 5L;
        // assert timestamp != null;
        // if ((currentTime - Long.parseLong(timestamp)) >= FIVE_MINUTES) {
        //     return handleNoAuth(response);
        // }
        //
        // assert nonce != null;
        // if (Long.parseLong(nonce) > 10000L) {
        //     return handleNoAuth(response);
        // }
        //
        // if (invokeUser == null) {
        //     return handleNoAuth(response);
        // }
        // if (!"yupi".equals(token)) {
        //     return handleNoAuth(response);
        // }

        // 4. 请求转发，调用模拟接口 + 响应日志
        return chain.filter(exchange);
    }

    private String getInvokeUser(String token) {
        return null;
    }

    @Override
    public int getOrder() {
        return -1;
    }

    public Mono<Void> handleNoAuth(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.FORBIDDEN);
        return response.setComplete();
    }

    public Mono<Void> handleInvokeError(ServerHttpResponse response) {
        response.setStatusCode(HttpStatus.INTERNAL_SERVER_ERROR);
        return response.setComplete();
    }
}
package com.caijuan.studentservice.config;

import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;

/**
 * @author cai juan
 * @date  2023/2/25 10:15
 * @see <a href="https://juejin.cn/post/7032092376961122335">掘金</a>
 * @see <a href="https://blog.csdn.net/weixin_39792935/article/details/122215625">还需配置 springMVC </a>
 * @see <a href="https://blog.csdn.net/zhanggonglalala/article/details/98070986">Swagger使用</a>
 *
 * @see <a href="http://localhost:8123/swagger-ui/index.html">访问地址</a>
 */

@Configuration
@ConditionalOnProperty(prefix="swagger",value={"enable"},havingValue="true")
public class Swagger2 {
    @Bean
    public Docket createRestApi() {
        return new Docket(DocumentationType.OAS_30)
                .apiInfo(apiInfo())
                .select()
                .apis(RequestHandlerSelectors.basePackage("com.caijuan.studentservice.controller"))
                .paths(PathSelectors.any())
                .build();
    }

    private ApiInfo apiInfo() {
        return new ApiInfoBuilder()
                .title("学生信息管理系统接口")
                .description("学生信息管理系统")
                .termsOfServiceUrl("https://localhost")
                .version("1.0")
                .build();
    }
}
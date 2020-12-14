package com.yonyou.iuap.corp.demo.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.service.Contact;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

@Configuration
@EnableSwagger2
public class SwaggerConfig {

    @Bean
    public Docket restAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(restAPIInfo())
                .groupName("demo")
                .select()
                // .apis(RequestHandlerSelectors.any())  // 注意修改此处的包名
                .apis(RequestHandlerSelectors.basePackage("com.yonyou.iuap.corp.demo.web"))//controller所在路径包
                .paths(PathSelectors.any())
                .build();

    }

    private ApiInfo restAPIInfo() {
        return new ApiInfoBuilder()
                .title("獲取友戶通臨時token")
                .description("用於登陸跳轉驗證")
                .contact(new Contact("聯系人", "", "xxx@walsin.com"))
                .version("1.0")
                .build();
    }

    @Bean
    public Docket actuatorAPI() {
        return new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(restAPIInfo())
                .groupName("actuator")
                .select()
                .apis(RequestHandlerSelectors.any())
                .paths(PathSelectors.regex("/actuator/.*"))
                .build();
    }


}

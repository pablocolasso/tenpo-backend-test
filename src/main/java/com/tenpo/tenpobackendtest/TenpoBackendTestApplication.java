package com.tenpo.tenpobackendtest;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiInfo;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import java.util.Collections;

@SpringBootApplication
public class TenpoBackendTestApplication {

	public static void main(String[] args) {
		SpringApplication.run(TenpoBackendTestApplication.class, args);
	}

	@Bean
	public Docket swaggerConfiguration() {
		return new Docket(DocumentationType.SWAGGER_2).select()
				.apis(RequestHandlerSelectors.basePackage("com.tenpo.tenpobackendtest"))
				.build()
				.apiInfo(apiDetails());
	}

	private ApiInfo apiDetails(){
		return new ApiInfo(
				"Tenpo test",
				"Backend test for tenpo Interview",
				"1.0",
				"Free",
				new springfox.documentation.service.Contact("Pablo Maximiliano Colasso Gerban", "", "pablomcolasso@gmail.com"),
				"API License",
				"",
				Collections.emptyList());

	}
}

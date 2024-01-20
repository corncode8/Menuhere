package booklet.menuhere.config;


import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;

import lombok.RequiredArgsConstructor;

import org.springframework.context.annotation.Configuration;


@OpenAPIDefinition(
        info = @Info(
                title = "menuHere 프로젝트 API 명세서",
                description = "menuHere 프로젝트에 사용되는 API 명세서",
                version = "v1"
        )
)
@Configuration
@RequiredArgsConstructor
public class SwaggerConfig {


}
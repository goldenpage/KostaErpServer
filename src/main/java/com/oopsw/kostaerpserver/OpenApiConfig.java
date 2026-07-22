package com.oopsw.kostaerpserver;

import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.headers.Header;
import io.swagger.v3.oas.models.media.Content;
import io.swagger.v3.oas.models.media.ObjectSchema;
import io.swagger.v3.oas.models.media.StringSchema;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityRequirement;
import io.swagger.v3.oas.models.security.SecurityScheme;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Collections;
import java.util.List;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI openAPI() {
        String schemeName = "bearerAuth";

        return new OpenAPI()
            .components(new Components()
                .addSecuritySchemes(schemeName,
                    new SecurityScheme()
                        .type(SecurityScheme.Type.HTTP)
                        .scheme("bearer")
                        .bearerFormat("JWT")))
            .addSecurityItem(new SecurityRequirement().addList(schemeName))
            .path("/api/auth/login", loginPath());
    }

    private PathItem loginPath() {
        ObjectSchema loginSchema = new ObjectSchema();
        loginSchema.addProperty("username", new StringSchema().example("0000000000"));
        loginSchema.addProperty("password", new StringSchema().format("password").example("password"));
        loginSchema.required(List.of("username", "password"));

        Operation loginOperation = new Operation()
            .tags(List.of("auth"))
            .summary("로그인")
            .description("Spring Security JwtAuthenticationFilter가 처리하는 로그인 API입니다. 성공 시 Authorization 응답 헤더에 Bearer Access Token이 내려옵니다.")
            .security(Collections.emptyList())
            .requestBody(new RequestBody()
                .required(true)
                .content(new Content().addMediaType(
                    "application/json",
                    new io.swagger.v3.oas.models.media.MediaType().schema(loginSchema)
                )))
            .responses(new ApiResponses()
                .addApiResponse("200", new ApiResponse()
                    .description("로그인 성공")
                    .addHeaderObject("Authorization", new Header()
                        .description("Bearer Access Token")
                        .schema(new StringSchema().example("Bearer eyJ...")))
                    .addHeaderObject("Set-Cookie", new Header()
                        .description("refreshToken 쿠키")
                        .schema(new StringSchema())))
                .addApiResponse("401", new ApiResponse()
                    .description("로그인 실패")));

        return new PathItem().post(loginOperation);
    }
}


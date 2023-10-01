package shop.mtcoding.bank.config;

import io.swagger.v3.oas.annotations.OpenAPIDefinition;
import io.swagger.v3.oas.annotations.info.Info;
import io.swagger.v3.oas.models.Components;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.Operation;
import io.swagger.v3.oas.models.PathItem;
import io.swagger.v3.oas.models.media.*;
import io.swagger.v3.oas.models.parameters.RequestBody;
import io.swagger.v3.oas.models.responses.ApiResponse;
import io.swagger.v3.oas.models.responses.ApiResponses;
import io.swagger.v3.oas.models.security.SecurityScheme;
import io.swagger.v3.oas.models.tags.Tag;
import org.springdoc.core.customizers.OpenApiCustomizer;
import org.springdoc.core.models.GroupedOpenApi;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Profile;
import org.springframework.http.HttpStatus;
import org.springframework.security.web.FilterChainProxy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.context.AbstractSecurityWebApplicationInitializer;
import shop.mtcoding.bank.config.jwt.JwtAuthenticationFilter;

import java.util.Optional;

import static org.springframework.http.MediaType.APPLICATION_JSON_VALUE;

@Profile({"dev"})
@Configuration
@OpenAPIDefinition(
        info = @Info(title = "Bank Account API",
                description = "Bank Account API 명세",
                version = "v1"))
public class SwaggerConfig {

    @Autowired
    private ApplicationContext applicationContext;

    @Bean
    public GroupedOpenApi chatOpenApi() {
        String[] paths = {"/api/**"};

        return GroupedOpenApi.builder()
                .group("Bank Account API v1")
                .pathsToMatch(paths)
                .addOpenApiCustomizer(springSecurityLoginEndpointCustomizer())
                .build();
    }

    @Bean
    public OpenAPI customOpenAPI() {
        return new OpenAPI()
                .components(new Components()
                        .addSecuritySchemes("bearer-key",
                                new SecurityScheme().type(SecurityScheme.Type.HTTP).scheme("bearer").bearerFormat("JWT")));
    }

    @Bean
    public OpenApiCustomizer springSecurityLoginEndpointCustomizer() {
        FilterChainProxy filterChainProxy = applicationContext.getBean(
                AbstractSecurityWebApplicationInitializer.DEFAULT_FILTER_NAME, FilterChainProxy.class);
        return openAPI -> {
            for (SecurityFilterChain filterChain : filterChainProxy.getFilterChains()) {
                Optional<JwtAuthenticationFilter> optionalFilter =
                        filterChain.getFilters().stream()
                                .filter(JwtAuthenticationFilter.class::isInstance)
                                .map(JwtAuthenticationFilter.class::cast)
                                .findAny();
                if (optionalFilter.isPresent()) {
                    JwtAuthenticationFilter jwtAuthenticationFilter = optionalFilter.get();
                    Operation operation = new Operation();

                    // 요청 양식 설정
                    Schema<?> schema = new ObjectSchema()
                            .addProperty(jwtAuthenticationFilter.getUsernameParameter(), new StringSchema().title("아이디"))
                            .addProperty(jwtAuthenticationFilter.getPasswordParameter(), new StringSchema().title("비밀번호"));
                    RequestBody requestBody = new RequestBody().content(
                            new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(schema)));
                    operation.requestBody(requestBody);

                    // 응답 형식 설정
                    ApiResponses apiResponses = new ApiResponses();

                    Schema<?> okDataSchema = new ObjectSchema()
                            .addProperty("id", new StringSchema().title("아이디").description("아이디"))
                            .addProperty("username", new StringSchema().title("이름").description("이름"))
                            .addProperty("createdAt", new StringSchema().title("생성일").description("생성일").example("yyyy-MM-dd HH:mm:ss"));

                    Schema<?> okSchema = new ObjectSchema()
                            .addProperty("code", new StringSchema().title("응답 코드").description("응답 코드").example("1"))
                            .addProperty("msg", new StringSchema().title("응답 메세지").description("응답 메세지").example("로그인 성공"))
                            .addProperty("data", okDataSchema);

                    Schema<?> badRequestSchema = new ObjectSchema()
                            .addProperty("code", new StringSchema().title("응답 코드").description("응답 코드").example("-1"))
                            .addProperty("msg", new StringSchema().title("응답 메세지").description("응답 메세지").example("로그인 실패"))
                            .addProperty("data", new StringSchema().example(null));

                    apiResponses.addApiResponse(String.valueOf(HttpStatus.OK.value()),
                            new ApiResponse().description("로그인 성공")
                                    .content(new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(okSchema))));
                    apiResponses.addApiResponse(String.valueOf(HttpStatus.BAD_REQUEST.value()),
                            new ApiResponse().description("로그인 실패")
                                    .content(new Content().addMediaType(APPLICATION_JSON_VALUE, new MediaType().schema(badRequestSchema))));
                    operation.responses(apiResponses);

                    // 태그 설정
                    String tagName = "Login Controller";
                    operation.addTagsItem(tagName)
                            .summary("로그인");
                    PathItem pathItem = new PathItem().post(operation).summary("인증 API");
                    openAPI.addTagsItem(new Tag().name(tagName).description("인증 API")).getPaths().addPathItem("/api/login", pathItem);
                }
            }
        };
    }

}

package com.laiyefei.project.original.soil.equip.swagger.prepper;

import com.google.common.base.Predicates;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.co.IModule;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.prepper.IPrepper;
import com.laiyefei.project.original.soil.equip.swagger.SwaggerInstall;
import com.laiyefei.project.original.soil.equip.swagger.co.SwaggerModuleInfo;
import com.laiyefei.project.original.soil.standard.spread.foundation.performer.TokenConfigComp;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co.LicenseType;
import com.laiyefei.project.original.soil.standard.spread.foundation.tools.util.ClassUtil;
import io.swagger.annotations.Api;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.autoconfigure.condition.ConditionalOnBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import springfox.documentation.builders.ApiInfoBuilder;
import springfox.documentation.builders.PathSelectors;
import springfox.documentation.builders.RequestHandlerSelectors;
import springfox.documentation.service.ApiKey;
import springfox.documentation.service.AuthorizationScope;
import springfox.documentation.service.SecurityReference;
import springfox.documentation.spi.DocumentationType;
import springfox.documentation.spi.service.contexts.SecurityContext;
import springfox.documentation.spring.web.plugins.ApiSelectorBuilder;
import springfox.documentation.spring.web.plugins.Docket;
import springfox.documentation.swagger2.annotations.EnableSwagger2;

import javax.annotation.PostConstruct;
import java.util.*;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : swagger 配置
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
@ConditionalOnBean({
        SwaggerModuleInfo.class,
        TokenConfigComp.class
})
@EnableSwagger2
public class SwaggerPrepper implements IPrepper, WebMvcConfigurer, ApplicationContextAware {

    private final SwaggerModuleInfo swaggerModuleInfo;
    private final TokenConfigComp tokenConfigComp;
    private ApplicationContext context;
    private SwaggerInstall swaggerInstall;

    @Autowired
    public SwaggerPrepper(SwaggerModuleInfo swaggerModuleInfo,
                          TokenConfigComp tokenConfigComp) {
        this.swaggerModuleInfo = swaggerModuleInfo;
        this.tokenConfigComp = tokenConfigComp;
    }

    @PostConstruct
    public void afterCheck() {
        Assert.notNull(this.tokenConfigComp, "error: sorry, the tokenComps is null.");
        Assert.notNull(this.context, "error: sorry, the context is null.");
        Map<String, Object> installs = this.context.getBeansWithAnnotation(SwaggerInstall.class);
        Assert.notNull(installs, "error: sorry, no register swagger event.");
        Assert.isTrue(0 < installs.size(), "error: sorry, no register swagger event.");
        swaggerInstall = Optional.of(installs)
                .orElse(new HashMap<>()).values().stream()
                .map(e -> SwaggerInstall.class.cast(ClassUtil.gainMergedAnnotation(e.getClass(), SwaggerInstall.class))).collect(Collectors.toList()).get(0);

    }

    @Bean
    public Docket create() {
        //get annotation
        final String title = Optional.ofNullable(this.tokenConfigComp.gainProperty("spring.application.name")).orElseGet(() -> "项目名丢失");
        ApiSelectorBuilder selectorBuilder = new Docket(DocumentationType.SWAGGER_2)
                .apiInfo(new ApiInfoBuilder().title("项目【".concat(title).concat("】接口文档"))
                        .description("项目【".concat(title).concat("】接口文档"))
                        .version(this.swaggerModuleInfo.getVersionCode())
                        .license(LicenseType.Apache.getCode())
                        .licenseUrl(LicenseType.Apache.getUrl())
                        .build())
                .select();
        //just get first near application
        final Class<? extends IModule<?>> clazz = this.swaggerInstall.value();
        final IModule module = this.context.getBean(clazz);
        Assert.notNull(module, "error: sorry, the class named ".concat(clazz.getName()).concat(" is not exists in spring context."));
        final String basePackage = module.getBasePackageCode();
        final Docket docket = selectorBuilder
//                .apis(RequestHandlerSelectors.basePackage(basePackage))
                .apis(RequestHandlerSelectors.withClassAnnotation(Api.class))
//                .apis(RequestHandlerSelectors.withClassAnnotation(RestController.class))
                .paths(Predicates.not(PathSelectors.regex("/error.*")))
                .paths(PathSelectors.any())
                .build();
        if (this.tokenConfigComp.containsProperty(TokenConfigComp.TOKEN_HEADER_CONFIG_KEY)) {
            final String tokenHeader = this.tokenConfigComp.gainTokenHeader();
            docket.securityContexts(List.of(SecurityContext.builder()
                    .securityReferences(Arrays.asList(
                            new SecurityReference(tokenHeader,
                                    new AuthorizationScope[]{new AuthorizationScope(tokenHeader, tokenHeader.concat("描述信息"))})))
                    .forPaths(PathSelectors.any())
                    .build()));
            docket.securitySchemes(List.of(new ApiKey(tokenHeader, tokenHeader, "header")));
        }
        return docket;
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

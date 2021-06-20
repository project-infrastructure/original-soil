package com.laiyefei.project.infrastructure.original.soil.whole.kernel.prepper;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.prepper.IPrepper;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.aid.IDataHolder;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.co.IResourceWhiteList;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.prepper.IInterceptor;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.ClassUtil;
import org.springframework.beans.BeansException;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;
import org.springframework.web.servlet.config.annotation.InterceptorRegistration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 抽象拦截器
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
@ConditionalOnClass(IDataHolder.class)
public class InterceptorRegister implements IPrepper, ApplicationContextAware, WebMvcConfigurer {

    private ApplicationContext context;

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        final List<IInterceptor> result = ClassUtil.GetBeansOrderedBy(this.context, IInterceptor.class);
        for (IInterceptor item : result) {
            if (!HandlerInterceptorAdapter.class.isAssignableFrom(item.getClass())) {
                System.out.println("warning: sorry, find the class named [" + item.getClass().getName() + "] is not assign from ".concat(HandlerInterceptorAdapter.class.getName()));
                continue;
            }
            this.excludeRegisterInterceptor(registry, HandlerInterceptorAdapter.class.cast(item));
        }
    }

    private void includeRegisterInterceptor(InterceptorRegistry registry, HandlerInterceptorAdapter interceptorAdapter) {
        //附加白名单
        final InterceptorRegistration registration = registry.addInterceptor(interceptorAdapter);
        for (IDataHolder.IWhiteKey whiteKey : IDataHolder.whiteKeys) {
            IDataHolder.IWhiteData whiteData = IDataHolder.gainWhiteData(whiteKey);
            registration.addPathPatterns(
                    Optional.of(whiteData.gainWhiteLists())
                            .orElse(new ArrayList<>()).stream().map(e -> e.gainPath()).collect(Collectors.toList()));
        }
    }

    private void excludeRegisterInterceptor(InterceptorRegistry registry, HandlerInterceptorAdapter interceptorAdapter) {
        //附加白名单
        final InterceptorRegistration registration = registry.addInterceptor(interceptorAdapter);
        for (IDataHolder.IWhiteKey whiteKey : IDataHolder.whiteKeys) {
            IDataHolder.IWhiteData whiteData = IDataHolder.gainWhiteData(whiteKey);
            registration.excludePathPatterns(
                    Optional.of(whiteData.gainWhiteLists())
                            .orElse(new ArrayList<>()).stream().map(e -> {
                        //断言校验
                        Assert.notNull(e.gainPath(), "error: sorry, the path is null.");
                        return e.gainPath();
                    }).collect(Collectors.toList()));
        }
    }

    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        //附加资源处理白名单
        for (IDataHolder.IWhiteKey whiteKey : IDataHolder.whiteKeys) {
            IDataHolder.IWhiteData whiteData = IDataHolder.gainWhiteData(whiteKey);
            final List<? extends IResourceWhiteList> resourceWhiteLists = whiteData.gainResourceWhiteLists();
            for (final IResourceWhiteList item : resourceWhiteLists) {
                if (!item.isResource()) {
                    continue;
                }
                //断言校验
                Assert.notNull(item.getRouteMatch(), "error: RouteMatch is null.");
                Assert.notNull(item.getClassPath(), "error: ClassPath is null.");

                registry.addResourceHandler(item.getRouteMatch()).addResourceLocations(item.getClassPath());
            }
        }
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }
}

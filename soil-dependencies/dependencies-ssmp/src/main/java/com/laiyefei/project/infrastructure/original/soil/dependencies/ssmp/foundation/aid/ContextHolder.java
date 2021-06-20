package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.aid;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.dao.IBaseDao;
import com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po.AbsBasePo;
import org.springframework.beans.BeansException;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.context.annotation.Configuration;
import org.springframework.util.Assert;

import javax.annotation.PostConstruct;
import java.util.function.Function;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 上下文持有者
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Configuration
public class ContextHolder implements IAid, ApplicationContextAware {
    private static ApplicationContext context;

    @PostConstruct
    private void afterCheck() {
        Assert.notNull(context, "error: sorry, the context is null.");
    }

    public static final <T> T GainBean(final Class<T> clazz) {
        return context.getBean(clazz);
    }

    public static final <T extends IBaseDao<? extends AbsBasePo>, R extends AbsBasePo> R DoDaoAction(final Class<T> clazz, final Function<T, R> function) {
        final T t = GainBean(clazz);
        Assert.notNull(t, "error: sorry, the class named [" + clazz.getName() + "] is not register to spring context.");
        return function.apply(t);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        this.context = applicationContext;
    }

    public static ApplicationContext getContext() {
        return context;
    }
}

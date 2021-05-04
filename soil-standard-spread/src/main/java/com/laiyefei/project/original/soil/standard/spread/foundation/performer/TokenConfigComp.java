package com.laiyefei.project.original.soil.standard.spread.foundation.performer;

import com.laiyefei.project.infrastructure.original.soil.standard.foundation.performer.IPerformer;
import com.laiyefei.project.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import org.springframework.context.EnvironmentAware;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : token 处理组件
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Component
public final class TokenConfigComp implements IPerformer, EnvironmentAware {
    public static final String TOKEN_HEADER_CONFIG_KEY = "project-infrastructure.original.soil.access-token-header";
    private static final String ACCESS_TOKEN = "access_token";
    protected static final String SIGN_KEY_INFO = "LL$leaf.fly(c)";

    private Environment environment;

    public Environment gainEnvironment() {
        return environment;
    }

    public String gainTokenHeader() {
        return JudgeUtil.NVL(environment.getProperty(TokenConfigComp.TOKEN_HEADER_CONFIG_KEY), ACCESS_TOKEN);
    }

    public String gainProperty(String key) {
        return environment.getProperty(key);
    }

    public boolean containsProperty(String key) {
        return environment.containsProperty(key);
    }

    @Override
    public void setEnvironment(Environment environment) {
        this.environment = environment;
    }
}

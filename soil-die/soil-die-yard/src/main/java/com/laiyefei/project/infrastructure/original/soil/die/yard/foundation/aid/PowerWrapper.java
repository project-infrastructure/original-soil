package com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.aid;

import com.laiyefei.project.infrastructure.original.soil.die.yard.foundation.pojo.po.Permission;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.aid.IAid;
import com.laiyefei.project.infrastructure.original.soil.common.pojo.ao.Power;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.JudgeUtil;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.tools.util.StringUtil;
import org.springframework.util.Assert;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-09-29 18:09
 * @Desc : 权限包装
 * @Version : v2.0.0.20200929
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public final class PowerWrapper implements IAid {

    private final Power power;
    private final RequestMapping requestMapping;
    private String method = StringUtil.EMPTY;
    private String path = StringUtil.EMPTY;

    public PowerWrapper(Power power, RequestMapping requestMapping, String method) {
        this.power = power;
        this.requestMapping = requestMapping;
        this.method = method;

        Assert.notNull(power, "error: sorry, the power is null.");
        Assert.notNull(requestMapping, "error: sorry, the requestMapping is null.");
        Assert.notNull(method, "error: sorry, the method is null.");
    }

    public Power getPermission() {
        return power;
    }

    public PowerWrapper append(final String path) {
        this.path = StringUtils.cleanPath(this.path.concat("/").concat(path)).replaceAll("\\/+", "/");
        return this;
    }

    public String getPath() {
        return path;
    }

    public RequestMapping getRequestMapping() {
        return requestMapping;
    }

    public Permission gainPermission() {
        return Permission.BuildBy(
                power.name(),
                this.path,
                this.method,
                power.accessType().getCode(),
                power.permissionType().getCode()
        );
    }

    public static final PowerWrapper BuildBy(final Power power, RequestMapping requestMapping, String method) {
        return new PowerWrapper(power, requestMapping, method);
    }

    public static final List<Permission> Transfer(final List<PowerWrapper> powerWrappers) {
        if (JudgeUtil.IsNull(powerWrappers)) {
            return new ArrayList<>();
        }
        return powerWrappers.stream().map(e -> e.gainPermission()).collect(Collectors.toList());
    }

}

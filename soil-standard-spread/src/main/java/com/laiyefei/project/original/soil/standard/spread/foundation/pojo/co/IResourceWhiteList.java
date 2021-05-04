package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.co;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 资源白名单接口
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IResourceWhiteList extends IWhiteList {
    String getRouteMatch();

    String getClassPath();

    @Override
    default boolean isResource() {
        return true;
    }
}

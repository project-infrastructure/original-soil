package com.laiyefei.project.original.soil.standard.spread.foundation.pojo.fo;


import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.fo.IFunction;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 持有者
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public interface IHolder extends IFunction {

    @FunctionalInterface
    interface IExecute {
        void done();
    }

    @FunctionalInterface
    interface IExecuteOk {
        boolean done();
    }
}

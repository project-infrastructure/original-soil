package com.laiyefei.project.original.soil.common.pojo.dto;

import com.alibaba.fastjson.JSON;
import com.laiyefei.project.original.soil.standard.spread.foundation.pojo.dto.AbsBaseDto;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.util.function.Supplier;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础传输对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@ApiModel("基础传输对象")
public class BaseDto<T> extends AbsBaseDto<T> {
    public static final <T> BaseDto<T> BuildBy(boolean ok, String code, String name) {
        return new BaseDto<>(ok, code, name);
    }

    public static final <T> BaseDto<T> BuildBy(boolean ok, String code, String name, T entity) {
        return new BaseDto<>(ok, code, name, entity);
    }

    public static final <T> BaseDto<T> BuildSuccessBy(String code, String name) {
        return BuildBy(true, code, name);
    }

    public static final <T> BaseDto<T> BuildFailedBy(String code, String name) {
        return BuildBy(false, code, name);
    }

    public static final <T> BaseDto<T> BuildSuccessBy(String code, String name, T entity) {
        return BuildBy(true, code, name, entity);
    }

    public static final <T> BaseDto<T> BuildFailedBy(String code, String name, T entity) {
        return BuildBy(false, code, name, entity);
    }

    public static final <T> BaseDto<T> BuildBy(boolean ok) {
        return ok ? BaseDto.BuildSuccess() : BaseDto.BuildFailed();
    }

    public static final <T> BaseDto<T> BuildFailed() {
        return BuildBy(false, "failed", "操作失败");
    }

    public static final <T> BaseDto<T> BuildSuccess() {
        return BuildBy(true, "success", "操作成功");
    }

    public static final <T> BaseDto<T> BuildSuccessBy(Supplier<T> exec) {
        return BuildBy(true, "success", "操作成功", exec.get());
    }

    public static final <T> BaseDto<T> BuildSuccessBy(T entity) {
        return BuildBy(true, "success", "操作成功", entity);
    }

    @ApiModelProperty("是否成功")
    protected final boolean ok;
    @ApiModelProperty("编码")
    protected final String code;
    @ApiModelProperty("名称")
    protected final String name;
    @ApiModelProperty("携带实体")
    protected T entity;

    public BaseDto(boolean ok, String code, String name) {
        this(ok, code, name, null);
    }

    public BaseDto(boolean ok, String code, String name, T entity) {
        super(ok, code, name, entity);
        this.ok = ok;
        this.code = code;
        this.name = name;
        this.entity = entity;
    }


    public boolean isOk() {
        return ok;
    }

    public String getCode() {
        return code;
    }

    public String getName() {
        return name;
    }

    public void setEntity(T entity) {
        this.entity = entity;
    }

    public T getEntity() {
        return entity;
    }

    @Override
    public String toString() {
        return JSON.toJSONString(this);
    }
}

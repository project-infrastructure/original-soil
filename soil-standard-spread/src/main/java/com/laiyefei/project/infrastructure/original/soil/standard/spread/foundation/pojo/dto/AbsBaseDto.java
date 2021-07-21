package com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto;

import java.util.function.Supplier;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础传输对象
 * @Version : v2.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
public abstract class AbsBaseDto<T> extends AbsDto {
    public static <T> AbsBaseDto<T> BuildBy(boolean ok, String code, String name) {
        return new AbsBaseDto<T>(ok, code, name) {
        };
    }

    public static <T> AbsBaseDto<T> BuildBy(boolean ok, String code, String name, T entity) {
        return new AbsBaseDto<T>(ok, code, name, entity) {
        };
    }

    public static <T> AbsBaseDto<T> BuildSuccessBy(String code, String name) {
        return BuildBy(true, code, name);
    }

    public static <T> AbsBaseDto<T> BuildFailedBy(String code, String name) {
        return BuildBy(false, code, name);
    }

    public static <T> AbsBaseDto<T> BuildSuccessBy(String code, String name, T entity) {
        return BuildBy(true, code, name, entity);
    }

    public static <T> AbsBaseDto<T> BuildFailedBy(String code, String name, T entity) {
        return BuildBy(false, code, name, entity);
    }

    public static <T> AbsBaseDto<T> BuildBy(boolean ok) {
        return ok ? AbsBaseDto.BuildSuccess() : AbsBaseDto.BuildFailed();
    }

    public static <T> AbsBaseDto<T> BuildFailed() {
        return BuildBy(false, "failed", "操作失败");
    }

    public static <T> AbsBaseDto<T> BuildSuccess() {
        return BuildBy(true, "success", "操作成功");
    }

    public static <T> AbsBaseDto<T> BuildSuccessBy(Supplier<T> exec) {
        return BuildBy(true, "success", "操作成功", exec.get());
    }

    protected final boolean ok;
    protected final String code;
    protected final String name;
    protected T entity;

    public AbsBaseDto(boolean ok, String code, String name) {
        this(ok, code, name, null);
    }

    public AbsBaseDto(boolean ok, String code, String name, T entity) {
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
}

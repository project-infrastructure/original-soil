package com.laiyefei.project.infrastructure.original.soil.dependencies.ssmp.foundation.pojo.po;

import com.baomidou.mybatisplus.annotation.FieldFill;
import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableField;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.extension.activerecord.Model;
import com.laiyefei.project.infrastructure.original.soil.standard.foundation.pojo.po.IPo;
import com.laiyefei.project.infrastructure.original.soil.standard.spread.foundation.pojo.dto.AbsDto;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.lang.reflect.Field;
import java.sql.Timestamp;

/**
 * @Author : leaf.fly(?)
 * @Create : 2020-08-29 18:09
 * @Desc : 基础po对象
 * @Version : v1.0.0.20200829
 * @Blog : http://laiyefei.com
 * @Github : http://github.com/laiyefei
 */
@Data
@EqualsAndHashCode(callSuper = false)
public abstract class AbsBasePo<DTO extends AbsDto> extends Model<AbsBasePo<?>> implements IPo<DTO> {
    public static final String CREATE_TIME = "create_time";
    public static final String UPDATE_TIME = "update_time";
    public static final String CREATOR_ID = "creator_id";
    public static final String UPDATOR_ID = "updator_id";

    public static final String getFieldCodeBy(final String tableFieldValue) {
        final Field[] fields = AbsBasePo.class.getDeclaredFields();
        for (Field field : fields) {
            if (!field.isAnnotationPresent(TableField.class)) {
                continue;
            }
            TableField tableField = field.getAnnotation(TableField.class);
            if (tableField.value().equals(tableFieldValue)) {
                return field.getName();
            }
        }
        throw new RuntimeException("error: no such match ".concat(tableFieldValue));
    }

    @TableId(type = IdType.ASSIGN_ID)
    protected String id;

    @TableField(value = CREATE_TIME, fill = FieldFill.INSERT)
    protected Timestamp createTime;
    @TableField(value = UPDATE_TIME, fill = FieldFill.INSERT_UPDATE)
    protected Timestamp updateTime;
    @TableField(value = CREATOR_ID, fill = FieldFill.INSERT)
    protected String creatorId;
    @TableField(value = UPDATOR_ID, fill = FieldFill.INSERT_UPDATE)
    protected String updatorId;

    @Override
    protected Serializable pkVal() {
        return this.id;
    }

    public abstract DTO buildDto();

}

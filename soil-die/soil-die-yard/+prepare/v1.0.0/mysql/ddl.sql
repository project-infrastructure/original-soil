create table permission
(
    id varchar(64) not null comment '主键id',
    create_time timestamp null comment '创建时间',
    updator_id varchar(64) null comment '更新人Id',
    update_time timestamp null,
    creator_id varchar(64) null comment '创建Id',
    name varchar(128) not null comment '权限名称',
    path varchar(512) null comment '接口路径',
    method varchar(16) null comment '请求方式',
    access_type varchar(8) not null comment '访问类型',
    type varchar(8) not null comment '权限类型',
    constraint permission_id_uindex
        unique (id),
    constraint permission_link_path
        unique (method, path)
)
    comment '权限表';

alter table permission
    add primary key (id);

create table role
(
    id varchar(64) not null comment '主键id',
    create_time timestamp null comment '创建时间',
    update_time timestamp null comment '更新时间',
    creator_id varchar(64) null comment '创建人Id',
    updator_id varchar(64) null comment '更新人Id',
    code varchar(128) not null comment '编码',
    name varchar(128) not null comment '名称',
    constraint role_code_uindex
        unique (code),
    constraint role_id_uindex
        unique (id)
)
    comment '角色信息表';

alter table role
    add primary key (id);

create table role_permission
(
    id varchar(64) not null comment '主键id',
    create_time timestamp not null comment '创建时间',
    creator_id varchar(64) not null comment '创建人id',
    update_time timestamp not null comment '更新时间',
    updator_id varchar(64) not null comment '更新人id',
    fk_role_id varchar(64) not null comment '外键角色id',
    fk_permission_id varchar(64) not null comment '外键权限id',
    constraint role_permission_id_uindex
        unique (id)
)
    comment '角色权限表';

alter table role_permission
    add primary key (id);

create table user
(
    id varchar(64) not null comment '主键id',
    create_time timestamp null comment '创建时间',
    update_time timestamp null comment '更新时间',
    creator_id varchar(64) null comment '创建人Id',
    updator_id varchar(64) null comment '更新人Id',
    account varchar(128) not null comment '账号',
    name varchar(128) not null comment '姓名',
    password varchar(1024) not null comment '密码密文',
    plain_text varchar(512) not null comment '明文密码',
    salt varchar(6) null,
    constraint user_account_uindex
        unique (account),
    constraint user_id_uindex
        unique (id)
)
    comment '用户信息';

alter table user
    add primary key (id);

create table user_role
(
    id varchar(64) not null comment '主键id',
    create_time timestamp not null comment '创建时间',
    creator_id varchar(64) not null comment '创建人id',
    update_time timestamp not null comment '更新时间',
    updator_id varchar(64) not null comment '更新人id',
    fk_user_id varchar(64) not null comment '用户id',
    fk_role_id varchar(64) not null comment '角色id',
    constraint user_role_id_uindex
        unique (id)
)
    comment '用户角色表';

alter table user_role
    add primary key (id);


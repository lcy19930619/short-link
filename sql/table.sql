create table short_link_record
(
    id             bigint auto_increment comment '数据id' primary key,
    uid            bigint                             not null comment '所属用户id',
    original_url   varchar(4096)                      not null comment '原始链接，非空',
    effective      bit      default b'1'              null comment '是否有效，防止链接泄露导致出现问题true有效，false失效',
    remark         varchar(50)                        null comment '备注信息',
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    short_link_key char(15)                           not null comment '短链接key，长度15，非空，唯一',
    snowflake_id   bigint                             not null comment '雪花id',
    constraint uni_short_link_key unique (short_link_key),
    constraint uni_snowflake_id unique (snowflake_id)
)
    comment '短链接记录表' charset = utf8;


create table click_record
(
    id             bigint auto_increment comment '数据id' primary key,
    create_time    datetime default CURRENT_TIMESTAMP null comment '创建时间',
    snowflake_id   bigint                             not null comment '雪花id'
)
    comment '点击记录表' charset = utf8;





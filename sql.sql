use onlineSurvey;
alter database onlineSurvey CHARACTER SET utf8 COLLATE utf8_general_ci;


drop  table  base_user;
create table base_user
(
    id       bigint primary key auto_increment comment '标识列',
    nickname varchar(20) comment '用户姓名',
    account  varchar(20) not null comment '登陆账号',
    password varchar(20) not null comment '密码',
    role_id  int         not null comment '角色id 1.用户 2.管理员 3.平台',
    email    varchar(200) comment '邮箱地址',
    phone    varchar(13)  comment '手机号码',
    is_del   tinyint default 0 comment '0未删除 1.删除'
) comment '用户表';

drop table inquiry;

create table inquiry
(
    id          bigint primary key auto_increment comment '标识列',
    inquiry_name   varchar(20)  not null comment '问卷名字',
    topic_name  varchar(20)  not null comment '问卷题目',
    create_time bigint       not null comment '创建时间',
    end_time bigint       not null comment '结束时间',
    is_del      tinyint default 0 comment '0未删除 1.删除'
) comment '调查表';

drop table inquiry_record;
create table inquiry_record
(
    id           bigint primary key auto_increment comment '标识列',
    inquiry_id bigint not null  comment '调查id',
    answer  bigint not null  comment '回答内容',
    create_time bigint       not null comment '创建时间',
    end_time bigint       not null comment '结束时间',
    is_del       tinyint default 0 comment '0未删除 1.删除'
) comment '调查表-记录';




create database onlineSurvey;
use onlineSurvey;
alter database onlineSurvey CHARACTER SET utf8 COLLATE utf8_general_ci;


drop table base_user;
create table base_user
(
    id       bigint primary key auto_increment comment '标识列',
    nickname varchar(20) comment '用户姓名',
    account  varchar(20) not null comment '登陆账号',
    password text        not null comment '密码',
    role_id  int         not null comment '角色id 1.用户 2.管理员 3.平台',
    phone    varchar(13) comment '手机号码',
    is_del   tinyint default 0 comment '0未删除 1.删除'
) comment '用户表';

drop table inquiry;

create table inquiry
(
    id           bigint primary key auto_increment comment '标识列',
    inquiry_name varchar(20) not null comment '问卷名字',
    topic_name   varchar(20) not null comment '问卷题目',
    user_id      bigint      not null comment '发布人id',
    user_name    varchar(20) not null comment '发布人名称',
    status       tinyint     not null comment '1.审核中 2.未开始 3.进行中 4.已结束 5.审核失败 ',
    create_time  bigint      not null comment '创建时间',
    end_time     bigint      not null comment '结束时间',
    is_del       tinyint default 0 comment '0未删除 1.删除'
) comment '调查表';

drop table inquiry_record;
create table inquiry_record
(
    id           bigint primary key auto_increment comment '标识列',
    inquiry_id   bigint       not null comment '调查id',
    inquiry_name varchar(20)  not null comment '问卷名字',
    topic_name   varchar(20)  not null comment '问卷题目',
    answer       varchar(255) not null comment '回答内容',
    user_id      bigint       not null comment '参与人id',
    user_name    varchar(20)  not null comment '参与人名称',
    create_time  bigint       not null comment '创建时间',
    end_time     bigint       not null comment '结束时间',
    is_del       tinyint default 0 comment '0未删除 1.删除'
) comment '调查表-记录';




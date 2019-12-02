create table base_user(
    id bigint primary key  auto_increment comment '标识列',
    nickname varchar(20) comment '用户姓名',
    account varchar(20) not null comment '登陆账号',
    password varchar(20) not null comment  '密码',
    role_id  int  not null  comment '角色id 1.信管学生 2.计算机学生 3.教师  4.超级管理员',
    email varchar(200)  comment  '邮箱地址',
    phone varchar(13) comment '手机号码',
    is_del tinyint default 0 comment '0未删除 1.删除'
)comment '用户表';

drop table book;

create table book (
    id bigint primary key  auto_increment comment '标识列',
    book_no  varchar(255) not null  comment  '图书编号',
    book_name varchar(20) not null comment  '图书名字',
    book_type int not null  comment '图书分类 1.文学 2.科幻 3.漫画',
    press_name varchar(20) not null  comment  '出版社名字',
    author_name varchar(20) not null  comment  '作者名字',
    status tinyint default  0  comment  '借阅状态 o.未借阅 1。借阅中',
    create_time bigint not null  comment  '创建时间',
    is_del tinyint default 0 comment '0未删除 1.删除'
)comment '图书表';

drop table borrowing_record;
create table borrowing_record(
    id bigint primary key  auto_increment comment '标识列',
    borrowing_no varchar(255) not null  comment  '借阅编号',
    book_id bigint not null comment '图书id',
    user_id bigint not null comment '用户id',
    user_name varchar(255) not null comment '借阅人',
    book_name varchar(255) not null comment '书名',
    fine_money bigint not null comment '逾期金额',
    renewal_num int not null  comment '续租次数',
    status tinyint default  0  comment  '借阅状态 0.申请中 1.借阅中 2。已归还  3.逾期 4.已拒绝',
    create_time bigint not null comment  '创建时间',
    end_time bigint not null  comment '借阅截止时间',
    is_del tinyint default 0 comment '0未删除 1.删除'
)comment '图书借阅-记录';


create table admin_setting(
    id bigint primary key  auto_increment comment '标识列',
    code varchar(255) not null  comment  'code码',
    value varchar(255) not null  comment  '值',
    create_time bigint not null comment  '创建时间',
    update_time bigint not null comment  '修改时间',
    update_admin varchar(255) not null  comment '修改人',
    is_del tinyint default 0 comment '0未删除 1.删除'
)comment '后台设置表';



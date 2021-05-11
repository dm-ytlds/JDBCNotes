drop table if exists t_user;

/*==============================================================*/
/* Table: t_user                                                */
/*==============================================================*/
create table t_user
(
   id                   bigint not null AUTO_INCREMENT,
   userName             varchar(255),
   userPwd              varchar(255),
   primary key (id)
);
commit;

insert into t_user(userName,userPwd) values('aaa','1233');
insert into t_user(userName,userPwd) values('bbb','1233');
insert into t_user(userName,userPwd) values('ccc','1233');

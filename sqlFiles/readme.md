在写sql语句时的注意事项：
 	1. 如果创建表的时候用了auto_increment自动编号关键字，在insert语句中就不需要将该列手动输入，例如：

```mysql
drop table if exists t_user;

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
```

2. 善于利用 drop table if exists t_user; 这样的语句，谨防表已存在，而出错。

--> 使用PowerDesigner软件对数据表进行可视化设计。


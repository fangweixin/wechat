create database weixin;
create user weixin;
set password for weixin@'%'=password('soft02');
grant all privileges on weixin.* to weixin@'%';

create table user (
  id int primary key AUTO_INCREMENT,
  name varchar(16),
  password varchar(32),
  gender varchar(2) default null,
  telephone varchar(14),
  address varchar(64),
  birthday date default null,
  headUrl varchar(300),
  problem varchar(100),
  ip varchar(128),
  weixinhao varchar(20)
);

create table comments (
	id int primary key not null,
	userid int,
	content varchar(800),
	ctime datetime not null
);

--创建图片表，保存发朋友圈带的图片
if exists (select * from sysobjects where name = 'picture') drop table picture;
create table picture (
	pid int,
	path varchar(300)
);


--创建friends表，保存好友关系
create table friends(
    id int primary key auto_increment,
	A int not null,
	B int not null,
	AseeB tinyint,
	BseeA tinyint,
	info varchar(32),
	datatime datetime,
	frdfg varchar(10)
);

if exists (select name from sysobjects where name='message') drop table message;
--创建message表，记录消息
create table message(
	mid int primary key,
	sender int not null,
	receiver int not null,
	mcontent varchar(140) not null,
	mtime datetime not null
);

--添加表friendship,message,comment,picture的外键
alter table message add constraint fk_message_sender foreign key (sender) references useraccount(uid);
alter table message add constraint fk_message_receiver foreign key (receiver) references useraccount(uid);
--添加默认值约束
--alter table friendship  add constraint df_friendship_bseea default (1) for bseea with values;
alter table friendship alter bseea set default (1);
--alter table friendship  add constraint df_friendship_isfriend default (0) for isfriend with values;
alter table friendship alter isfriend set default (0);
--添加唯一性约束
alter table friendship  add constraint uq_friendship_userab unique(usera, userb);
--创建视图
--create view view_user as select uid, telephone, nickname, appnumber from useraccount;
/*
create view view_friends
	as select friendid fid, nickname userA, telephone telephoneA, nickname userB, telephone telephoneB
from useraccount, friendship
where isfriend = 1;
*/
--创建索引
create index idx_message on message(mid desc);
--创建登录
--create login dba with password='Admin888', default_database=wechat;
--创建用户
--create user dba for login dba with default_schema=wecaht;
--赋予权限
--exec sp_addrolemember 'db_owner', 'dba';
/*
--添加事务
SET XACT_ABORT ON
BEGIN TRANSACTION
update friendship set isfriend=1 where usera=7 and userb=11;
insert into friendship(usera,userb,bseea,isfriend,infomation) values(11,7,1,1,null);
COMMIT TRANSACTION

SET XACT_ABORT ON
BEGIN TRANSACTION
insert into comment(senderid,content,ctime) values(1,'dhjfbhj',getdate());
insert into picture(pid,path) values(3,'http://www.picture.com/2.png');
COMMIT TRANSACTION

truncate table friendship;
*/

--添加用户
create login dba with password='abcd1234@', default_database=mydb;
create user dba for login dba with default_schema=dbo;
exec sp_addrolemember 'db_owner', 'dba';

--向表中插入记录
--insert into message(sender,receiver,mcontent,mtime) values(7,11,'haha',getdate());
--insert into useraccount(password,nickname,telephone,gender,birthday,wechatnumber,profile,address,ip) values('123456','tom','33333333','女','2010-5-3','xxx','http://www.picture.com/1.png','河南','192.168.1.10');
--insert into comment(senderid,content,ctime) values(1,'dhjfbhj',getdate());
--insert into
--select dbo.getoptimizedtime(mtime) from message;
--if exists (select name from sysobjects where name='friend') drop table friend go

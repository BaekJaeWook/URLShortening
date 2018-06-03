create table url_map
(
	shortening_key varchar(40) primary key,
	long_url varchar(1000) not null,
	create_date date not null,
	expiry_date date not null
);

create table dd
(
	id varchar(50) primary key,
	message varchar(200) not null,
	create_date date not null,
	modify_date date
);

insert into dd (id, message, create_date, modify_date) values ('NOT_EXIST', 'NOT_EXIST', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('EMPTY_MESSAGE', '입력란에 단축하시려는 URL을 입력해주세요.', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('INVALID_MESSAGE', 'http:// 혹은 https://로 시작하는 유효한 URL을 입력해주세요.', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('SUCCESS', 'success', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('FAIL', 'fail', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('EXPIRY_DATE', '14', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('HASH_COLLISION', 'Hash Collision', sysdate, sysdate);
insert into dd (id, message, create_date, modify_date) values ('LONG_KEY_TOO_LARGE', 'Too Large LongKey', sysdate, sysdate);
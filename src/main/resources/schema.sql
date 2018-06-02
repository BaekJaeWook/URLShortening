create table url_map
(
	shortening_key varchar(40) primary key,
	long_url varchar(200) not null,
	create_date date not null
);
CREATE TABLE tb_user (
	id UUID NOT NULL PRIMARY KEY,
	email varchar(200) not null unique,
	password varchar(300) not null
);
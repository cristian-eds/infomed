CREATE TABLE IF NOT EXISTS person (
	id UUID NOT NULL PRIMARY KEY,
	name varchar(200) not null,
	access_code varchar(8),
	id_user_father UUID NOT NULL REFERENCES tb_user(id)
);
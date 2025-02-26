create table historic_log(
	id UUID PRIMARY KEY NOT NULL,
	action VARCHAR(20) NOT NULL CHECK (action IN ('GET', 'POST', 'PUT', 'DELETE')),
	description VARCHAR(100),
	date_hour TIMESTAMP WITH TIME ZONE NOT NULL,
	id_user UUID REFERENCES tb_user(id)
);
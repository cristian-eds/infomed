CREATE TYPE action_type AS ENUM ('GET', 'PUT', 'POST', 'DELETE');

create table historic_log(
	id UUID PRIMARY KEY NOT NULL,
	action action_type NOT NULL,
	description VARCHAR(100),
	date_hour TIMESTAMP WITH TIME ZONE NOT NULL,
	id_user UUID REFERENCES tb_user(id)
);
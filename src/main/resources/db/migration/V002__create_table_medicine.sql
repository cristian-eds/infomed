create table medicine (
	id UUID PRIMARY KEY NOT NULL,
	name VARCHAR(200) NOT NULL,
	total_days DOUBLE PRECISION NOT NULL,
	frequency_hours DOUBLE PRECISION NOT NULL,
	registration_date TIMESTAMP WITH TIME ZONE,
	atualization_date TIMESTAMP WITH TIME ZONE,
	id_user UUID REFERENCES tb_user(id)
);

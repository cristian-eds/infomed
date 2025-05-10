ALTER TABLE tb_user
ADD COLUMN role VARCHAR(20);

ALTER TABLE tb_user
ADD CONSTRAINT chk_role CHECK (role IN ('USER','ADMIN'));

ALTER TABLE tb_user
ALTER COLUMN role SET DEFAULT 'USER';

UPDATE tb_user
SET
	role = 'ADMIN'
WHERE
	role is null;
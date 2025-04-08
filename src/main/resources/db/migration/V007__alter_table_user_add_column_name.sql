alter table tb_user
add column name varchar(150);

update tb_user
set name = 'user'
where name is null;

ALTER TABLE tb_user
ALTER COLUMN name SET NOT NULL;

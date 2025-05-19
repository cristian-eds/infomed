ALTER TABLE medicine
ADD COLUMN id_person UUID REFERENCES person(id);
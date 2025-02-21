CREATE TABLE MEDICINE_ITEM(
	id UUID PRIMARY KEY NOT NULL,
	medicine_item_sequence int NOT NULL,
	day_hour TIMESTAMP NOT NULL,
	conclusion bool default 'false',
	id_medicine UUID NOT NULL REFERENCES medicine(id) ON DELETE CASCADE
);
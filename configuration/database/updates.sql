GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO restaurant_app_user;
grant postgres to restaurant_app_user;
alter table item ADD COLUMN image varchar(150);

-- 18.11.
alter table Bill_item ADD COLUMN parent_id integer NULL;
ALTER TABLE Bill_item ADD CONSTRAINT FK_Bill_item_parent
	FOREIGN KEY (parent_id) REFERENCES Bill_item (bill_item_id) ON DELETE No Action ON UPDATE No Action
;
alter table category ADD COLUMN priority integer NULL;
UPDATE category SET priority = 1 WHERE code = 'HLAVNI_JIDLO';
UPDATE category SET priority = 2 WHERE code = 'POLEVKA';
UPDATE category SET priority = 3 WHERE code = 'MASO';
UPDATE category SET priority = 4 WHERE code = 'PRILOHA';
UPDATE category SET priority = 5 WHERE code = 'NEALKOHOLICKE_NAPOJE';
UPDATE category SET priority = 6 WHERE code = 'PIVO';
UPDATE category SET priority = 7 WHERE code = 'PANAKY';

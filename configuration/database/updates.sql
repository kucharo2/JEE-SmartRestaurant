GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO restaurant_app_user;
grant postgres to restaurant_app_user;
alter table item ADD COLUMN image varchar(150);

-- 18.11.
alter table Bill_item ADD COLUMN parent_id integer;
ALTER TABLE Bill_item ADD CONSTRAINT FK_Bill_item_parent
	FOREIGN KEY (parent_id) REFERENCES Bill_item (bill_item_id) ON DELETE No Action ON UPDATE No Action
;
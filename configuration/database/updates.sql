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

--18.11 18:10
alter table account ADD COLUMN email VARCHAR(150) NULL;
alter table account ADD COLUMN phone VARCHAR(13) NOT NULL;

----18.11 18:43 user roles and anonymous_customer (username: anonymous, pass:cvut2017)
insert into account_role (role_id) values ('COOKER');
insert into account_role (role_id) values ('REGISTERED_CUSTOMER');
insert into account_role (role_id) values ('ANONYMOUS_CUSTOMER');
insert into account (username, password, role_id, phone)
values ('anonymous', 'ffa266cf9a65a1b19cf4f5d106498e61', 'ANONYMOUS_CUSTOMER', '123');

-- 19.11 add created column
ALTER table bill_item add COLUMN created TIMESTAMP DEFAULT now();
-- 19.11. update incorrect data on bill
UPDATE bill SET status = 'CANCELED' WHERE status = 'CREATED';
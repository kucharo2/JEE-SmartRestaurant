GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO restaurant_app_user;
grant postgres to restaurant_app_user;
alter table item ADD COLUMN image varchar(150);
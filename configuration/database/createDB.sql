-- Run this script on System database to create user and empty database

CREATE USER restaurant_app_user WITH PASSWORD 'DoIt4Tweety';

CREATE DATABASE smart_restaurant
  WITH ENCODING='UTF8'
       OWNER=restaurant_app_user
       CONNECTION LIMIT=-1;

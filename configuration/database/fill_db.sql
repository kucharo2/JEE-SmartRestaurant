delete from restaurant_table;
delete from item_combination;
delete from item;
delete from category;

/* RESTAURANT TABLES */
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'VIP',4);
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'dvojka',8);
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'samotka',2);
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'trojka',4);
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'ctykra',6);
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'na stojaka',4);
INSERT INTO restaurant_table (table_id, name,size) values (nextval('Restaurant_table_SEQ'), 'petka',5);

/* CATEGORY */
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),null, 'jídlo', 'MAIN_FOOD');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),null, 'pití', 'DRINKS');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='MAIN_FOOD'), 'příloha','PRILOHA');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='MAIN_FOOD'), 'hlavní jídlo','HLAVNI_JIDLO');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='MAIN_FOOD'), 'maso','MASO');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='MAIN_FOOD'), 'polévka','POLEVKA');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='DRINKS'), 'pivo','PIVO');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='DRINKS'), 'nealkoholický nápoje','NEALKOHOLICKE_NAPOJE');
INSERT INTO category (category_id, parent_id, category_name, code) values (nextval('Category_SEQ'),(SELECT category_id FROM category where code='DRINKS'), 'panáky','PANAKY');

/* DISH */
-- INSERT INTO item (item_id, name, price, category_id) values (nextval('Item_SEQ'), 'Kuře DIPLOMAT', 80,2);
-- INSERT INTO item (item_id, name, price, category_id) values (nextval('Item_SEQ'), 'Kuřecí řízek přírodní',65,3);
-- INSERT INTO item (item_id, name, price, category_id) values (nextval('Item_SEQ'), 'Smažený kuřecí řízek',65,3);
-- delete from item;
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kuře DIPLOMAT', 80,2+2,'DIPLOMAT');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kuřecí řízek přírodní',65,3+2,'KURECI_RIZEK_PRIRODNI');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Smažený kuřecí řízek',65,3+2,'SMAZENY_KURECI_RIZEK');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kuřecí řízek s bilinkovou omáčkou',70,3+2,'KURECI_RIZEK_BILINKOVA_OMACKA');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kuřecí medailonky',70,3+2,'KURECI_MEDAILONKY');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kuřecí kapsa',85,3+2,'KURECI_KAPSA');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Hovězí řízek',80,3+2,'HOVEZI_STEAK');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Tatarák',105,2+2,'TATARAK');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Smažený sýr',35,3+2,'SMAZENY_SYR');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kung-Pao',80,2+2,'KUNGPAO');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Hranolky',7,1+2,'HRANOLKY');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Americké brambory',12,1+2,'AMERICKE_BRAMBORY');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Rýže',10,1+2,'RYZE');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Kari Rýže',12,1+2,'KARI_RYZE');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Krokety',14,1+2,'KROKETY');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Brambory',10,1+2,'BRAMBORY');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Svíčková',95,2+2,'SVICKOVA');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Knedlíky',15,1+2,'KNEDLIKY');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Mexické fazole',76,2+2,'MEXICKE_FAZOLE');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Chleba 1ks',5,1+2,'CHELBA');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Česneková polévka',24,4+2,'CESNEKOVA_POLEVKA');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Rohlík 1ks',3,1+2,'ROHLIK');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Tatarská omáčka',10,1+2,'TATARSKA_OMACKA');
INSERT INTO item (item_id, name, price, category_id, code) values (nextval('Item_SEQ'), 'Gulášová polévka',23,4+2,'GULASOVA_POLEVKA');

/* DRINK */
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kozel 10° 0.5L',25,7,'KOZEL_10_VELKY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kozel 10° 0.3L',17,7,'KOZEL_10_MALY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kozel 11° 0.5L',29,7,'KOZEL_11_VELKY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kozel 11° 0.3L',20,7,'KOZEL_11_MALY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kozel nefiltr. 0.5L',32,7,'KOZEL_NEFILTR_VELKY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kozel nefiltr. 0.3L',22,7,'KOZEL_NEFILTR_MALY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Šokující Kozel',30,7,'SOKUJICI_KOZEL');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Big Shock 0.5L',40,8,'BIG_SHOCK_VELKY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Big Shock 0.3L',28,8,'BIG_SHOCK_MALY');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Coca cola',25,8,'COCACOLA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Coca cola light',25,8,'COCACOLA_LIGHT');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Matoni jemně perlivá 0.3L',15,8,'MATONI_JEMNE_PERLIVA_MALA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Matoni jemně perlivá 0.5L',22,8,'MATONI_JEMNE_PERLIVA_VELKA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Matoni ochucená 0.5L',24,8,'MATONI_OCHUCENA_VELKA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Matoni ochucená 0.3L',27,8,'MATONI_OCHUCENA_MALA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Amundsen vodka',25,9,'AMUNDSEN');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Pražská vodka',27,9,'PRAZSKA_VODKA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Božkov Rum',25,9,'BOZKOV_RUM');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Božkov Vodka',24,9,'BOZKOV_VODKA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Božkov Zelená',25,9,'BOZKOVA_ZELENA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Finlandia vodka',30,9,'FINLANDIA');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Božkov Citrus',25,9,'BOZKOV_CITRUS');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Božkov Zetko',25,9,'BOZKOV_ZETKO');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Becherovka orig.',25,9,'BECHEROVKA_ORIG');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Becherovka citrus',25,9,'BECHEROVKA_CISTRUS');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Amundsen vodka meloun',25,9,'AMUNDSEN_MELOUN');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Amundsen vodka peach',25,9,'AMUNDSEN_PEACH');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kofola točená 0.5L',25,8,'KOFOLA_0.5');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kofola točená 0.3L',15,8,'KOFOLA_0.4');
INSERT INTO item (item_id,name, price, category_id, code) values (nextval('Item_SEQ'), 'Kofola točená 0.4L',20,8,'KOFOLA_0.3');


INSERT INTO item_combination (item_1, item_2) VALUES
  (24-3,23-3),
  (24-3,25-3),
  (24-3,6-3),
  (24-3,7-3),
  (24-3,9-3),
  (24-3,12-3),
  (24-3,8-3),
  (24-3,5-3),
  (24-3,10-3),
  (24-3,22-3),
  (24-3,20-3),
  (24-3,13-3),
  (24-3,11-3),
  (24-3,4-3),
  (27-3,23-3),
  (27-3,25-3),
  (27-3,6-3),
  (27-3,7-3),
  (27-3,9-3),
  (27-3,12-3),
  (27-3,8-3),
  (27-3,5-3),
  (27-3,10-3),
  (27-3,22-3),
  (27-3,20-3),
  (27-3,13-3),
  (27-3,11-3),
  (27-3,4-3),
  (6-3,7-3),
  (6-3,9-3),
  (6-3,12-3),
  (6-3,8-3),
  (6-3,5-3),
  (6-3,10-3),
  (6-3,4-3),
  (6-3,25-3),
  (6-3,17-3),
  (6-3,26-3),
  (6-3,18-3),
  (6-3,9-3),
  (6-3,23-3),
  (6-3,16-3),
  (6-3,15-3),
  (6-3,14-3),
  (7-3,9-3),
  (7-3,12-3),
  (7-3,8-3),
  (7-3,5-3),
  (7-3,10-3),
  (7-3,4-3),
  (7-3,25-3),
  (7-3,17-3),
  (7-3,26-3),
  (7-3,18-3),
  (7-3,9-3),
  (7-3,23-3),
  (7-3,16-3),
  (7-3,15-3),
  (7-3,14-3),
  (9-3,12-3),
  (9-3,8-3),
  (9-3,5-3),
  (9-3,10-3),
  (9-3,4-3),
  (9-3,25-3),
  (9-3,17-3),
  (9-3,26-3),
  (9-3,18-3),
  (9-3,9-3),
  (9-3,23-3),
  (9-3,16-3),
  (9-3,15-3),
  (9-3,14-3),
  (12-3,8-3),
  (12-3,5-3),
  (12-3,10-3),
  (12-3,4-3),
  (12-3,25-3),
  (12-3,17-3),
  (12-3,26-3),
  (12-3,18-3),
  (12-3,9-3),
  (12-3,23-3),
  (12-3,16-3),
  (12-3,15-3),
  (12-3,14-3),
  (8-3,5-3),
  (8-3,10-3),
  (8-3,4-3),
  (8-3,25-3),
  (8-3,17-3),
  (8-3,26-3),
  (8-3,18-3),
  (8-3,9-3),
  (8-3,23-3),
  (8-3,16-3),
  (8-3,15-3),
  (8-3,14-3),
  (5-3,10-3),
  (5-3,4-3),
  (5-3,25-3),
  (5-3,17-3),
  (5-3,26-3),
  (5-3,18-3),
  (5-3,9-3),
  (5-3,23-3),
  (5-3,16-3),
  (5-3,15-3),
  (5-3,14-3),
  (10-3,4-3),
  (10-3,25-3),
  (10-3,17-3),
  (10-3,26-3),
  (10-3,18-3),
  (10-3,9-3),
  (10-3,23-3),
  (10-3,16-3),
  (10-3,15-3),
  (10-3,14-3),
  (22-3,25-3),
  (22-3,17-3),
  (22-3,23-3),
  (22-3,16-3),
  (20-3,21-3),
  (13-3,25-3),
  (13-3,17-3),
  (13-3,18-3),
  (13-3,23-3),
  (13-3,16-3),
  (13-3,15-3),
  (13-3,14-3),
  (11-3,23-3),
  (4-3,25-3),
  (4-3,17-3),
  (4-3,26-3),
  (4-3,18-3),
  (4-3,9-3),
  (4-3,23-3),
  (4-3,16-3),
  (4-3,15-3),
  (4-3,14-3),
  (25-3,26-3),
  (25-3,23-3),
  (17-3,16-3),
  (26-3,18-3),
  (26-3,9-3),
  (26-3,23-3),
  (26-3,15-3),
  (26-3,14-3),
  (18-3,9-3),
  (18-3,15-3),
  (18-3,14-3),
  (9-3,15-3),
  (9-3,14-3),
  (16-3,15-3),
  (16-3,14-3),
  (15-3,14-3);


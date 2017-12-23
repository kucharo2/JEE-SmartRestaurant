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
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kuře DIPLOMAT', 80,2+2,'DIPLOMAT', 'Chutné kuře, které umí řešit konflikty.', 'img/items/kure-diplomat.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kuřecí řízek přírodní',65,3+2,'KURECI_RIZEK_PRIRODNI', 'Pořádný přírodní plátek kuřete.', 'img/items/kureci-prirodni-rizek.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Smažený kuřecí řízek',65,3+2,'SMAZENY_KURECI_RIZEK', 'Smažen pomalu na slunečnicovém oleji.', 'img/items/smazeny-kureci-rizek.jpeg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kuřecí řízek s bilinkovou omáčkou',70,3+2,'KURECI_RIZEK_BILINKOVA_OMACKA', 'Přírodní kuřecí řízek zalitý omáčkou se směsí bylinek.', 'img/items/kureci-rizek-s-bylinkovou-omackou.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kuřecí medailonky',70,3+2,'KURECI_MEDAILONKY', 'Malé dozlatova smažené kuřecí kousky.', 'img/items/kureci-medailonky.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kuřecí kapsa',85,3+2,'KURECI_KAPSA', 'Kuřecí kapsa plněná uzeninou a sýrem', 'img/items/kureci-kapsa.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Hovězí řízek',80,3+2,'HOVEZI_STEAK', 'Ano ... řízek může být i hovězí.', 'img/items/hovezi-rizek.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Tatarák',105,2+2,'TATARAK', 'S vlastní přípravou. Podáváme s vajíčkem a různými druhy koření. Topinky nejsou v ceně.', 'img/items/tatarak.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Smažený sýr',35,3+2,'SMAZENY_SYR', 'Eidam nebo Gouda, dle nabídky.', 'img/items/smazeny-syr.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kung-Pao',80,2+2,'KUNGPAO', 'Klasický pokrm z Asijské kuchyně.', 'img/items/kung-pao.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Hranolky',7,1+2,'HRANOLKY', 'Hranolky jsou pečeny v troubě, nikoli ve fritovacím oleji.', 'img/items/hranolky.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Americké brambory',12,1+2,'AMERICKE_BRAMBORY', 'Pečené brambory se směsí grilovacích koření.', 'img/items/americke-brambory.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Rýže',10,1+2,'RYZE', 'Dlouhozrná rýže vařená doměkka.', 'img/items/ryze.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kari Rýže',12,1+2,'KARI_RYZE', 'Kari rýže se vyznačuje svou žlutou barvou po kari.', 'img/items/kari-ryze.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Krokety',14,1+2,'KROKETY', 'Příloha z brambor tvarovaná do kuliček', 'img/items/krokety.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Brambory',10,1+2,'BRAMBORY', 'Klasické vařené brambory.', 'img/items/brambory.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Svíčková',95,2+2,'SVICKOVA', 'Nejlepší hospodská svíčková s citronem, šlehačkou a brusinkovým terčem.', 'img/items/svickova.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Knedlíky',15,1+2,'KNEDLIKY', 'Co by to bylo za svíčkovou nebo gulášek bez houskových knedlíků.', 'img/items/knedliky.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Mexické fazole',76,2+2,'MEXICKE_FAZOLE', 'Patří mezi ostřejší pokrmy. Možno podávat i v tortilách', 'img/items/mexicke-fazole.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Chleba 1ks',5,1+2,'CHELBA', 'Kousek Šumavského chleba vždy přijde vhod.', 'img/items/chleba.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Česneková polévka',24,4+2,'CESNEKOVA_POLEVKA', 'Česnekačka s tvarůžky, vajíčkem a opečenými krutony.', 'img/items/cesnekova-polevka.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Rohlík 1ks',3,1+2,'ROHLIK', '42 gramový bílý rohlík.', 'img/items/rohlik.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Tatarská omáčka',10,1+2,'TATARSKA_OMACKA', 'Majonéza, do níž se přimíchává koření a zelenina.', 'img/items/tatarska-omacka.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Gulášová polévka',23,4+2,'GULASOVA_POLEVKA', 'Tradiční česká kuchyně. S kouskem pečiva nahradí hravě hlavní jídlo.', 'img/items/gulasova-polevka.jpg');

/* DRINK */
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kozel 10° 0.5L',25,7,'KOZEL_10_VELKY', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kozel 10° 0.3L',17,7,'KOZEL_10_MALY', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kozel 11° 0.5L',29,7,'KOZEL_11_VELKY', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kozel 11° 0.3L',20,7,'KOZEL_11_MALY', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kozel nefiltr. 0.5L',32,7,'KOZEL_NEFILTR_VELKY', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kozel nefiltr. 0.3L',22,7,'KOZEL_NEFILTR_MALY', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Šokující Kozel',30,7,'SOKUJICI_KOZEL', 'Vyráběno v pivovaru ve Velkých Popovicích.', 'img/items/kozel.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Big Shock 0.5L',40,8,'BIG_SHOCK_VELKY', 'Velký energetický nápoj. Vhodný pro studenty a sportovce.', 'img/items/big-shock.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Big Shock 0.3L',28,8,'BIG_SHOCK_MALY', 'Malý energetický nápoj. Vhodný pro studenty i sportovce.', 'img/items/big-shock.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Coca cola',25,8,'COCACOLA', 'Sladký nápoj hnědé barvy s obsahem kofeinu a bezkokainového výtažku koky', 'img/items/coca-cola.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Coca cola light',25,8,'COCACOLA_LIGHT', 'sladký nápoj hnědé barvy s obsahem kofeinu a bezkokainového výtažku koky s nižším obsahem cukru.', 'img/items/coca-cola.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Matoni jemně perlivá 0.3L',15,8,'MATONI_JEMNE_PERLIVA_MALA', 'Kde to žije, tam je Mattoni. Neochucená, jemně perlivá, velká.', 'img/items/mattoni.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Matoni jemně perlivá 0.5L',22,8,'MATONI_JEMNE_PERLIVA_VELKA', 'Kde to žije, tam je Mattoni. Neochucená, jemně perlivá, menší.', 'img/items/mattoni.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Matoni ochucená 0.5L',24,8,'MATONI_OCHUCENA_VELKA', 'Kde to žije, tam je Mattoni. Ochucená, velká.', 'img/items/mattoni.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Matoni ochucená 0.3L',27,8,'MATONI_OCHUCENA_MALA', 'Kde to žije, tam je Mattoni. Ochucená, menší.', 'img/items/mattoni.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Amundsen vodka',25,9,'AMUNDSEN', 'Výjimečná jemnost a křišťálová čistota jsou charakteristické pro Vodku Amundsen.', 'img/items/amundsen.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Pražská vodka',27,9,'PRAZSKA_VODKA', 'Typická česká vodka.', 'img/items/bozkov.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Božkov Rum',25,9,'BOZKOV_RUM', 'Typický český rum.', 'img/items/bozkov.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Božkov Vodka',24,9,'BOZKOV_VODKA', 'Levnjší vodka od firmy Božkov.','img/items/bozkov.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Božkov Zelená',25,9,'BOZKOVA_ZELENA', 'Co takhle cesta do lesa?','img/items/bozkov.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Finlandia vodka',30,9,'FINLANDIA', 'Finlandia Vodka je finská vodka vyráběná z šestiřadého ječmene', 'img/items/finlandia.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Božkov Citrus',25,9,'BOZKOV_CITRUS', 'Citrusový alkoholický nápoj.', 'img/items/bozkov.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Božkov Zetko',25,9,'BOZKOV_ZETKO', 'Bylinkový alkoholický nápoj.', 'img/items/bozkov.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Becherovka orig.',25,9,'BECHEROVKA_ORIG', 'Tradiční český bylinkový alkoholický nápoj.', 'img/items/becherovka.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Becherovka citrus',25,9,'BECHEROVKA_CISTRUS', 'Citrusová verze tradičního českého bylinkového alkoholického nápoje.', 'img/items/becherovka.png');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Amundsen vodka meloun',25,9,'AMUNDSEN_MELOUN', 'Výjimečná jemnost a křišťálová čistota jsou charakteristické pro Vodku Amundsen.', 'img/items/amundsen.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Amundsen vodka peach',25,9,'AMUNDSEN_PEACH', 'Výjimečná jemnost a křišťálová čistota jsou charakteristické pro Vodku Amundsen.', 'img/items/amundsen.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kofola točená 0.5L',25,8,'KOFOLA_0.5', '5dl něčeho, co když milujete, není to řešit.', 'img/items/kofola.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kofola točená 0.3L',15,8,'KOFOLA_0.4', '4dl něčeho, co když milujete, není to řešit.', 'img/items/kofola.jpg');
INSERT INTO item (item_id, name, price, category_id, code, description, image) values (nextval('Item_SEQ'), 'Kofola točená 0.4L',20,8,'KOFOLA_0.3', '3dl něčeho, co když milujete, není to řešit.', 'img/items/kofola.jpg');


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


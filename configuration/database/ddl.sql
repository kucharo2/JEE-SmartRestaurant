/* Drop Sequences for Autonumber Columns */

DROP SEQUENCE IF EXISTS Account_SEQ
;

DROP SEQUENCE IF EXISTS Order_SEQ
;

DROP SEQUENCE IF EXISTS Order_item_SEQ
;

DROP SEQUENCE IF EXISTS category_seq
;

DROP SEQUENCE IF EXISTS Item_SEQ
;

DROP SEQUENCE IF EXISTS item_combination_seq
;

DROP SEQUENCE IF EXISTS item_param_seq
;

DROP SEQUENCE IF EXISTS Reservation_SEQ
;

DROP SEQUENCE IF EXISTS Restaurant_Table_SEQ
;

DROP SEQUENCE IF EXISTS Review_SEQ
;

/* Drop Tables */

DROP TABLE IF EXISTS Account CASCADE
;

DROP TABLE IF EXISTS Account_role CASCADE
;

DROP TABLE IF EXISTS o_order CASCADE
;

DROP TABLE IF EXISTS Order_item CASCADE
;

DROP TABLE IF EXISTS Category CASCADE
;

DROP TABLE IF EXISTS Item CASCADE
;

DROP TABLE IF EXISTS Item_combination CASCADE
;

DROP TABLE IF EXISTS Item_param CASCADE
;

DROP TABLE IF EXISTS Reservation CASCADE
;

DROP TABLE IF EXISTS Restaurant_table CASCADE
;

DROP TABLE IF EXISTS Review CASCADE
;

/* Create Tables */

CREATE TABLE Account
(
	account_id integer NOT NULL   DEFAULT NEXTVAL(('Account_SEQ'::text)::regclass),
	username varchar(50) NOT NULL,
	password varchar(100) NOT NULL,
	first_name varchar(50) NULL,
	last_name varchar(50) NULL,
	role_id varchar(50) NOT NULL
)
;

CREATE TABLE Account_role
(
	role_id varchar(50) NOT NULL
)
;

CREATE TABLE o_order 
(
	order_id integer NOT NULL   DEFAULT NEXTVAL(('Order_SEQ'::text)::regclass),
	date date NOT NULL   DEFAULT now(),
	status varchar(50) NOT NULL DEFAULT 'CREATED',
	table_id integer NULL,
	account_id integer NULL
)
;

CREATE TABLE Order_item
(
	order_item_id integer NOT NULL   DEFAULT NEXTVAL(('Order_item_SEQ'::text)::regclass),
	paid boolean NOT NULL   DEFAULT false,
	price numeric NOT NULL,
	order_id integer NULL,
	item_id integer NULL,
	parent_id integer NULL,
	created TIMESTAMP DEFAULT now()
)
;

CREATE TABLE Category
(
	category_id integer NOT NULL   DEFAULT NEXTVAL(('category_seq'::text)::regclass),
	code varchar(50) NOT NULL,
	parent_id integer NULL,
	category_name varchar(100) NULL
)
;

CREATE TABLE Item
(
	item_id integer NOT NULL   DEFAULT NEXTVAL(('Item_SEQ'::text)::regclass),
	name varchar(50) NOT NULL,
	price numeric NOT NULL,
	category_id integer NOT NULL,
	description varchar(255) NULL,
	code varchar(50) NOT NULL
-- 	,image varchar(150) NOT NULL
)
;

CREATE TABLE Item_combination
(
	item_combination_id integer NOT NULL   DEFAULT NEXTVAL(('item_combination_seq'::text)::regclass),
	item_1 integer NOT NULL,
	item_2 integer NOT NULL
)
;

CREATE TABLE Item_param
(
	item_param_id integer NOT NULL   DEFAULT NEXTVAL(('item_param_seq'::text)::regclass),
	param_name varchar(50) NULL,
	param_value varchar(50) NULL,
	item_id integer NULL
)
;

CREATE TABLE Reservation
(
	reservation_id integer NOT NULL   DEFAULT NEXTVAL(('Reservation_SEQ'::text)::regclass),
	timeFrom date NOT NULL,
	timeTo date NOT NULL,
	customer_name varchar(100) NOT NULL,
	description varchar(255) NULL,
	mail varchar(50) NULL,
	phone_number varchar(15) NOT NULL,
	storno boolean NOT NULL   DEFAULT false,
	table_id integer NULL
)
;

CREATE TABLE Restaurant_table
(
	table_id integer NOT NULL   DEFAULT NEXTVAL(('Restaurant_table_SEQ'::text)::regclass),
	name varchar(50) NOT NULL,
	size numeric NOT NULL    -- Number of peoples that can sit behind the table.
)
;

CREATE TABLE Review
(
	review_id integer NOT NULL   DEFAULT NEXTVAL(('Review_SEQ'::text)::regclass),
	stars integer NOT NULL,
	text varchar(255) NULL,
	nickname varchar(50) NOT NULL,
	item_id integer NULL
)
;

/* Create Primary Keys, Indexes, Uniques, Checks */

ALTER TABLE Account ADD CONSTRAINT PK_Account
	PRIMARY KEY (account_id)
;

CREATE INDEX IXFK_Account_Account_table ON Account (role_id ASC)
;

ALTER TABLE Account_role ADD CONSTRAINT PK_Account_table
	PRIMARY KEY (role_id)
;

ALTER TABLE o_order ADD CONSTRAINT PK_Order
	PRIMARY KEY (order_id)
;

CREATE INDEX IXFK_Order_Account ON o_order (account_id ASC)
;

CREATE INDEX IXFK_Order_Table ON o_order (table_id ASC)
;

ALTER TABLE Order_item ADD CONSTRAINT PK_OrderItem
	PRIMARY KEY (order_item_id)
;

CREATE INDEX IXFK_Order_item_Item ON Order_item (item_id ASC)
;

CREATE INDEX IXFK_OrderItem_Order ON Order_item (order_id ASC)
;

ALTER TABLE Category ADD CONSTRAINT PK_Category
	PRIMARY KEY (category_id)
;

ALTER TABLE Category ADD CONSTRAINT UQ_Category_name UNIQUE (code)
;

CREATE INDEX IXFK_Category_Category ON Category (parent_id ASC)
;

ALTER TABLE Item ADD CONSTRAINT PK_Item
	PRIMARY KEY (item_id)
;

CREATE INDEX IXFK_Item_Category ON Item (category_id ASC)
;

CREATE INDEX IXFK_Item_Item ON Item (item_id ASC)
;

ALTER TABLE Item_combination ADD CONSTRAINT PK_Item_cmmbination
	PRIMARY KEY (item_combination_id)
;

CREATE INDEX IXFK_Item_cmmbination_Item ON Item_combination (item_1 ASC)
;

CREATE INDEX IXFK_Item_combination_Item ON Item_combination (item_2 ASC)
;

ALTER TABLE Item_param ADD CONSTRAINT PK_Item_param
	PRIMARY KEY (item_param_id)
;

CREATE INDEX IXFK_Item_param_Item ON Item_param (item_id ASC)
;

ALTER TABLE Reservation ADD CONSTRAINT PK_Reservation
	PRIMARY KEY (reservation_id)
;

CREATE INDEX IXFK_Reservation_Table ON Reservation (table_id ASC)
;

ALTER TABLE Restaurant_table ADD CONSTRAINT PK_Table
	PRIMARY KEY (table_id)
;

ALTER TABLE Review ADD CONSTRAINT PK_Review
	PRIMARY KEY (review_id)
;

CREATE INDEX IXFK_Review_Item ON Review (item_id ASC)
;

/* Create Foreign Key Constraints */

ALTER TABLE Account ADD CONSTRAINT FK_Account_Account_table
	FOREIGN KEY (role_id) REFERENCES Account_role (role_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE o_order ADD CONSTRAINT FK_Order_Account
	FOREIGN KEY (account_id) REFERENCES Account (account_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE o_order ADD CONSTRAINT FK_Order_Table
	FOREIGN KEY (table_id) REFERENCES Restaurant_table (table_id) ON DELETE Restrict ON UPDATE No Action
;

ALTER TABLE Order_item ADD CONSTRAINT FK_Order_item_Item
	FOREIGN KEY (item_id) REFERENCES Item (item_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Order_item ADD CONSTRAINT FK_OrderItem_Order
	FOREIGN KEY (order_id) REFERENCES o_order (order_id) ON DELETE Restrict ON UPDATE No Action
;

ALTER TABLE Category ADD CONSTRAINT FK_Category_Category
	FOREIGN KEY (parent_id) REFERENCES Category (category_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Item ADD CONSTRAINT FK_Item_Category
	FOREIGN KEY (category_id) REFERENCES Category (category_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Item_combination ADD CONSTRAINT FK_Item_cmmbination_Item
	FOREIGN KEY (item_1) REFERENCES Item (item_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Item_combination ADD CONSTRAINT FK_Item_combination_Item
	FOREIGN KEY (item_2) REFERENCES Item (item_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Item_param ADD CONSTRAINT FK_Item_param_Item
	FOREIGN KEY (item_id) REFERENCES Item (item_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Reservation ADD CONSTRAINT FK_Reservation_Table
	FOREIGN KEY (table_id) REFERENCES Restaurant_table (table_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Review ADD CONSTRAINT FK_Review_Item
	FOREIGN KEY (item_id) REFERENCES Item (item_id) ON DELETE No Action ON UPDATE No Action
;

ALTER TABLE Order_item ADD CONSTRAINT FK_Order_item_parent
	FOREIGN KEY (parent_id) REFERENCES Order_item (order_item_id) ON DELETE No Action ON UPDATE No Action
;

/* Create Table Comments, Sequences for Autonumber Columns */

CREATE SEQUENCE Account_SEQ INCREMENT 1 START 1
;

CREATE SEQUENCE Order_SEQ INCREMENT 1 START 1
;

CREATE SEQUENCE Order_item_SEQ INCREMENT 1 START 1
;

CREATE SEQUENCE category_seq INCREMENT 1 START 1
;

CREATE SEQUENCE Item_SEQ INCREMENT 1 START 1
;

CREATE SEQUENCE item_combination_seq INCREMENT 1 START 1
;

CREATE SEQUENCE item_param_seq INCREMENT 1 START 1
;

CREATE SEQUENCE Reservation_SEQ INCREMENT 1 START 1
;

COMMENT ON COLUMN Restaurant_table.size
	IS 'Number of peoples that can sit behind the table.'
;

CREATE SEQUENCE Restaurant_Table_SEQ INCREMENT 1 START 1
;

CREATE SEQUENCE Review_SEQ INCREMENT 1 START 1
;

GRANT ALL PRIVILEGES ON ALL TABLES IN SCHEMA public TO restaurant_app_user;
--
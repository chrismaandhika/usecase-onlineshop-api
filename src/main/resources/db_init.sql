-- public."member" definition

-- Drop table

-- DROP TABLE public."member";

CREATE TABLE public."member" (
	id bigserial NOT NULL,
	full_name varchar NOT NULL,
	va_number varchar NULL,
	CONSTRAINT member_pk PRIMARY KEY (id)
);


-- public.oauth2api_client definition

-- Drop table

-- DROP TABLE public.oauth2api_client;

CREATE TABLE public.oauth2api_client (
	id varchar NOT NULL,
	secret varchar NOT NULL,
	"role" varchar NOT NULL,
	CONSTRAINT oauth2api_client_pk PRIMARY KEY (id)
);

INSERT INTO public.oauth2api_client (id,secret,"role") VALUES
('my_client','ABCDEFG','CLIENT')
;

-- public.oauth2api_scope definition

-- Drop table

-- DROP TABLE public.oauth2api_scope;

CREATE TABLE public.oauth2api_scope (
	"role" varchar NOT NULL,
	"scope" varchar NOT NULL,
	CONSTRAINT oauth2api_scope_pk2 PRIMARY KEY (role, scope)
);

INSERT INTO public.oauth2api_scope ("role","scope") VALUES
('MEMBER','read')
,('MEMBER','write')
,('CLIENT','inquiry')
,('CLIENT','pay')
;


-- public.payment_method definition

-- Drop table

-- DROP TABLE public.payment_method;

CREATE TABLE public.payment_method (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	partner_code varchar NULL,
	CONSTRAINT payment_method_pk PRIMARY KEY (id)
);


-- public.product definition

-- Drop table

-- DROP TABLE public.product;

CREATE TABLE public.product (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	description text NULL,
	price int8 NOT NULL,
	CONSTRAINT product_pk PRIMARY KEY (id)
);


-- public.shipment_method definition

-- Drop table

-- DROP TABLE public.shipment_method;

CREATE TABLE public.shipment_method (
	id bigserial NOT NULL,
	"name" varchar NOT NULL,
	price int4 NOT NULL,
	CONSTRAINT shipment_method_pk PRIMARY KEY (id)
);


-- public.cart definition

-- Drop table

-- DROP TABLE public.cart;

CREATE TABLE public.cart (
	member_id int8 NOT NULL,
	last_checkout_init_time timestamp NULL,
	CONSTRAINT cart_pk PRIMARY KEY (member_id),
	CONSTRAINT cart_fk FOREIGN KEY (member_id) REFERENCES member(id)
);


-- public.cart_items definition

-- Drop table

-- DROP TABLE public.cart_items;

CREATE TABLE public.cart_items (
	id bigserial NOT NULL,
	cart_id int8 NOT NULL,
	product_id int8 NOT NULL,
	quantity int4 NOT NULL,
	is_in_checkout bool NOT NULL,
	"version" int8 NULL,
	CONSTRAINT cart__items_pk PRIMARY KEY (id),
	CONSTRAINT cart__items_fk FOREIGN KEY (cart_id) REFERENCES cart(member_id)
);


-- public.delivery_address definition

-- Drop table

-- DROP TABLE public.delivery_address;

CREATE TABLE public.delivery_address (
	id bigserial NOT NULL,
	member_id int8 NOT NULL,
	full_address varchar NOT NULL,
	CONSTRAINT delivery_address_pk PRIMARY KEY (id),
	CONSTRAINT delivery_address_fk FOREIGN KEY (member_id) REFERENCES member(id)
);


-- public.oauth2api_user definition

-- Drop table

-- DROP TABLE public.oauth2api_user;

CREATE TABLE public.oauth2api_user (
	username varchar NOT NULL,
	password_hash varchar NULL,
	"role" varchar NULL,
	member_id int8 NULL,
	CONSTRAINT oauth2api_user_pk PRIMARY KEY (username),
	CONSTRAINT oauth2api_user_fk FOREIGN KEY (member_id) REFERENCES member(id)
);

INSERT INTO public.oauth2api_user (username,password_hash,"role",member_id) VALUES
('my_member','$2a$10$GjRpCxT64lj708/k6G3rp.peqhIg8Fry.CXoELV3d1KU.cUwchFFO','MEMBER',1)
;


-- public.product_catalogue definition

-- Drop table

-- DROP TABLE public.product_catalogue;

CREATE TABLE public.product_catalogue (
	product_id int8 NOT NULL,
	"name" varchar NOT NULL,
	price int8 NOT NULL,
	available_stock int4 NOT NULL,
	star numeric NULL DEFAULT 0.0,
	CONSTRAINT product_category_pk PRIMARY KEY (product_id),
	CONSTRAINT product_category_fk FOREIGN KEY (product_id) REFERENCES product(id)
);


-- public.product_stock definition

-- Drop table

-- DROP TABLE public.product_stock;

CREATE TABLE public.product_stock (
	product_id int8 NOT NULL,
	original_stock int4 NOT NULL,
	reserved_stock int4 NOT NULL,
	"version" int8 NULL,
	CONSTRAINT product_stock_prmk PRIMARY KEY (product_id),
	CONSTRAINT product_stock_fk FOREIGN KEY (product_id) REFERENCES product(id)
);


-- public.checkout definition

-- Drop table

-- DROP TABLE public.checkout;

CREATE TABLE public.checkout (
	id bigserial NOT NULL,
	member_id int8 NOT NULL,
	address_id int8 NULL,
	shipment_method_id int8 NULL,
	payment_method_id int8 NULL,
	shipping_cost int4 NULL,
	total_item_price int8 NULL,
	status varchar NOT NULL,
	"version" int8 NULL,
	CONSTRAINT checkout_pk PRIMARY KEY (id),
	CONSTRAINT checkout_fk FOREIGN KEY (member_id) REFERENCES member(id),
	CONSTRAINT checkout_fk_1 FOREIGN KEY (address_id) REFERENCES delivery_address(id),
	CONSTRAINT checkout_fk_2 FOREIGN KEY (payment_method_id) REFERENCES payment_method(id) DEFERRABLE,
	CONSTRAINT checkout_fk_3 FOREIGN KEY (shipment_method_id) REFERENCES shipment_method(id)
);


-- public.checkout_items definition

-- Drop table

-- DROP TABLE public.checkout_items;

CREATE TABLE public.checkout_items (
	id bigserial NOT NULL,
	product_id int8 NOT NULL,
	quantity int4 NOT NULL,
	unit_price int8 NOT NULL,
	"version" int8 NULL,
	checkout_id int8 NULL,
	CONSTRAINT checkout_items_pk PRIMARY KEY (id),
	CONSTRAINT checkout_checkout_items_fk FOREIGN KEY (checkout_id) REFERENCES checkout(id) DEFERRABLE,
	CONSTRAINT checkout_items_fk FOREIGN KEY (product_id) REFERENCES product(id)
);


-- public.payment definition

-- Drop table

-- DROP TABLE public.payment;

CREATE TABLE public.payment (
	id bigserial NOT NULL,
	checkout_id int8 NULL,
	total_amount int8 NULL,
	virtual_account varchar NULL,
	status varchar NOT NULL,
	"version" int8 NULL,
	CONSTRAINT payment_pk2 PRIMARY KEY (id),
	CONSTRAINT payment_fk FOREIGN KEY (checkout_id) REFERENCES checkout(id)
);
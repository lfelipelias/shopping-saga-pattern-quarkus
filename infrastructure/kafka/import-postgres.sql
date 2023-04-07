drop table if exists order_info cascade;
drop table if exists order_product cascade;
drop table if exists payment cascade;

create table order_info (
        order_id int8 generated by default as identity,
        create_date timestamp,
        status varchar(255),
        status_id int8,
        total_order numeric(19, 2),
        user_id int8,
        primary key (order_id)
);

create table order_product (
        op_id int8 generated by default as identity,
        create_date timestamp,
        price numeric(19, 2),
        product_id int8,
        order_id int8,
        primary key (op_id)
);

create table payment (
    pay_id int8 generated by default as identity,
	create_date timestamp,
	order_id int8,
	status varchar(255),
	status_id int8,
	primary key (pay_id)
);

create table product (
        prod_id int8 generated by default as identity,
        create_date timestamp,
        name varchar(255),
        order_id int8,
        price numeric(19, 2),
        status varchar(255),
        status_id int8,
        primary key (prod_id)
);

alter table if exists order_product
       add constraint FKllpxfv7t1urk3jk80eoce2vc9
       foreign key (order_id)
       references order_info;


INSERT INTO order_info (user_id, status, status_id, create_date, total_order) VALUES (2, 'CREATED', 0, '2023-05-01', 1200);

INSERT INTO order_product (order_id, product_id, create_date, price) VALUES (1, 1, '2022-12-01', 900);
INSERT INTO order_product (order_id, product_id, create_date, price) VALUES (1, 2, '2022-11-05', 300);

INSERT INTO product (name, price, order_id, status, status_id, create_date) VALUES ('GAMER MONITOR', 900, 1, 'SELL_IN_PROGRESS', 2, '2022-05-05');
INSERT INTO product (name, price, order_id, status, status_id, create_date) VALUES ('SSD HD', 300, 1, 'SELL_IN_PROGRESS', 2, '2022-05-05');

INSERT INTO product (name, price, status, status_id, create_date) VALUES ('RYZEN PROCESSOR', 700, 'AVAILABLE', 1, '2022-05-05');


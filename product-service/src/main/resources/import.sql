create table product (
       prod_id bigint auto_increment,
       name varchar(255),
       price numeric(19,2),
       order_id int,
       create_date timestamp,
       status varchar(255),
       status_id int,
       primary key (prod_id)
);

-- PRODUCTS ASSIGNED TO A ORDER IN PROGRESS
INSERT INTO product (name, price, order_id, status, status_id, create_date) VALUES ('GAMER MONITOR', 900, 1, 'SELL_IN_PROGRESS', 2, '2022-05-05');
INSERT INTO product (name, price, order_id, status, status_id, create_date) VALUES ('SSD HD', 300, 1, 'SELL_IN_PROGRESS', 2, '2022-05-05');

-- PRODUCT AVAILABLE
--INSERT INTO product (name, price, status, status_id, create_date) VALUES ('KEYBOARD SET', 20, 1, 'AVAILABLE', 1, '2022-04-05');
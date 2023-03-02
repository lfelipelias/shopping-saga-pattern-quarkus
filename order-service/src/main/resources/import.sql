create table order_info (
       order_id bigint auto_increment,
       user_id int,
       create_date timestamp,
       total_order numeric(19,2),
       status varchar(255),
       status_id int,
       primary key (order_id)
);

INSERT INTO order_info (user_id, status, status_id, create_date, total_order) VALUES (2, 'CREATED', 0, '2023-05-01', 1200);

create table order_product (
        op_id bigint auto_increment,
        order_id bigint,
        create_date timestamp,
        product_id bigint,
        price numeric(19,2),
        primary key (op_id),
        CONSTRAINT FK_ORDER_INFO FOREIGN KEY (order_id) REFERENCES order_info(order_id)
);

INSERT INTO order_product (order_id, product_id, create_date, price) VALUES (1, 1, '2022-12-01', 900);
INSERT INTO order_product (order_id, product_id, create_date, price) VALUES (1, 2, '2022-11-05', 300);
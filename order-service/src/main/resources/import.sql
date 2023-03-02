create table order_info (
       order_id bigint auto_increment,
       user_id int,
       status varchar(255),
       status_id int,
       primary key (order_id)
);

INSERT INTO order_info (user_id, status, status_id) VALUES (2, 'CREATED', 0);

create table order_product (
        op_id bigint auto_increment,
        order_id bigint,
        product_id bigint,
        primary key (op_id),
        CONSTRAINT FK_ORDER_INFO FOREIGN KEY (order_id) REFERENCES order_info(order_id)
);

INSERT INTO order_product (order_id, product_id) VALUES (1, 1);
INSERT INTO order_product (order_id, product_id) VALUES (1, 2);
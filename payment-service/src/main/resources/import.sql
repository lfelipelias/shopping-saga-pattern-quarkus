create table payment (
       pay_id bigint auto_increment,
       order_id bigint,
       create_date timestamp,
       status varchar(255),
       status_id int,
       primary key (pay_id)
);
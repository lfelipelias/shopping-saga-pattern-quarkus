create table order_info (
       orderId bigint auto_increment,
       userId int,
       status varchar(255),
       statusId int,
       primary key (orderId)
);

INSERT INTO order_info (userId, status, statusId) VALUES (2, 'IN_PROGRESS', 1);

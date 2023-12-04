create database session13;
use session13;

create table category
(
    id     int primary key auto_increment,
    name   varchar(100) not null unique,
    status bit(1) default 1
);

CREATE TABLE product
(
    id          int primary key auto_increment,
    name        varchar(200) NOT NULL,
    price       double       NOT NULL,
    category_id int          NOT NULL,
    foreign key (category_id) references category (id)
);

delimiter //
create procedure proc_show_list_category()
begin
    select * from category;
end //

delimiter //
create procedure category(in cName varchar(100), cStatus bit(1))
begin
    insert into category(name, status) values (cName, cStatus);
end //

delimiter //
create procedure proc_update_category(in cName varchar(100), cStatus bit(1), cId int)
begin
    update category set name = cName, status = cStatus where id = cId;
end //

delimiter //
create procedure proc_delete_customer(in cId int)
begin
    delete from category where id = cId;
end //

delimiter //
create procedure proc_find_by_id(in cId int)
begin
    select * from category where id = cId;
end //

delimiter //
create procedure proc_find_by_name(in cName varchar(100))
begin
    select * from category where name like concat('%', cName, '%');
end //

delimiter //
create procedure proc_change_status(in idStatus int)
begin
    update category set status = status ^ 1 where id = idStatus;
end //

drop procedure proc_change_status;


delimiter //
create procedure proc_show_list_product()
begin
    select * from product;
end //

delimiter //
create procedure proc_add_new_product(in pName varchar(200), pPrice double, pCategory int)
begin
    insert into product(name, price, category_id) values (pName, pPrice, pCategory);
end //

delimiter //
create procedure proc_find_product_by_id(in pId int)
begin
    select * from product where id = pId;
end //

delimiter //
create procedure proc_update_product(in pName varchar(200), pPrice double, pCat int, pId int)
begin
    update product set name=pName, price=pPrice, category_id=pCat where id = pId;
end //

delimiter //
create procedure  proc_delete_product(in pId int)
begin
    delete from product where id = pId;
end //
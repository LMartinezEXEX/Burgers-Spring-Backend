delete from INGREDIENTS;

insert into roles (name) values ('ROLE_USER');
insert into roles (name) values ('ROLE_MODERATOR');
insert into roles (name) values ('ROLE_ADMIN');

insert into INGREDIENTS (id, name, price, type, description) values (1000, 'MEAT', 0,'PROTEIN', '');
insert into INGREDIENTS (id, name, price, type, description) values (1001, 'VEGAN', 0, 'PROTEIN', '');
insert into INGREDIENTS (id, name, price, type, description) values (1002, 'LENTIL', 0, 'PROTEIN', '');
insert into INGREDIENTS (id, name, price, type, description) values (1003, 'CHEDDAR', 20, 'TOPPING', '');
insert into INGREDIENTS (id, name, price, type, description) values (1004, 'BACON', 20, 'TOPPING', '');
insert into INGREDIENTS (id, name, price, type, description) values (1005, 'CARAMELIZED ONION', 20, 'TOPPING', '');
insert into INGREDIENTS (id, name, price, type, description) values (1006, 'TOMATO', 20, 'TOPPING', '');
insert into INGREDIENTS (id, name, price, type, description) values (1007, 'LETTUCE', 20, 'TOPPING', '');
insert into INGREDIENTS (id, name, price, type, description) values (1008, 'MUSHROOM', 20, 'TOPPING', '');
insert into INGREDIENTS (id, name, price, type, description) values (1009, 'MAYONEESE', 10, 'SAUCE', '');
insert into INGREDIENTS (id, name, price, type, description) values (1010, 'KETCHUP', 10, 'SAUCE', '');
insert into INGREDIENTS (id, name, price, type, description) values (1011, 'SPECIAL SAUCE', 10, 'SAUCE', '');

insert into BURGER_SIZE (name, price) values ('SMALL', 520);
insert into BURGER_SIZE (name, price) values ('MEDIUM', 620);
insert into BURGER_SIZE (name, price) values ('LARGE', 720);
insert into BURGER_SIZE (name, price) values ('EXTRA LARGE', 820);
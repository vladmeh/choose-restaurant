DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM restaurant;
DELETE FROM lunch;
DELETE FROM choice_restaurant;

-- user@yandex.ru : user
-- admin@gmail.com: admin

INSERT INTO users (id, email, name, password) VALUES (0, 'user@yandex.ru', 'User', '$2a$04$BJGG/TXtpoBcHBWcbR2JuOZb316ThHVlPyATPPivLsqv/dLC3g.7e'), (1, 'admin@gmail.com', 'Admin', '$2a$04$n/osmjB//rURpDif2AFzMepdJMhQ4fAHUlVJN2PaytD6srcFo4J3y');
INSERT INTO user_roles (role, user_id) VALUES ('ROLE_USER', 0), ('ROLE_ADMIN', 1);
INSERT INTO restaurant (id, name) VALUES (0,'McDonalds'), (1,'Burger King'), (2,'KFC');
INSERT INTO lunch (ID, LUNCH_DATE, RESTAURANT_ID, NAME, PRICE) VALUES (0, today(), 0, 'Макчикен', 100), (1, today()-1, 0, 'Картофель фри', 75), (2, today(), 1, 'Двойной Чизбургер', 118), (3, today()-1, 1, 'Цезарь Ролл', 164), (4, today(), 2, 'Танкобургер', 120), (5, today()-1, 2, 'Чиззи Чикен Фри', 80);
INSERT INTO CHOICE_RESTAURANT (ID, USER_ID, CHOICE_DATE, RESTAURANT_ID) VALUES (0, 0, '2018-05-23', 0);

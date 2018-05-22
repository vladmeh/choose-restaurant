DELETE FROM user_roles;
DELETE FROM users;

INSERT INTO users (ID, EMAIL, NAME, PASSWORD) VALUES (0, 'user@yandex.ru', 'User', 'password'), (1, 'admin@gmail.com', 'Admin', 'admin');
INSERT INTO user_roles (ROLE, USER_ID) VALUES ('ROLE_USER', 0), ('ROLE_ADMIN', 1);

DROP TABLE users IF EXISTS;
DROP TABLE user_roles IF EXISTS;

CREATE TABLE users
(
  id                INTEGER PRIMARY KEY AUTO_INCREMENT,
  name              VARCHAR(255)            NOT NULL,
  email             VARCHAR(255)            NOT NULL,
  password          VARCHAR(255)            NOT NULL
);

CREATE UNIQUE INDEX users_unique_email_idx ON USERS (email);

CREATE TABLE user_roles
(
  user_id INTEGER NOT NULL,
  role    VARCHAR(255),
  CONSTRAINT user_roles_idx UNIQUE (user_id, role),
  FOREIGN KEY (user_id) REFERENCES USERS (id) ON DELETE CASCADE
);
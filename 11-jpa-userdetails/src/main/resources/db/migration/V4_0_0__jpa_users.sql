DROP TABLE IF EXISTS authorities;
DROP TABLE IF EXISTS users;

CREATE TABLE TBL_AUTH_USERS
(
    id int(20) PRIMARY KEY AUTO_INCREMENT,
    username varchar(255),
    password varchar(255),
    enable bit(1) NOT NULL,
    locked bit(1) NOT NULL
);

CREATE TABLE TBL_AUTH_AUTHORITIES
(
    id int(20) PRIMARY KEY AUTO_INCREMENT,
    authority varchar(255),
    userEntity_id int(20),
    CONSTRAINT fk_user FOREIGN KEY (userEntity_id) REFERENCES TBL_AUTH_USERS(id)
);
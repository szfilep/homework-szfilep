CREATE DATABASE mstore
  DEFAULT CHARACTER SET utf8
  DEFAULT COLLATE utf8_general_ci;

CREATE USER 'mstore'@'localhost'  IDENTIFIED BY 'mstore';
GRANT ALL ON mstore.* TO mstore;


CREATE DATABASE ItemSQLDB


USE ItemSQLDB

CREATE TABLE item_table(
	id BIGINT NOT NULL IDENTITY (1,1),
	title VARCHAR(255),
	description VARCHAR(1024),
	unit_price DECIMAL(10,5)
)



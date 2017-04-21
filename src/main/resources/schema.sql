# DROP TABLE IF EXISTS reservations;
CREATE TABLE reservations
(
	id BIGINT(20) NOT NULL AUTO_INCREMENT,
	reservation_name VARCHAR(50) NOT NULL,
	PRIMARY KEY (id)
);


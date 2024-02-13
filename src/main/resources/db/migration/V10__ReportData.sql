
CREATE TABLE `report` (
                          `id` int NOT NULL,
                          `city` varchar(255) DEFAULT NULL,
                          `first_name` varchar(255) DEFAULT NULL,
                          `last_name` varchar(255) DEFAULT NULL,
                          `street` varchar(255) DEFAULT NULL,
                          `as_on_date` datetime(6) DEFAULT NULL,
                          PRIMARY KEY (`id`)
);

INSERT INTO report
(id, city, first_name, last_name, street, as_on_date)
VALUES(318, 'New York City', 'John', 'Smith', '123 Main St', '2024-01-29');
INSERT INTO report
(id, city, first_name, last_name, street, as_on_date)
VALUES(319, 'New York City', 'John', 'Smith', '123 Main St', '2024-01-29');
INSERT INTO report
(id, city, first_name, last_name, street, as_on_date)
VALUES(320, 'New York City', 'John', 'Smith', '123 Main St', '2024-01-29');
INSERT INTO report
(id, city, first_name, last_name, street, as_on_date)
VALUES(321, 'New York City', 'John', 'Smith', '123 Main St', '2024-01-29');
INSERT INTO report
(id, city, first_name, last_name, street, as_on_date)
VALUES(322, 'New York City', 'John', 'Smith', '123 Main St', '2024-01-29');
INSERT INTO report
(id, city, first_name, last_name, street, as_on_date)
VALUES(323, 'New York City', 'John', 'Smith', '123 Main St', '2024-01-29');
CREATE DATABASE    procureem;

CREATE TABLE IF NOT EXISTS users (
   id SERIAL PRIMARY KEY,
    name VARCHAR(100),
    role VARCHAR (20),
    username VARCHAR (50),
    password VARCHAR (100)
);

CREATE TABLE IF NOT EXISTS inventory(
    id SERIAL PRIMARY KEY,
    name VARCHAR (100),
    specification VARCHAR (100),
    quantity INT,
    owner_id INT
);

CREATE TABLE IF NOT EXISTS rfq(
    id SERIAL PRIMARY KEY,
    name VARCHAR (100),
    specification VARCHAR (100),
    quantity INT,
    max_unit_price REAL,
    actual_unit_price REAL,
    delivery_due VARCHAR (20),
    range VARCHAR (20),
    support_period VARCHAR (20),
    additional_requirements TEXT,
    buyer_id INT,
    seller_id INT
);

CREATE TABLE IF NOT EXISTS supplier_rfq(
    rfq_id INT,
    seller_id INT,
    price REAL
);

CREATE TABLE IF NOT EXISTS notifications(
    id SERIAL PRIMARY KEY,
    title VARCHAR(50),
    message TEXT,
    recipient_role CHAR,
    recipient_id INT
);


CREATE TABLE IF NOT EXISTS viewed_notifications(
    notification_id INT,
    user_id INT
);











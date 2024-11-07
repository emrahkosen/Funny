DROP TABLE IF EXISTS customer;
CREATE TABLE IF NOT EXISTS customer (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    first_name VARCHAR(255),
    last_name VARCHAR(255)
);


CREATE TABLE IF NOT EXISTS Book (
    book_id INT AUTO_INCREMENT PRIMARY KEY,
    title VARCHAR(255),
    isbn VARCHAR(20) UNIQUE,
    published_year INT,
    genre VARCHAR(100)
);


CREATE TABLE user_table (
                            id BIGINT AUTO_INCREMENT PRIMARY KEY,
                            username VARCHAR(255) UNIQUE NOT NULL,
                            password VARCHAR(255),
                            firstName VARCHAR(255),
                            lastName VARCHAR(255),
                            email VARCHAR(255),
                            birthDate DATE,
                            nationality VARCHAR(255)
);
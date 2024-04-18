-- `application.properties`에 아래 세 줄 추가하면 자동 실행됨
-- # dummy data (어플리케이션 실행 시 더미 데이터 생성 SQL 자동 실행)
-- spring.sql.init.mode=always
-- spring.jpa.defer-datasource-initialization=true

-- User
CREATE TABLE IF NOT EXISTS user (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    email VARCHAR(255) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    name VARCHAR(255) NOT NULL,
    birthdate CHAR(7) NOT NULL,
    status BOOLEAN NOT NULL,
    address VARCHAR(255),
    phone VARCHAR(20) NOT NULL,
    grade ENUM('BRONZE', 'SILVER', 'GOLD', 'PLATINUM') DEFAULT 'BRONZE',
    role ENUM('USER', 'ADMIN') NOT NULL DEFAULT 'USER',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP
);

-- Category
CREATE TABLE IF NOT EXISTS category (
    category_key BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(255) NOT NULL,
    parent_key BIGINT,
    level INTEGER,
    FOREIGN KEY (parent_key) REFERENCES category(category_key)
);

-- Product
CREATE TABLE IF NOT EXISTS product (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_id BIGINT,
    price INT NOT NULL,
    name VARCHAR(255) NOT NULL,
    description TEXT,
    status BOOLEAN NOT NULL,
    current_stock INT NOT NULL,
    seller_id BIGINT,
    image VARCHAR(255),
    create_at DATE,
    FOREIGN KEY (category_id) REFERENCES category(category_key),
    FOREIGN KEY (seller_id) REFERENCES user(user_id)
);
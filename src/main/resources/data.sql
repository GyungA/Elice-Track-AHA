-- User
-- INSERT INTO user (email, password, name, birthdate, status, address, phone, grade, role)
-- VALUES
-- ('user1@example.com', 'password123', 'John Doe', '9901011', true, '1234 Street Name', '010-1234-5678', 'BRONZE', 'USER'),
-- ('user2@example.com', 'password123', 'Jane Doe', '8802021', true, '5678 Avenue Name', '010-2345-6789', 'SILVER', 'USER'),
-- ('user3@example.com', 'password123', 'Jim Beam', '9703031', false, '91011 Boulevard', '010-3456-7890', 'GOLD', 'ADMIN');

-- Category
-- 루트 카테고리 추가
-- INSERT INTO category (category_name, level) VALUES ('Electronics', 1);
-- INSERT INTO category (category_name, level) VALUES ('Clothing', 1);

-- Electronics 하위 카테고리
-- INSERT INTO category (category_name, parent_key, level) VALUES ('Smartphones', 1, 2);
-- INSERT INTO category (category_name, parent_key, level) VALUES ('Laptops', 1, 2);
-- INSERT INTO category (category_name, parent_key, level) VALUES ('Cameras', 1, 2);

-- Clothing 하위 카테고리
-- INSERT INTO category (category_name, parent_key, level) VALUES ('Men', 2, 2);
-- INSERT INTO category (category_name, parent_key, level) VALUES ('Women', 2, 2);


-- Product
-- INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
-- VALUES (3, 999, 'iPhone 13', 'Latest Apple iPhone 13 with improved camera', true, 50, 1, 'image_url_here', '2021-09-24');

-- INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
-- VALUES (4, 1200, 'MacBook Pro', 'Apple MacBook Pro with M1 Chip', true, 30, 1, 'image_url_here', '2021-06-11');

-- INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
-- VALUES (5, 450, 'Nikon Z6', 'Full-frame mirrorless camera with high resolution', true, 15, 2, 'image_url_here', '2022-01-08');

-- User
INSERT INTO users (email, password, name, birthdate, status, address, phone, grade, role)
VALUES
    ('user1@example.com', 'password123', 'John Doe', '9901011', true, '1234 Street Name', '010-1234-5678', 'BRONZE', 'USER'),
    ('user2@example.com', 'password123', 'Jane Doe', '8802021', true, '5678 Avenue Name', '010-2345-6789', 'SILVER', 'USER'),
    ('user3@example.com', 'password123', 'Jim Beam', '9703031', false, '91011 Boulevard', '010-3456-7890', 'GOLD', 'ADMIN');

-- Category
-- 최상위 카테고리
INSERT INTO category (name, parent_id) VALUES ('Electronics', null);
INSERT INTO category (name, parent_id) VALUES ('Clothing', null);

-- Electronics 하위 카테고리
INSERT INTO category (name, parent_id) VALUES ('Smartphones', 1);
INSERT INTO category (name, parent_id) VALUES ('Laptops', 1);
INSERT INTO category (name, parent_id) VALUES ('Cameras', 1);

-- Clothing 하위 카테고리
INSERT INTO category (name, parent_id) VALUES ('Men', 2);
INSERT INTO category (name, parent_id) VALUES ('Women', 2);


-- Product
INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (3, 999, 'iPhone 13', 'Latest Apple iPhone 13 with improved camera', true, 50, 1, 'image_url_here', '2021-09-24');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (4, 1200, 'MacBook Pro', 'Apple MacBook Pro with M1 Chip', true, 30, 1, 'image_url_here', '2021-06-11');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (5, 450, 'Nikon Z6', 'Full-frame mirrorless camera with high resolution', true, 15, 2, 'image_url_here', '2022-01-08');
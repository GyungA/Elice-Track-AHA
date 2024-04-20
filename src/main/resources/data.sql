-- User
INSERT INTO users (email, password, name, birthdate, status, address, phone, grade, role)
VALUES
('user1@example.com', 'password123', 'John Doe', '9901011', true, '1234 Street Name', '010-1234-5678', 'BRONZE', 'USER'),
('user2@example.com', 'password123', 'Jane Doe', '8802021', true, '5678 Avenue Name', '010-2345-6789', 'SILVER', 'USER'),
('user3@example.com', 'password123', 'Jim Beam', '9703031', false, '91011 Boulevard', '010-3456-7890', 'GOLD', 'ADMIN');

-- Category
-- 최상위 카테고리
INSERT INTO category (name, parent_id) VALUES ('식품', null);
INSERT INTO category (name, parent_id) VALUES ('주얼리/패션', null);
INSERT INTO category (name, parent_id) VALUES ('홈리빙', null);
INSERT INTO category (name, parent_id) VALUES ('케이스/문구', null);
INSERT INTO category (name, parent_id) VALUES ('뷰티', null);
INSERT INTO category (name, parent_id) VALUES ('반려동물', null);
INSERT INTO category (name, parent_id) VALUES ('공예', null);
INSERT INTO category (name, parent_id) VALUES ('영유아동', null);

-- 식품 하위 카테고리
INSERT INTO category (name, parent_id) VALUES ('디저트/음료', 1);
INSERT INTO category (name, parent_id) VALUES ('수제먹거리', 1);
INSERT INTO category (name, parent_id) VALUES ('농축수산물', 1);

-- 주얼리/패션 하위 카테고리
INSERT INTO category (name, parent_id) VALUES ('주얼리', 2);
INSERT INTO category (name, parent_id) VALUES ('의류', 2);
INSERT INTO category (name, parent_id) VALUES ('패션잡화', 2);

INSERT INTO category (name, parent_id) VALUES ('홈인테리어', 3);
INSERT INTO category (name, parent_id) VALUES ('주방/생활', 3);

INSERT INTO category (name, parent_id) VALUES ('문구/취미', 4);
INSERT INTO category (name, parent_id) VALUES ('기념일/파티', 4);
INSERT INTO category (name, parent_id) VALUES ('일러스트', 4);
INSERT INTO category (name, parent_id) VALUES ('차량용품', 4);

INSERT INTO category (name, parent_id) VALUES ('기초/색조', 5);
INSERT INTO category (name, parent_id) VALUES ('헤어/바디/케어', 5);

INSERT INTO category (name, parent_id) VALUES ('사료/간식', 6);
INSERT INTO category (name, parent_id) VALUES ('반려패션', 6);
INSERT INTO category (name, parent_id) VALUES ('반려용품', 6);

INSERT INTO category (name, parent_id) VALUES ('인테리어공예', 7);
INSERT INTO category (name, parent_id) VALUES ('주방공예', 7);
INSERT INTO category (name, parent_id) VALUES ('생활공예', 7);

INSERT INTO category (name, parent_id) VALUES ('영유아패션', 8);
INSERT INTO category (name, parent_id) VALUES ('영유아용품', 8);
INSERT INTO category (name, parent_id) VALUES ('답례품/기념품', 8);


-- Product
INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (9, 7800, 'Cheese Cake', 'Made in France', true, 100, 1, 'image_url_here', '2021-09-24');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (10, 15000, 'Apple Pie', 'This Pie is made by Elice', true, 40, 1, 'image_url_here', '2021-06-11');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (11, 25000, 'T-Bone Steak', '맛없으면 환불 보장', true, 10, 2, 'image_url_here', '2022-01-08');


INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (14, 180000, 'Dunk Low', '2021년 잇템 범고래', true, 100, 3, 'image_url_here', '2022-01-25');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (13, 300000, 'carhartt - Outer', '2024 S/S 신상', true, 30, 1, 'image_url_here', '2024-03-11');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (22, 5000, '노세범', '올리브영 Best 상품', true, 500, 2, 'image_url_here', '2022-01-08');


INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (27, 50000, '그릇 세트', 'For 신혼 부부', true, 200, 1, 'image_url_here', '2022-06-24');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (22, 35000, 'DrGroot Shampoo', '탈모 방지 샴푸', true, 1000, 1, 'image_url_here', '2023-03-11');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (15, 900000, 'LG 김치냉장고', '4중 김치 냉장고', true, 80, 2, 'image_url_here', '2022-08-08');


INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (9, 18000, 'Knotted Doughnut Set', '6가지 도넛으로 구성', true, 100, 1, 'image_url_here', '2024-04-10');

INSERT INTO product (category_id, price, name, description, status, current_stock, seller_id, image, create_at)
VALUES (16, 300000, 'JoMalone ', 'For Man 23 F/W', true, 50, 1, 'image_url_here', '2023-02-11');

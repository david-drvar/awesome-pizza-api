-- Populate 'ingredient' table
INSERT INTO ingredient (name, price)
VALUES ('Tomato sauce', 1),
       ('Mozzarella cheese', 2),
       ('Pepperoni', 2),
       ('Mushrooms', 2),
       ('Onions', 1),
       ('Green peppers', 3),
       ('Olives', 2),
       ('Bacon', 3),
       ('Ham', 2),
       ('Pineapple', 3);

-- Populate 'ingredient_price_history' table
INSERT INTO ingredient_price_history (price, date_start, ingredient_id)
VALUES (1.5, CURRENT_TIMESTAMP, 1),
       (2.0, CURRENT_TIMESTAMP, 2),
       (1.75, CURRENT_TIMESTAMP, 3),
       (1.25, CURRENT_TIMESTAMP, 4),
       (1.0, CURRENT_TIMESTAMP, 5),
       (1.0, CURRENT_TIMESTAMP, 6),
       (1.25, CURRENT_TIMESTAMP, 7),
       (1.5, CURRENT_TIMESTAMP, 8),
       (1.75, CURRENT_TIMESTAMP, 9),
       (1.75, CURRENT_TIMESTAMP, 10);

-- Populate 'premade_pizza' table
INSERT INTO premade_pizza (name, price)
VALUES ('Margherita', 8.99),
       ('Pepperoni', 9.99),
       ('Vegetarian', 10.99),
       ('Hawaiian', 11.99),
       ('Meat Lover', 12.99),
       ('Supreme', 13.99),
       ('BBQ Chicken', 11.99),
       ('Buffalo Chicken', 11.99),
       ('Four Cheese', 10.99),
       ('Mediterranean', 12.99);

-- Populate 'pizza_combo' table
INSERT INTO pizza_combo (price, premade_pizza_id, pizza_size)
VALUES (15.99, 1, 'LARGE'),
       (17.99, 2, 'SMALL'),
       (14.99, 3, 'LARGE'),
       (16.99, 4, 'MEDIUM'),
       (18.99, 5, 'LARGE'),
       (20.99, 6, 'LARGE'),
       (16.99, 7, 'LARGE'),
       (16.99, 8, 'SMALL'),
       (15.99, 9, 'LARGE'),
       (17.99, 10, 'LARGE');

-- Populate 'pizza_orders' table
INSERT INTO pizza_orders (timestamp, address, customer_name, customer_surname, order_status, phone_number, price)
VALUES (CURRENT_TIMESTAMP, '123 Main St', 'John', 'Doe', 'PLACED', '123-456-7890', 100),
       (CURRENT_TIMESTAMP, '456 Elm St', 'Jane', 'Smith', 'PLACED', '456-789-0123', 100),
       (CURRENT_TIMESTAMP, '789 Oak St', 'Michael', 'Johnson', 'PLACED', '789-012-3456', 100),
       (CURRENT_TIMESTAMP, '321 Pine St', 'Emily', 'Brown', 'PLACED', '987-654-3210', 100),
       (CURRENT_TIMESTAMP, '654 Maple St', 'David', 'Taylor', 'PLACED', '654-321-0987', 100),
       (CURRENT_TIMESTAMP, '987 Cedar St', 'Sarah', 'Anderson', 'PLACED', '321-098-7654', 100),
       (CURRENT_TIMESTAMP, '159 Birch St', 'Ryan', 'Wilson', 'PLACED', '012-345-6789', 100),
       (CURRENT_TIMESTAMP, '753 Walnut St', 'Jessica', 'Martinez', 'PLACED', '234-567-8901', 100),
       (CURRENT_TIMESTAMP, '852 Spruce St', 'Christopher', 'Garcia', 'PLACED', '345-678-9012', 100),
       (CURRENT_TIMESTAMP, '369 Cherry St', 'Amanda', 'Hernandez', 'PLACED', '456-789-0123', 100);

-- Populate 'order_pizzacombo' table
INSERT INTO order_pizzacombo (order_id, pizzacombo_id)
VALUES (1, 1),
       (2, 2),
       (3, 3),
       (4, 4),
       (5, 5),
       (6, 6),
       (7, 7),
       (8, 8),
       (9, 9),
       (10, 10);

-- Populate 'pizzacombo_ingredient' table
INSERT INTO pizzacombo_ingredient (ingredient_id, pizzacombo_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 3),
       (7, 3),
       (8, 4),
       (9, 4),
       (10, 5);

-- Populate 'premade_pizza_ingredient' table
INSERT INTO premade_pizza_ingredient (ingredient_id, premade_pizza_id)
VALUES (1, 1),
       (2, 1),
       (3, 2),
       (4, 2),
       (5, 2),
       (6, 3),
       (7, 3),
       (8, 4),
       (9, 4),
       (10, 5);

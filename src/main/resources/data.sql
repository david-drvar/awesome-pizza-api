-- Populate 'ingredient' table
INSERT INTO ingredient (name, price)
VALUES ('Tomato sauce', 1.2),
       ('Mozzarella cheese', 1.5),
       ('Pepperoni', 1.75),
       ('Mushrooms', 1.85),
       ('Onions', 1.5),
       ('Green peppers', 1.4),
       ('Olives', 1.5),
       ('Bacon', 2),
       ('Ham', 1.95),
       ('Pineapple', 1.95);

-- Populate 'ingredient_price_history' table
INSERT INTO ingredient_price_history (price, date_start, ingredient_id)
VALUES (1.2, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 1),
       (1.5, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 2),
       (1.75, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 3),
       (1.85, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 4),
       (1.5, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 5),
       (1.4, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 6),
       (1.50, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 7),
       (2, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 8),
       (1.95, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 9),
       (1.95, CURRENT_TIMESTAMP - INTERVAL '1 DAY', 10),

       (1, CURRENT_TIMESTAMP, 1),
       (1.75, CURRENT_TIMESTAMP, 2),
       (1.5, CURRENT_TIMESTAMP, 3),
       (1.20, CURRENT_TIMESTAMP, 4),
       (1.3, CURRENT_TIMESTAMP, 5),
       (1.1, CURRENT_TIMESTAMP, 6),
       (1.20, CURRENT_TIMESTAMP, 7),
       (1.75, CURRENT_TIMESTAMP, 8),
       (2, CURRENT_TIMESTAMP, 9),
       (2, CURRENT_TIMESTAMP, 10);

-- -- Populate 'premade_pizza' table
INSERT INTO premade_pizza (name, price)
VALUES ('Margherita', 8.74),
       ('Pepperoni', 9.99),
       ('Vegetarian', 8.29),
       ('Hawaiian', 9.74),
       ('Meat Lover', 7.99),
       ('Supreme', 9.69),
       ('BBQ Chicken', 11.74),
       ('Buffalo Chicken', 10.54),
       ('Four Cheese', 10.19),
       ('Mediterranean', 9.94);

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
INSERT INTO pizza_orders (timestamp, street, customer_name, customer_surname, order_status, phone_number, price, town,
                          zip)
VALUES (CURRENT_TIMESTAMP + INTERVAL '1' SECOND, '123 Main St', 'John', 'Doe', 'PLACED', '+39 123 344 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '2' SECOND, '456 Elm St', 'Jane', 'Smith', 'PLACED', '+39 123 344 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '3' SECOND, '789 Oak St', 'Michael', 'Johnson', 'PLACED', '+39 123 344 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '4' SECOND, '321 Pine St', 'Emily', 'Brown', 'PLACED', '+39 123 323 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '5' SECOND, '654 Maple St', 'David', 'Taylor', 'PLACED', '+39 123 342 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '6' SECOND, '987 Cedar St', 'Sarah', 'Anderson', 'PLACED', '+39 123 233 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '7' SECOND, '159 Birch St', 'Ryan', 'Wilson', 'PLACED', '+39 123 123 4567', 100,
        'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '8' SECOND, '753 Walnut St', 'Jessica', 'Martinez', 'PLACED', '+39 123 123 4567',
        100, 'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '9' SECOND, '852 Spruce St', 'Christopher', 'Garcia', 'PLACED', '+39 123 123 4567',
        100, 'Milano', '20063'),
       (CURRENT_TIMESTAMP + INTERVAL '10' SECOND, '369 Cherry St', 'Amanda', 'Hernandez', 'PLACED', '+39 123 123 4567',
        100, 'Milano', '20063');

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
       (10, 5),
       (1, 6),
       (3, 6),
       (7, 6),
       (8, 7),
       (9, 7),
       (10, 7),
       (3, 8),
       (5, 8),
       (2, 8),
       (1, 9),
       (4, 9),
       (9, 9),
       (1, 10),
       (2, 10),
       (4, 10);

-- Populate 'user' table
INSERT INTO users (username, password, role)
VALUES ('admin', '$2a$10$xaqBrGvBd/lj0dr8j.KjTeN7V0JsCFn8fZx2rwiMaVui.k4JlmWgG', 'ADMIN'),
       ('pizzamaker', '$2a$10$xaqBrGvBd/lj0dr8j.KjTeN7V0JsCFn8fZx2rwiMaVui.k4JlmWgG', 'PIZZAMAKER');

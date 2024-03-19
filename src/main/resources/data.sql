-- Populate 'ingredient' table
INSERT INTO ingredient (id, name)
VALUES (1, 'Tomato sauce'),
       (2, 'Mozzarella cheese'),
       (3, 'Pepperoni'),
       (4, 'Mushrooms'),
       (5, 'Onions'),
       (6, 'Green peppers'),
       (7, 'Olives'),
       (8, 'Bacon'),
       (9, 'Ham'),
       (10, 'Pineapple');

-- Populate 'ingredient_price_history' table
INSERT INTO ingredient_price_history (id, price, date_start, ingredient_id)
VALUES (1, 1.5, CURRENT_TIMESTAMP, 1),
       (2, 2.0, CURRENT_TIMESTAMP, 2),
       (3, 1.75, CURRENT_TIMESTAMP, 3),
       (4, 1.25, CURRENT_TIMESTAMP, 4),
       (5, 1.0, CURRENT_TIMESTAMP, 5),
       (6, 1.0, CURRENT_TIMESTAMP, 6),
       (7, 1.25, CURRENT_TIMESTAMP, 7),
       (8, 1.5, CURRENT_TIMESTAMP, 8),
       (9, 1.75, CURRENT_TIMESTAMP, 9),
       (10, 1.75, CURRENT_TIMESTAMP, 10);

-- Populate 'premade_pizza' table
INSERT INTO premade_pizza (id, name, price)
VALUES (1, 'Margherita', 8.99),
       (2, 'Pepperoni', 9.99),
       (3, 'Vegetarian', 10.99),
       (4, 'Hawaiian', 11.99),
       (5, 'Meat Lover', 12.99),
       (6, 'Supreme', 13.99),
       (7, 'BBQ Chicken', 11.99),
       (8, 'Buffalo Chicken', 11.99),
       (9, 'Four Cheese', 10.99),
       (10, 'Mediterranean', 12.99);

-- Populate 'pizza_combo' table
INSERT INTO pizza_combo (id, price, premade_pizza_id, pizza_size)
VALUES (1, 15.99, 1, 'LARGE'),
       (2, 17.99, 2, 'SMALL'),
       (3, 14.99, 3, 'LARGE'),
       (4, 16.99, 4, 'MEDIUM'),
       (5, 18.99, 5, 'LARGE'),
       (6, 20.99, 6, 'LARGE'),
       (7, 16.99, 7, 'LARGE'),
       (8, 16.99, 8, 'SMALL'),
       (9, 15.99, 9, 'LARGE'),
       (10, 17.99, 10, 'LARGE');

-- Populate 'pizza_orders' table
INSERT INTO pizza_orders (id, timestamp, address, customer_name, customer_surname, order_status, phone_number)
VALUES (1, CURRENT_TIMESTAMP, '123 Main St', 'John', 'Doe', 'PLACED', '123-456-7890'),
       (2, CURRENT_TIMESTAMP, '456 Elm St', 'Jane', 'Smith', 'PLACED', '456-789-0123'),
       (3, CURRENT_TIMESTAMP, '789 Oak St', 'Michael', 'Johnson', 'PLACED', '789-012-3456'),
       (4, CURRENT_TIMESTAMP, '321 Pine St', 'Emily', 'Brown', 'PLACED', '987-654-3210'),
       (5, CURRENT_TIMESTAMP, '654 Maple St', 'David', 'Taylor', 'PLACED', '654-321-0987'),
       (6, CURRENT_TIMESTAMP, '987 Cedar St', 'Sarah', 'Anderson', 'PLACED', '321-098-7654'),
       (7, CURRENT_TIMESTAMP, '159 Birch St', 'Ryan', 'Wilson', 'PLACED', '012-345-6789'),
       (8, CURRENT_TIMESTAMP, '753 Walnut St', 'Jessica', 'Martinez', 'PLACED', '234-567-8901'),
       (9, CURRENT_TIMESTAMP, '852 Spruce St', 'Christopher', 'Garcia', 'PLACED', '345-678-9012'),
       (10, CURRENT_TIMESTAMP, '369 Cherry St', 'Amanda', 'Hernandez', 'PLACED', '456-789-0123');

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

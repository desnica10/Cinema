-- Users --
    -- ADMIN --
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'admin', '123', true, 'ADMIN', '1968-08-12', 'admin@gmail.com', 'Admin', 'Admin',
	'064/7891789'); -- ID #1

	-- MENAGER --
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'manager', '123', true, 'MANAGER', '1988-02-06', 'manager@gmail.com', 'Manager',
	'Manager', '064/7891789'); -- ID #2
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'stefan', '123', true, 'MANAGER', '1997-04-23', 'stefandesnica@gmail.com', 'Stefan',
	'Desnica', '061/3121789'); -- ID #3

	-- CUSTOMER --
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'danilo', '123', true, 'CUSTOMER', '1998-11-12', 'danilodesnica@gmail.com', 'Danilo',
	'Desnica', '065/51255122'); -- ID #4
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'marko', '123', true, 'CUSTOMER', '1996-03-11', 'marko@gmail.com', 'Marko',
	'Markovic', '062/52144129'); -- ID #5
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'petar', '123', true, 'CUSTOMER', '1997-05-21', 'petar@gmail.com', 'Petar',
	'Petrovic', '062/52144129'); -- ID #6

-- Cinemas --
INSERT INTO public.cinemas (id, name, address, email, phone)
    VALUES (nextval('cinema_id_seq'), 'Cinaplexxx', 'Bulevar Oslobodjenja 12, Novi Sad', 'cinaplexxx@gmail.com',
    '061/2211789'); -- ID #1
INSERT INTO public.cinemas (id, name, address, email, phone)
    VALUES (nextval('cinema_id_seq'), 'CineStar', 'Sentandrejski put 11, Novi Sad', 'press@cinestarcinemas.eu',
    '063/8131219'); -- ID #2

-- Cinemas - Menagers --
INSERT INTO public.menagers (cinema_id, user_id) VALUES (1, 2); -- Cinaplexxx - manager -- ID #1

INSERT INTO public.menagers (cinema_id, user_id) VALUES (2, 2); -- CineStar - manager -- ID #2
INSERT INTO public.menagers (cinema_id, user_id) VALUES (2, 3); -- CineStar - stefan -- ID #3

-- Halls --
INSERT INTO public.halls (id, capacity, name, cinema_id)
	VALUES (nextval('hall_id_seq'), 30, 'Hall 1', 1); -- Cinaplexxx - Hall 1 -- ID #1
INSERT INTO public.halls (id, capacity, name, cinema_id)
	VALUES (nextval('hall_id_seq'), 20, 'Hall 2', 1); -- Cinaplexxx - Hall 2 -- ID #2

INSERT INTO public.halls (id, capacity, name, cinema_id)
	VALUES (nextval('hall_id_seq'), 45, '1', 2); -- CineStar - 1 -- ID #3

-- Movies --
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 0, 'Opis 1', 120, 'King', 'ACTION'); -- ID #1
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 7, 'Opis 2', 90, 'Mr. Bean', 'COMEDY'); -- ID #2
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 6, 'Opis 3', 102, 'Dictator', 'COMEDY'); -- ID #3
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 8, 'Opis 4', 141, 'Matrix', 'ACTION'); -- ID #4
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 0, 'Opis 5', 152, 'Harry Potter', 'SF'); -- ID #5
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 0, 'Opis 6', 114, 'Prestigue', 'THRILLER'); -- ID #6

-- Projections --
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-08-19', 350, 1, 3); -- Cinaplexxx - Dictator -- 19/08/2020 -- ID #1
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-08-20', 450, 1, 2); -- Cinaplexxx - Mr. Bean -- 20/08/2020 -- ID #2
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-09-30', 450, 1, 4); -- Cinaplexxx - Matrix -- 30/08/2020 -- ID #3
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-09-29', 350, 1, 5); -- Cinaplexxx - Harry Potter -- 29/08/2020 -- ID #4
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-09-30', 350, 1, 5); -- Cinaplexxx - Harry Potter -- 30/08/2020 -- ID #5

INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-08-20', 300, 2, 5); -- CineStar - Harry Potter -- 20/08/2020 -- ID #6
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-09-29', 300, 2, 4); -- CineStar - Matrix -- 29/08/2020 -- ID #7
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-09-30', 300, 2, 4); -- CineStar - Matrix -- 30/08/2020 -- ID #8

-- Projection-Halls --
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (1, 1);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (2, 1);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (3, 1);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (3, 2);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (4, 2);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (5, 1);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (5, 2);

INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (6, 3);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (7, 3);
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (8, 3);

-- Tickets --
INSERT INTO public.tickets(id, number_of_seats, reservation_date, status, customer_id, hall_id, projection_id)
	VALUES (nextval('ticket_id_seq'), 2, '2020-08-19', 'PAID', 4, 1, 1); -- Danilo -- Cinaplexxx -- Dictator -- Hall 1
	-- 19/08/2020 -- ID #1
INSERT INTO public.tickets(id, number_of_seats, reservation_date, status, customer_id, hall_id, projection_id)
	VALUES (nextval('ticket_id_seq'), 4, '2020-08-20', 'PAID', 4, 1, 2); -- Danilo -- Cinaplexxx -- Mr. Bean -- Hall 1
	-- 20/08/2020  -- ID #2

INSERT INTO public.tickets(id, number_of_seats, reservation_date, status, customer_id, hall_id, projection_id)
	VALUES (nextval('ticket_id_seq'), 2, '2020-08-20', 'PAID', 5, 3, 6); -- Marko -- CineStar - Matrix -- Hall 1
	-- 20/08/2020  -- ID #3
INSERT INTO public.tickets(id, number_of_seats, reservation_date, status, customer_id, hall_id, projection_id)
	VALUES (nextval('ticket_id_seq'), 2, '2020-08-19', 'PAID', 5, 1, 1); -- Marko -- Cinaplexxx -- Dictator -- Hall 1
	-- 19/08/2020  -- ID #4

-- Grades --
INSERT INTO public.grades(id, grade, customer_id, movie_id)
	VALUES (nextval('grade_id_seq'), 6, 5, 3);
INSERT INTO public.grades(id, grade, customer_id, movie_id)
	VALUES (nextval('grade_id_seq'), 8, 5, 4);

INSERT INTO public.grades(id, grade, customer_id, movie_id)
	VALUES (nextval('grade_id_seq'), 7, 4, 2);
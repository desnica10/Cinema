-- Users --
    -- ADMIN --
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'admin', '123', true, 'ADMIN', '1968-08-12', 'admin@gmail.com', 'Admin', 'Admin',
	'064/7891789');

	-- MENAGER --
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'menager', '123', true, 'MANAGER', '1988-02-06', 'menager@gmail.com', 'Menager',
	'Menager', '064/7891789');
INSERT INTO public.users (id, username, password, active, type, birth, email, first_name, last_name, phone)
	VALUES (nextval('user_id_seq'),'stefan', '123', true, 'MANAGER', '1997-04-23', 'stefandesnica@gmail.com', 'Stefan',
	'Desnica', '061/3121789');

-- Cinemas --
INSERT INTO public.cinemas (id, name, address, email, phone)
    VALUES (nextval('cinema_id_seq'), 'Cinaplexxx', 'Bulevar Oslobodjenja 5', 'cinaplexxx@gmail.com', '061/2211789');

-- Cinemas - Menagers --
INSERT INTO public.menagers (cinema_id, user_id) VALUES (1, 2);

-- Halls --
INSERT INTO public.halls (id, capacity, name, cinema_id)
	VALUES (nextval('hall_id_seq'), 30, 'Hall 1', 1);

-- Movies --
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 7.8, 'Opis 1', 120, 'King', 'ACTION');
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 8, 'Opis 2', 90, 'Mr. Bean', 'COMEDY');
INSERT INTO public.movies(id, grade, description, duration, name, type)
	VALUES (nextval('movie_id_seq'), 6.8, 'Opis 3', 90, 'Dictator', 'COMEDY');

-- Projections --
INSERT INTO public.projections(id, date, price, cinema_id, movie_id)
	VALUES (nextval('projection_id_seq'), '2020-08-22', 450, 1, 3);

-- Projection-Halls --
INSERT INTO public.projections_halls(projection_id, hall_id)
	VALUES (1, 1);

-- insert into ROLES (name) values ('ROLE_PASSENGER1');
-- insert into ROLES (name) values ('ROLE_DRIVER1');
-- insert into ROLES (name) values ('ROLE_ADMIN1');


--admin
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 12, Novi Sad', false, 'admin@gmail.com', 'Filip', 'Vuksan', 'fica123', '0664587545', 'picture')
-- insert into USER_ROLE(user_id, role_id) values (1,3)
insert into ADMINS(id) values (1)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 1)

--passenger
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 127, Novi Sad', false, 'marko.markovic@gmail.com', 'Marko', 'Markovic', 'marko123', '06652685', 'picture')
insert into PASSENGER(id) values (2)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 2)
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 2, Novi Sad', false, 'petar.petrovic@gmail.com', 'Petar', 'Petrovic', 'petar123', '066874521', 'picture')
insert into PASSENGER(id) values (3)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 3)
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 44, Novi Sad', false, 'janko.jankovic@gmail.com', 'Janko', 'Jankovic', 'janko123', '0654784595', 'picture')
insert into PASSENGER(id) values (4)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 4)

--vehicle type
insert into VEHICLE_TYPE(price_per_km, vehicle_name) values (500, 'STANDARD')
insert into VEHICLE_TYPE(price_per_km, vehicle_name) values (570, 'VAN')

--location
insert into LOCATION(address, latitude, longitude) values ('Radnicka 19, Novi Sad', 45.249101856630546, 19.848034)
insert into LOCATION(address, latitude, longitude) values ('Zeleznicka 2, Novi Sad', 45.2493924092008, 19.840783700998372 )
insert into LOCATION(address, latitude, longitude) values ('Jevrejska 2, Novi Sad', 45.254090, 19.841760)
insert into LOCATION(address, latitude, longitude) values ('Vladike Platona 2, Novi Sad', 45.252250, 19.848140)

--vehicle
insert into VEHICLE(baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (true, 'NS 234 NR', false, 4, 'Audi A4', 1, 1)
insert into VEHICLE(baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (true, 'NS 444 VT', true, 4, 'Opel astra', 2, 2)

--driver
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 78, Novi Sad', false, 'andrea.katzenberger@gmail.com', 'Andrea', 'Katzenberger', 'andrea123', '0641254658', 'picture')
insert into DRIVER(id, vehicle_id) values (5, 1)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 5)
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 27, Novi Sad', false, 'bojana.popov@gmail.com', 'Bojana', 'Popov', 'bojana123', '0634857954', 'picture')
insert into DRIVER(id, vehicle_id) values (6, 2)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 6)
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (true, 'Bulevar oslobodjenja 7, Novi Sad', false, 'mirko.mirkovic@gmail.com', 'Mirko', 'Mirkovic', 'mirko123', '0645879458', 'picture')
insert into DRIVER(id, vehicle_id) values (7, null)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 7)

--document
insert into DOCUMENT(image, name, driver_id) values ('image', 'vozacka dozvola', 5)
insert into DOCUMENT(image, name, driver_id) values ('image', 'licna karta', 5)
insert into DOCUMENT(image, name, driver_id) values ('image', 'vozacka dozvola', 6)
insert into DOCUMENT(image, name, driver_id) values ('image', 'licna karta', 6)

--TIMESTAMP '1999-01-31 10:00:00'

--ride 1
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (true, TIMESTAMP '1999-01-31 10:00:00', 10, true, true, TIMESTAMP '1999-01-31 10:00:00', 'PENDING', 2500, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (12, 1, 2)
insert into RIDE_ROUTES(ride_id, routes_id) values (1, 1)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (1, 2)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (1, 3)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 1)

--review for vehicle
insert into REVIEW(comment, rating, review_type, passenger_id, ride_id) values ('Vozilo je prljavo', 3.5, 'VEHICLE', 2, 1)
insert into RIDE_REVIEWS(ride_id, reviews_id) values (1, 1)
insert into VEHICLE_REVIEWS(vehicle_id, reviews_id) values (1, 1)

--review for driver
insert into REVIEW(comment, rating, review_type, passenger_id, ride_id) values ('Vozac vrlo ljubazan', 4.5, 'DRIVER', 3, 1)
insert into RIDE_REVIEWS(ride_id, reviews_id) values (1, 2)

--panic
insert into PANIC(reason, time, ride_id, user_id) values ('Putnici se nisu pojavili', TIMESTAMP '1999-01-31 10:00:00', 1, 5)
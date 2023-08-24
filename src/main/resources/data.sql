--admin
--pass: fica123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, reset_password_token, reset_password_token_expiration, role) values (true, 'Bulevar oslobodjenja 12 Novi Sad', false, 'admin@gmail.com', 'Filip', 'Vuksan', '$2a$10$xxZY6ssiuGbG/YwQ.3Uf..Ac5mS8zW8viMNsOcot7JLUYVUg6rqTm', '0664587545', 'picture', 'LOCAL', 'token', '2017-10-01 21:58:58.508-07', 'ADMIN')
insert into ADMINS(id) values (1)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 1)

--passenger
--pass: marko123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, role) values (true, 'Bulevar oslobodjenja 127, Novi Sad', false, 'marko.markovic@gmail.com', 'Marko', 'Markovic', '$2a$10$pW7xhKHkW/n6UAr1zlrK8OG9lZgio/Ix/K3dltB512sCDRL8h9ZI2', '06652685', 'picture', 'LOCAL', 'PASSENGER')
insert into PASSENGER(id) values (2)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 2)
--pass: petar123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, role) values (true, 'Bulevar oslobodjenja 2, Novi Sad', false, 'petar.petrovic@gmail.com', 'Petar', 'Petrovic', '$2a$10$cQnYiXJL0XHGoX27SCRLjOhoHRzKWlzkNP5sEXjStrFdtjAntCHH6', '066874521', 'picture', 'LOCAL', 'PASSENGER')
insert into PASSENGER(id) values (3)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 3)
--pass: janko123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, role) values (true, 'Bulevar oslobodjenja 44, Novi Sad', false, 'janko.jankovic@gmail.com', 'Janko', 'Jankovic', '$2a$10$gbCmI8/02BLAcEAov2.H9eyCwWDhE7aenS7bxfByfERuRnP1Buaa2', '0654784595', 'picture', 'LOCAL', 'PASSENGER')
insert into PASSENGER(id) values (4)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 4)

--vehicle type
insert into VEHICLE_TYPE(price_per_km, vehicle_name) values (500, 'STANDARD')
insert into VEHICLE_TYPE(price_per_km, vehicle_name) values (570, 'VAN')
insert into VEHICLE_TYPE(price_per_km, vehicle_name) values (650, 'LUXURY')

--location
insert into LOCATION(address, latitude, longitude) values ('Radnicka 19, Novi Sad', 45.249101856630546, 19.848034)
insert into LOCATION(address, latitude, longitude) values ('Zeleznicka 2, Novi Sad', 45.2493924092008, 19.840783700998372 )
insert into LOCATION(address, latitude, longitude) values ('Jevrejska 2, Novi Sad', 45.254090, 19.841760)
insert into LOCATION(address, latitude, longitude) values ('Vladike Platona 2, Novi Sad', 45.252250, 19.848140)

--vehicle
insert into VEHICLE(baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (true, 'NS 234 NR', false, 4, 'Audi A4', 1, 1)
insert into VEHICLE(baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (true, 'NS 444 VT', true, 4, 'Opel astra', 2, 1)
insert into VEHICLE(baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (false, 'NS 444 VT', false, 4, 'BMW', 3, 3)

--driver
--pass: andrea123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, role) values (true, 'Bulevar oslobodjenja 78, Novi Sad', false, 'andrea.katzenberger@gmail.com', 'Andrea', 'Katzenberger', '$2a$10$PelwlV29qdhWztr21ChMBeYYQUkWMuJhJhYqLKBKQiTa7he8qaIN6', '0641254658', 'picture', 'LOCAL', 'DRIVER')
insert into DRIVER(id, vehicle_id) values (5, 1)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 5)
--pass: bojana123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, role) values (true, 'Bulevar oslobodjenja 27, Novi Sad', false, 'bojana.popov@gmail.com', 'Bojana', 'Popov', '$2a$10$L0CStOxSs0ZS.aFea9/AaOvk2//F5/3SDIGr266nwbm7EURWDKgaK', '0634857954', 'picture', 'LOCAL', 'DRIVER')
insert into DRIVER(id, vehicle_id) values (6, 2)
insert into USER_ACTIVATION(date, lifespan, user_id) values ('2023-01-20 13:51:00.900595', 3, 6)
--pass: mirko123
insert into USERS(active, address, blocked, email, first_name, last_name, password, phone_number, picture, provider, role) values (true, 'Bulevar oslobodjenja 7, Novi Sad', false, 'mirko.mirkovic@gmail.com', 'Mirko', 'Mirkovic', '$2a$10$bm0/QkZmhQEQVI8LwFV9QeescIjZjdJSHre/xK2lJw020xCHJxV0m', '0645879458', 'picture', 'LOCAL', 'DRIVER')
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

--ride 2
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (true, TIMESTAMP '2023-01-30 14:30:00', 30, false, true, TIMESTAMP '2023-01-30 14:00:00', 'FINISHED', 2500, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (12, 1, 2)
insert into RIDE_ROUTES(ride_id, routes_id) values (2, 2)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (2, 2)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 2)

--ride 3
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (false, TIMESTAMP '2023-01-28 16:00:00', 60, false, true, TIMESTAMP '2023-01-28 15:00:00', 'PENDING', 2500, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (20, 1, 3)
insert into RIDE_ROUTES(ride_id, routes_id) values (3, 3)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (3, 3)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 3)

--ride 4
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, scheduled_time, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (true, TIMESTAMP '2023-01-31 17:00:00', 30, true, true, TIMESTAMP '2023-01-31 16:25:00', TIMESTAMP '2023-01-31 16:30:00', 'STARTED', 2500, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (20, 2, 4)
insert into RIDE_ROUTES(ride_id, routes_id) values (4, 4)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (4, 2)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 4)

--ride 5
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (true, TIMESTAMP '2023-08-23 14:30:00', 30, false, true, TIMESTAMP '2023-08-23 14:00:00', 'FINISHED', 2500, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (12, 1, 2)
insert into RIDE_ROUTES(ride_id, routes_id) values (5, 5)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (5, 2)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 5)

--ride 6
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (true, TIMESTAMP '2023-08-22 14:30:00', 30, false, true, TIMESTAMP '2023-08-22 14:00:00', 'FINISHED', 2000, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (5, 2, 4)
insert into RIDE_ROUTES(ride_id, routes_id) values (6, 6)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (6, 2)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 6)

--ride 7
insert into RIDE(baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (true, TIMESTAMP '2023-08-21 14:30:00', 30, false, true, TIMESTAMP '2023-08-21 14:00:00', 'FINISHED', 1000, 5, null, 1)
insert into ROUTE(km, departure_id, destination_id) values (16, 1, 3)
insert into RIDE_ROUTES(ride_id, routes_id) values (7, 7)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (7, 2)
insert into DRIVER_RIDES(driver_id, rides_id) values (5, 7)

--review for vehicle
insert into REVIEW(comment, rating, review_type, passenger_id, ride_id) values ('Vozilo je prljavo', 3.5, 'VEHICLE', 2, 1)
insert into RIDE_REVIEWS(ride_id, reviews_id) values (1, 1)
insert into VEHICLE_REVIEWS(vehicle_id, reviews_id) values (1, 1)

--review for driver
insert into REVIEW(comment, rating, review_type, passenger_id, ride_id) values ('Vozac vrlo ljubazan', 4.5, 'DRIVER', 3, 1)
insert into RIDE_REVIEWS(ride_id, reviews_id) values (1, 2)

--panic
insert into PANIC(reason, time, ride_id, user_id) values ('Strasan je', TIMESTAMP '1999-01-31 10:00:00', 1, 5)

--favourite route 1
insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime1', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (1,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (1,1)

--favourite route 2
insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime2', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (2,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (2,2)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime3', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (3,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (3,1)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime4', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (4,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (4,1)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime5', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (5,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (5,1)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime6', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (6,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (6,1)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime7', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (7,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (7,1)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime8', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (8,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (8,1)

insert into FAVORITE_ROUTES(baby_transport, favorite_name, pet_transport, vehicle_type) values (true, 'ime9', false, 'STANDARD')
insert into FAVOURITE_ROUTE_PASSENGER(favourite_route_id, passenger_id) values (9,2)
insert into FAVOURITE_ROUTE_ROUTE (favourite_route_id, route_id) values (9,1)

insert into WORKING_HOUR(start, end_time, driver_id) values (TIMESTAMP '2023-08-08 12:20:34.222', TIMESTAMP '2023-08-08 20:20:34.222', 5)

--messages
insert into MESSAGE(message, ride_id, sending_time, type, reciever_id, sender_id) values ('Cao!', 1, TIMESTAMP '2023-08-23 12:20:34.222', 'RIDE', 5, 2)
insert into MESSAGE(message, ride_id, sending_time, type, reciever_id, sender_id) values ('Pozdrav!', 1, TIMESTAMP '2023-08-23 12:22:44.222', 'RIDE', 2, 5)
insert into MESSAGE(message, ride_id, sending_time, type, reciever_id, sender_id) values ('Kako si?', 1, TIMESTAMP '2023-08-23 12:25:34.222', 'RIDE', 5, 2)

--chats
insert into CHAT(user1_id, user2_id) values (5, 2)
insert into CHAT_MESSAGES(chat_id, messages_id) values (1, 1)
insert into CHAT_MESSAGES(chat_id, messages_id) values (1, 2)
insert into CHAT_MESSAGES(chat_id, messages_id) values (1, 3)
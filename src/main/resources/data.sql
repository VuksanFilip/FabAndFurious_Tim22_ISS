--admin
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (1, true, 'Bulevar oslobodjenja 12, Novi Sad', false, 'admin@gmail.com', 'Filip', 'Vuksan', 'fica123', '0664587545', 'picture')
insert into ADMIN(id) values (1)

--passenger
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (2, true, 'Bulevar oslobodjenja 127, Novi Sad', false, 'marko.markovic@gmail.com', 'Marko', 'Markovic', 'marko123', '06652685', 'picture')
insert into PASSENGER(id) values (2)
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (3, true, 'Bulevar oslobodjenja 2, Novi Sad', false, 'petar.petrovic@gmail.com', 'Petar', 'Petrovic', 'petar123', '066874521', 'picture')
insert into PASSENGER(id) values (3)
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (4, true, 'Bulevar oslobodjenja 44, Novi Sad', false, 'janko.jankovic@gmail.com', 'Janko', 'Jankovic', 'janko123', '0654784595', 'picture')
insert into PASSENGER(id) values (4)

--vehicle type
insert into VEHICLE_TYPE(id, price_per_km, vehicle_name) values (1, 500, 'STANDARD')
insert into VEHICLE_TYPE(id, price_per_km, vehicle_name) values (2, 570, 'VAN')

--location
insert into LOCATION(id, address, latitude, longitude) values (1, 'Radnicka 19, Novi Sad', 45.249101856630546, 19.848034)
insert into LOCATION(id, address, latitude, longitude) values (2, 'Zeleznicka 2, Novi Sad', 45.2493924092008, 19.840783700998372 )
insert into LOCATION(id, address, latitude, longitude) values (3, 'Jevrejska 2, Novi Sad', 45.254090, 19.841760)
insert into LOCATION(id, address, latitude, longitude) values (4, 'Vladike Platona 2, Novi Sad', 45.252250, 19.848140)

--vehicle
insert into VEHICLE(id, baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (1, true, 'NS 234 NR', false, 4, 'Audi A4', 1, 1)
insert into VEHICLE(id, baby_friendly, license_number, pet_friendly, seats, vehicle_model, current_location_id, vehicle_type_id) values (2, true, 'NS 444 VT', true, 4, 'Opel astra', 2, 2)

--driver
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (5, true, 'Bulevar oslobodjenja 78, Novi Sad', false, 'andrea.katzenberger@gmail.com', 'Andrea', 'Katzenberger', 'andrea123', '0641254658', 'picture')
insert into DRIVER(id, vehicle_id) values (5, 1)
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (6, true, 'Bulevar oslobodjenja 27, Novi Sad', false, 'bojana.popov@gmail.com', 'Bojana', 'Popov', 'bojana123', '0634857954', 'picture')
insert into DRIVER(id, vehicle_id) values (6, 2)
insert into USER(id, active, address, blocked, email, first_name, last_name, password, phone_number, picture) values (7, true, 'Bulevar oslobodjenja 7, Novi Sad', false, 'mirko.mirkovic@gmail.com', 'Mirko', 'Mirkovic', 'mirko123', '0645879458', 'picture')
insert into DRIVER(id, vehicle_id) values (7, null)

--document
insert into DOCUMENT(id, image, name, driver_id) values (1, 'image', 'vozacka dozvola', 5)
insert into DOCUMENT(id, image, name, driver_id) values (2, 'image', 'licna karta', 5)
insert into DOCUMENT(id, image, name, driver_id) values (3, 'image', 'vozacka dozvola', 6)
insert into DOCUMENT(id, image, name, driver_id) values (4, 'image', 'licna karta', 6)

--TIMESTAMP '1999-01-31 10:00:00'

--ride 1
insert into RIDE(id, baby_transport, end_time, estimated_time_in_minutes, panic, pet_transport, start_time, status, total_cost, driver_id, letter_id, vehicle_id) values (1, true, TIMESTAMP '1999-01-31 10:00:00', 10, false, true, TIMESTAMP '1999-01-31 10:00:00', 'FINISHED', 2500, 5, null, 1)
insert into ROUTE(id, km, departure_id, destination_id) values (1, 12, 1, 2)
insert into RIDE_ROUTES(ride_id, routes_id) values (1, 1)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (1, 2)
insert into RIDE_PASSENGER(ride_id, passenger_id) values (1, 3)
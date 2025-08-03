-- This file allow to write SQL commands that will be emitted in test and dev.
-- The commands are commented as their support depends of the database
-- insert into myentity (id, field) values(1, 'field-1');
-- insert into myentity (id, field) values(2, 'field-2');
-- insert into myentity (id, field) values(3, 'field-3');
-- alter sequence myentity_seq restart with 4;

insert into vehicles (brand, engine, model, vehicleYear, status)  values ( 'BMW', 'V8', 'X5', 2020, 'AVAILABLE' );
insert into vehicles (brand, engine, model, vehicleYear, status)  values ( 'Audi', 'V6', 'A4', 2019, 'AVAILABLE');

insert into booking (customerName, startDate, endDate, vehicle_id, status) values ('John Doe', '2023-10-01', '2023-10-05', 1, 'CREATED');
insert into booking (customerName, startDate, endDate, vehicle_id, status) values ('Jane Smith', '2023-11-02', '2023-11-06', 1, 'CREATED');

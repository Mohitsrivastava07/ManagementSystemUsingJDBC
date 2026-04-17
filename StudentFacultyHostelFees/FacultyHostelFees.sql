-- create the database of studentfacultyhostelfees_db;
create database studentfacultyhostelfees_db;

-- use this database
use studentfacultyhostelfees_db;

-- create the table of studentshostelfees
create table facultyhostelfees (
	kid varchar(6) primary key not null,
    faculty_name varchar(255) not null,
    email_id varchar(255) unique not null,
    phone_number int(10) not null,
    which_month varchar(30) not null,
    address varchar(355) not null,
    collected_by varchar(255) not null,
    date_time timestamp default current_timestamp
);

-- shifting the address column after phone_number columns
alter table facultyhostelfees modify column address varchar(355) not null after phone_number;

-- adding the room_number after address column
alter table facultyhostelfees add column room_number varchar(4) not null after address;

-- changes the phone_number data type
alter table facultyhostelfees modify phone_number varchar(10) not null;

-- retrieve the table
select * from facultyhostelfees;


-- creating the database with name studenthostelfees_db
create database studenthostelfees_db;

-- firstly using the name of database studenthostelfees_db for CRUD operation with 'use' keyword
use studenthostelfees_db;

-- creating the table name with studentfees
create table studentfees (
	kid varchar(6) primary key not null,
    name varchar(155) not null,
    email_id varchar(255) not null,
    phone_number varchar(10) not null,
    month varchar(20) not null,
    withdraw_by varchar(155) not null,
    date_time timestamp default current_timestamp
);

-- retrieve the table of studentfees
select * from studentfees;
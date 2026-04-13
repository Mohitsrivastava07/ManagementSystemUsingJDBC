-- Creating the database name of hospitalappointment_db
create database hospitalappointment_db;

-- Use this database
use hospitalappointment_db;

-- Creatig the table name of patients
create table patients (
	patient_id int auto_increment primary key,
    patient_name varchar(155) not null,
    patient_age int(3) not null,
    gender varchar(20) not null,
    date_time timestamp default current_timestamp
);

-- Creting the table name of doctors
create table doctors (
	doctor_id int auto_increment primary key,
    doctor_name varchar(155) not null,
    doctor_specialization varchar(100) not null,
    date_time timestamp default current_timestamp
);

-- create the table name of appointments
create table appointments (
	appointment_id int auto_increment primary key,
    patient_id int not null, foreign key(patient_id) references patients(patient_id) on delete cascade,
    doctor_id int not null, foreign key(doctor_id) references doctors(doctor_id) on delete cascade,
    appointment_date varchar(15) not null,
    date_time timestamp default current_timestamp
);

-- Retrieve the patients table
select * from patients;

-- Retrieve the doctors table
select * from doctors;

-- Retrieve the appointments table
select * from appointments;

-- Insertinng the data into doctors table
insert into doctors(doctor_name, doctor_specialization) values 
("Dr. Ravi Ranjan", "Pediatrics"),
("Dr. Suresh Singh", "Geriatrics"),
("Dr. Keshav Pathak", "Cardiology"),
("Dr. B.N. Chaudhary", "Oncology"),
('Dr. Amit Sharma', 'Cardiologist'),
('Dr. Neha Verma', 'Dermatologist'),
('Dr. Rajesh Gupta', 'Neurologist'),
('Dr. Priya Singh', 'Pediatrician'),
('Dr. Anil Mehta', 'Orthopedic'),
('Dr. Kavita Joshi', 'Gynecologist'),
('Dr. Rohit Agarwal', 'ENT Specialist'),
('Dr. Sunita Reddy', 'Ophthalmologist'),
('Dr. Vikram Chauhan', 'General Physician'),
('Dr. Pooja Malhotra', 'Psychiatrist'),
('Dr. Sandeep Yadav', 'Urologist'),
('Dr. Meena Iyer', 'Endocrinologist'),
('Dr. Arjun Kapoor', 'Oncologist'),
('Dr. Sneha Patil', 'Radiologist'),
('Dr. Deepak Mishra', 'Gastroenterologist'),
('Dr. Ritu Saxena', 'Dentist'),
('Dr. Manish Tiwari', 'Pulmonologist'),
('Dr. Aarti Deshmukh', 'Nephrologist'),
('Dr. Karan Khanna', 'Surgeon'),
('Dr. Shalini Pandey', 'Physiotherapist');
DROP DATABASE courier_management_IMT2019030;

CREATE DATABASE courier_management_IMT2019030;

USE courier_management_IMT2019030;

CREATE TABLE students(
    student_id VARCHAR(20) PRIMARY KEY,
    student_name VARCHAR(20),
    student_email VARCHAR(20),
    student_phone_number VARCHAR(20)
);

CREATE TABLE source_details(
    source_id VARCHAR(20) PRIMARY KEY,
    source_name VARCHAR(20),
    source_contact VARCHAR(20)
);

CREATE TABLE packages(
    package_id INT PRIMARY KEY,
    package_name VARCHAR(20),
    student_id VARCHAR(20),
    source_id VARCHAR(20),
    FOREIGN KEY (student_id) REFERENCES students(student_id),
    FOREIGN KEY (source_id) REFERENCES source_details(source_id)
);
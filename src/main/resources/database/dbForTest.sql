CREATE DATABASE School;
DROP DATABASE School;
USE School;

# select * from for test

select * from activity;
select * from users;
select * from discipline;
select * from responsible;
select * from students;
select * from class;
select * from grade;
select * from grade_student;
select * from grade_discipline;
select * from class_discipline;
select * from class_student;
select * from electives;
select * from rating;

DESCRIBE activity;

# Drop tables for test

DROP TABLE users;
DROP TABLE class;
DROP TABLE discipline;
DROP TABLE students;
DROP TABLE responsible;
DROP TABLE class_discipline;
DROP TABLE electives;
DROP TABLE rating;
DROP TABLE grade;
DROP TABLE grade_student;
DROP TABLE grade_discipline;
DROP TABLE activity;
DROP TABLE activity_class;
DROP TABLE activity_student;
DROP TABLE activity_discipline;

ALTER TABLE activity
DROP COLUMN activityNumber;

ALTER TABLE activity
DROP COLUMN year;

# Inserts for test
INSERT INTO users (cpf, email, name, password, roles, username) VALUES 
('12309845670', 'teacher@example.com', 'teacherUser', '$2a$10$XQ53ObWwSxDluLgHa1k3/.ElzO.qNw7rWoO08Ps.QVxA1pNlvLmSK', 'ROLE_TEACHER', 'nickTeacher'),
('78945612309', 'student@example.com', 'studentUser', '$2a$10$gEDj0eU8m5Y6OzRbfpFfZ.ONzYoP9X8.O6zAvqK4FA3v4yZyfSiGq', 'ROLE_STUDENT', 'nickStudent'),
('00000000000', 'secretary@example.com', 'secretaryUser', '$2a$10$d.6YFjppEyYZ2BfJPrRdoeqD2LjGRuBdN4xWqW/ZTjsc6MXdKsZFa', 'ROLE_SECRETARY', 'nickSecretary');
#('45678912301', 'admin@example.com', 'adminUser', '$2a$10$d.6YFjppEyYZ2BfJPrRdoeqD2LjGRuBdN4xWqW/ZTjsc6MXdKsZFa', 'ROLE_ADMIN', 'nickAdmin');

INSERT INTO class (name, year) VALUES
('1-A', '2024'),
('2-A', '2024'),
('3-A', '2024'),
('1-B', '2024'),
('2-B', '2024'),
('3-B', '2024');

INSERT INTO discipline (name) VALUES
('Biology'),
('Mathematic'),
('Portuguese'),
('History');

INSERT INTO responsible (cpf, address, name, phone) VALUES
('98765432109', 'Rua dos Testes, 123', 'João da Silva', '81987654321'),
('12345678901', 'Avenida Principal, 456', 'Maria Souza', '21123456789'),
('45678912304', 'Rua das Flores, 789', 'Pedro Oliveira', '19456789123'),
('78912345605', 'Rua dos Pássaros, 101', 'Ana Santos', '41789123456'),
('32165498706', 'Avenida Central, 234', 'Carlos Pereira', '31321654987');

INSERT INTO students (birthdate, cpf, name, cpf_responsible) VALUES
('1999-12-31', '12345678901', 'João da Silva', '98765432109'),
('2005-04-15', '98765432102', 'Maria Oliveira', '12345678901'),
('2003-08-22', '45678912303', 'Pedro Santos', '45678912304'),
('2001-11-07', '78912345604', 'Ana Souza', '78912345605'),
('2002-06-19', '32165498705', 'Lucas Pereira', '32165498706'),
('2004-03-10', '65432198701', 'Clara Silva', '98765432109'),
('2006-07-25', '78945612302', 'Rafael Souza', '12345678901'),
('2000-09-30', '32178965403', 'Beatriz Oliveira', '45678912304'),
('2002-12-12', '65498732104', 'Gabriel Santos', '78912345605'),
('2003-05-18', '98732165405', 'Larissa Pereira', '32165498706');      
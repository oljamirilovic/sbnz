INSERT INTO role_table (id, name)
VALUES (1, 'THERAPIST');
INSERT INTO role_table (id, name)
VALUES (2, 'PATIENT');

-- LOZINKE SVIH KORISNIKA SU 'test' :)

insert into users (username, password, first_name, last_name, role_id)
values ('therapist1', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Therapist1', 'Therapistson1', 1);
insert into therapist (id) values (1);

insert into users (username, password, first_name, last_name, role_id)
values ('patient1', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Patient1', 'Patientson1', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (1, 50, 'MALE', 1000.0, 'MODERATE');

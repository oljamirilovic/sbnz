INSERT INTO role_table (id, name)
VALUES (1, 'THERAPIST');
INSERT INTO role_table (id, name)
VALUES (2, 'PATIENT');

-- LOZINKE SVIH KORISNIKA SU 'test' :)

----------------USERS------------------------
insert into users (username, password, first_name, last_name, role_id)
values ('therapist1', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Therapist1', 'Therapistson1', 1);
insert into therapist (id) values (1);

insert into users (username, password, first_name, last_name, role_id)
values ('patient1', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Peter', 'Peterson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (2, 60, 'MALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id)
values ('patient2', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'John', 'Johnson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (3, 30, 'MALE', 1000.0, 'VERY_ACTIVE');

insert into users (username, password, first_name, last_name, role_id)
values ('patient3', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Jane', 'Jensen', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (4, 40, 'FEMALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id)
values ('patient4', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Carol', 'Carlson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (5, 70, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id)
values ('patient5', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Sarah', 'Samson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (6, 55, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id)
values ('patient6', '$2y$10$t4NZP3qGGdzGakospEzFHOPQngmjvi7dZeZSiwfiNz.1rv/smO0Ce', 'Jack', 'Jackson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (7, 25, 'MALE', 1000.0, 'MODERATE');

----------------FAMILY------------------------
insert into family(child_id, parent_id) values (3, 2);
insert into family(child_id, parent_id) values (3, 6);

----------------SYMPTOMS------------------------
insert into symptom(name) values ('HEIGHT_LOSS');
insert into symptom(name) values ('WEIGHT_LOSS');
insert into symptom(name) values ('HIP_PAIN');
insert into symptom(name) values ('BACK_PAIN');
insert into symptom(name) values ('NECK_PAIN');
insert into symptom(name) values ('BAD_POSTURE');
insert into symptom(name) values ('SMOKER');
insert into symptom(name) values ('HORMONE_IMBALANCE');
insert into symptom(name) values ('EARLY_MENOPAUSE');
insert into symptom(name) values ('BONE_FRACTURE');

----------------ILLNESSES------------------------
insert into illness(name, testType) values ('OSTEOPOROSIS', 'BONE_DENSITY');
insert into illness(name, testType) values ('OSTEOPENIA', 'BONE_DENSITY');

--osteoporosis
insert into illnessSymptoms(illness_id, symptom_id) values (1, 1);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 2);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 4);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 6);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 8);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 9);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 10);

--osteopenia
insert into illnessSymptoms(illness_id, symptom_id) values (1, 1);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 2);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 4);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 6);
insert into illnessSymptoms(illness_id, symptom_id) values (1, 10);

---------------TEST_RESULTS------------------------
insert into test_result(score) values (3);

----------------APPOINTMENTS------------------------
insert into appointment(therapist_id, date, resolved) values (1, '2021-10-23', true);

----------------DIAGNOSIS------------------------
insert into diagnosis(date, illness_id, test_result_id, patient_id, appointment_id)
values ('2021-10-23', 1, 1, 3, 1);

----------------THERAPY------------------------
insert into therapy(minutes, therapyType, startDate, endDate, diagnosis_id) values
(15, 'KINESI_THERAPY', '2021-10-23', '2021-11-07', 1);
insert into therapy(minutes, therapyType, startDate, endDate, diagnosis_id) values
(30, 'KINESI_THERAPY', '2021-11-07', '2021-11-21', 1);

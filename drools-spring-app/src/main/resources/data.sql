INSERT INTO role_table (id, name)
VALUES (1, 'THERAPIST');
INSERT INTO role_table (id, name)
VALUES (2, 'PATIENT');

-- LOZINKE SVIH KORISNIKA SU '123' :)

----------------USERS------------------------
insert into users (username, password, first_name, last_name, role_id)
values ('therapist1', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Therapist1', 'Therapistson1', 1);
insert into therapist (id) values (1);

insert into users (username, password, first_name, last_name, role_id)
values ('patient1', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Peter', 'Peterson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (2, 60, 'MALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id)
values ('patient2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'John', 'Johnson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (3, 30, 'MALE', 800.0, 'VERY_ACTIVE');

insert into users (username, password, first_name, last_name, role_id)
values ('patient3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Jane', 'Jensen', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (4, 40, 'FEMALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id)
values ('patient4', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Carol', 'Carlson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (5, 70, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id)
values ('patient5', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Sarah', 'Samson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (6, 55, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id)
values ('patient6', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Jack', 'Jackson', 2);
insert into patient (id, age, gender, bmd, physical_activity) values (7, 25, 'MALE', 1000.0, 'MODERATE');

----------------FAMILY------------------------
insert into family(child_id, parent_id) values (3, 2);
insert into family(child_id, parent_id) values (3, 6);

----------------SYMPTOMS------------------------
insert into symptom(name) values ('HEIGHT LOSS');
insert into symptom(name) values ('WEIGHT LOSS');
insert into symptom(name) values ('HIP PAIN');
insert into symptom(name) values ('BACK PAIN');
insert into symptom(name) values ('NECK PAIN');
insert into symptom(name) values ('BAD POSTURE');
insert into symptom(name) values ('SMOKER');
insert into symptom(name) values ('HORMONE IMBALANCE');
insert into symptom(name) values ('EARLY MENOPAUSE');
insert into symptom(name) values ('BONE FRACTURE');

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
insert into illnessSymptoms(illness_id, symptom_id) values (2, 1);
insert into illnessSymptoms(illness_id, symptom_id) values (2, 2);
insert into illnessSymptoms(illness_id, symptom_id) values (2, 4);
insert into illnessSymptoms(illness_id, symptom_id) values (2, 6);
insert into illnessSymptoms(illness_id, symptom_id) values (2, 10);

---------------TEST_RESULTS------------------------
insert into test_result(score) values (3);

----------------DIAGNOSIS------------------------
insert into diagnosis(date, illness_id, test_result_id, patient_id)
values ('2021-10-23', 1, 1, 3);
insert into diagnosis(date, illness_id, test_result_id, patient_id)
values ('2022-06-22', null, null, 3);

----------------APPOINTMENTS------------------------
insert into appointment(therapist_id, date, diagnosis_id, resolved) values (1, '2021-10-23', 1, true);
insert into appointment(therapist_id, date, diagnosis_id, resolved) values (1, '2022-06-22', 2, false);

----------------THERAPY------------------------
insert into therapy(minutes, therapyType, startDate, endDate, diagnosis_id) values
(15, 'KINESI_THERAPY', '2021-10-23', '2021-11-07', 1);
insert into therapy(minutes, therapyType, startDate, endDate, diagnosis_id) values
(30, 'KINESI_THERAPY', '2021-11-07', '2021-11-21', 1);

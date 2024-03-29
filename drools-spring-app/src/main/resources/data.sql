INSERT INTO role_table (id, name)
VALUES (1, 'THERAPIST');
INSERT INTO role_table (id, name)
VALUES (2, 'PATIENT');
INSERT INTO role_table (id, name)
VALUES (3, 'ADMIN');

-- LOZINKE SVIH KORISNIKA SU '123' :)

----------------USERS------------------------
insert into users (username, password, first_name, last_name, role_id, deleted)
values ('therapist1', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Therapist1', 'Therapistson1', 1, false );
insert into therapist (id) values (1);

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient1', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Peter', 'Peterson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (2, 60, 'MALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'John', 'Johnson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (3, 30, 'MALE', 800.0, 'VERY_ACTIVE');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Jane', 'Jensen', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (4, 40, 'FEMALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient4', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Carol', 'Carlson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (5, 70, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient5', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Sarah', 'Samson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (6, 55, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient6', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Jack', 'Jackson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (7, 25, 'MALE', 1000.0, 'MODERATE');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient7', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Josh', 'Johnson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (8, 85, 'MALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('patient8', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Clara', 'Johnson', 2, false);
insert into patient (id, age, gender, bmd, physical_activity) values (9, 83, 'FEMALE', 1000.0, 'SEDENTARY');

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('admin', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Admin', 'Adminovic', 3, false);

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('therapist2', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Therapist2', 'Therapistson2', 1, false);
insert into therapist (id) values (11);

insert into users (username, password, first_name, last_name, role_id, deleted)
values ('therapist3', '$2a$04$Vbug2lwwJGrvUXTj6z7ff.97IzVBkrJ1XfApfGNl.Z695zqcnPYra', 'Therapist3', 'Therapistson3', 1, false);
insert into therapist (id) values (12);

----------------FAMILY------------------------
insert into family(child, parent) values ('patient2', 'patient1');
insert into family(child, parent) values ('patient2', 'patient5');
insert into family(child, parent) values ('patient5', 'patient4');
insert into family(child, parent) values ('patient1', 'patient7');
insert into family(child, parent) values ('patient1', 'patient8');

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
insert into symptom(name) values ('WEIGHT GAIN');
insert into symptom(name) values ('JOINT SWELLING');
insert into symptom(name) values ('KNEE CRACKING');
insert into symptom(name) values ('JOINT PAIN');

----------------ILLNESSES------------------------
insert into illness(name, testType) values ('OSTEOPOROSIS', 'BONE_DENSITY');
insert into illness(name, testType) values ('OSTEOPENIA', 'BONE_DENSITY');
insert into illness(name, testType) values ('ARTHROSIS', 'NA');
insert into illness(name, testType) values ('ARTHRITIS', 'NA');

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

--arthrosis
insert into illnessSymptoms(illness_id, symptom_id) values (3, 8);
insert into illnessSymptoms(illness_id, symptom_id) values (3, 11);
insert into illnessSymptoms(illness_id, symptom_id) values (3, 12);
insert into illnessSymptoms(illness_id, symptom_id) values (3, 13);
insert into illnessSymptoms(illness_id, symptom_id) values (3, 14);

--arthritis
insert into illnessSymptoms(illness_id, symptom_id) values (4, 14);
insert into illnessSymptoms(illness_id, symptom_id) values (4, 12);
insert into illnessSymptoms(illness_id, symptom_id) values (4, 2);
insert into illnessSymptoms(illness_id, symptom_id) values (4, 4);
insert into illnessSymptoms(illness_id, symptom_id) values (3, 3);

---------------TEST_RESULTS------------------------
insert into test_result(score) values (3);
insert into test_result(score) values (2);

----------------DIAGNOSIS------------------------
insert into diagnosis(date, illness_id, test_result_id, patient_id)
values ('2021-10-23', 1, 1, 3);
insert into diagnosis(date, illness_id, test_result_id, patient_id)
values ('2022-06-22', null, null, 3);
insert into diagnosis(date, illness_id, test_result_id, patient_id)
values ('2021-12-23', 2, 2, 9);

----------------APPOINTMENTS------------------------
insert into appointment(therapist_id, date, diagnosis_id, resolved) values (1, '2021-10-23', 1, true);
insert into appointment(therapist_id, date, diagnosis_id, resolved) values (1, '2022-08-15', 2, false);
insert into appointment(therapist_id, date, diagnosis_id, resolved) values (1, '2021-12-23', 3, true);

----------------THERAPY------------------------
insert into therapy(minutes, therapyType, startDate, endDate, resolved, diagnosis_id) values
(15, 'KINESI_THERAPY', '2021-10-23', '2021-11-07', true, 1);
insert into therapy(minutes, therapyType, startDate, endDate, resolved, diagnosis_id) values
(30, 'KINESI_THERAPY', '2022-08-07', null, false, 1);
insert into therapy(minutes, therapyType, startDate, endDate, resolved, diagnosis_id) values
(60, 'POOL_THERAPY', '2022-06-11', null, false, 3);

----------------JMR------------------------
insert into joint_motion_range(date, elbowFlexion, kneeFlexion, shoulderFlexion, flexionScore, diagnosis_id) values
('2022-06-11', 0, 0, 0, 2, 1);
insert into joint_motion_range(date, elbowFlexion, kneeFlexion, shoulderFlexion, flexionScore, diagnosis_id) values
('2022-06-11', 0, 0, 0, 3, 1);
insert into joint_motion_range(date, elbowFlexion, kneeFlexion, shoulderFlexion, flexionScore, diagnosis_id) values
('2022-06-11', 0, 0, 0, 2, 3);

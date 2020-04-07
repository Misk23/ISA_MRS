insert into authority (name) values ('ADMIN_ROLE');
insert into authority (name) values ('CLINIC_ADMIN_ROLE');
insert into authority (name) values ('DOCTOR_ROLE');
insert into authority (name) values ('NURSE_ROLE');
insert into authority (name) values ('PATIENT_ROLE');


-- DODAVANJE User naslednice sa dtype-om i dodavanje role-ova njemu
insert into user (username, password, dtype) values ('user', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 'Patient');
insert into user (username, password, dtype) values ('admin', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq', 'Admin');

insert into user_authority (user_id, authority_id) values (1, 5); -- user has PASSENGER_ROLE
insert into user_authority (user_id, authority_id) values (2, 1); -- admin has ADMIN_ROLE




insert into authority (name) values ('ADMIN_ROLE');
insert into authority (name) values ('CLINIC_ADMIN_ROLE');
insert into authority (name) values ('DOCTOR_ROLE');
insert into authority (name) values ('NURSE_ROLE');
insert into authority (name) values ('PATIENT_ROLE');


-- DODAVANJE User naslednice sa dtype-om i dodavanje role-ova njemu
insert into user (username,password, dtype,name, last_name, email) values ('misk', '$2a$04$Amda.Gm4Q.ZbXz9wcohDHOhOBaNQAkSS1QO26Eh8Hovu3uzEpQvcq','Admin','Milos', 'Pantic', 'mihajlo.jovkovic@gmail.com');
insert into user_authority (user_id, authority_id) values (1, 1); -- misk has ADMIN_ROLE




-- admin1  $2a$10$C1ES3Wvt8z16pwl3.vnJ1.0x7PnaZeSYPoM.2qm4B3kcxqlEM1l8u
-- supervisor1  $2a$10$AN8oSklq4UJeMeAbSrKCcus9EXVlDmHUx3W.iUIF3SXhns7K8/4Jm
-- supervisor2  $2a$10$gIcF5FSoYaJE0/Qvtjhe6egn1SsPG6EvFpEHuGhuH9QV80xSCdAIm
-- supervisor3  $2a$10$BPLRIiuPd4fWqLG00KEpAewTV9YyzbTYjOfZNq8lZ5ekoX0QWU582
-- supervisor4  $2a$10$LJtaMRVFZ0fO7V8RfPc2dOY./eC57hloOuSxuVYsqMns7OMnZrJHC
-- groupleader1  $2a$10$fifhquQDWOHEDyCed3ex7estscwVdqaSlThFKStCApYzupllnqUa6
-- groupleader2  $2a$10$aJRwhRFndDyW4Ek0XC1V.OD015eeLe8TnMhnXwXNwqwf4o1g5BPPq
-- groupleader3  $2a$10$ElIz9NWgto/VgfdCHnvFwu.GZFWbreFtu1qnl6UL1I5j2.f6ZN1Xu

INSERT INTO role (name) VALUES ('ROLE_ADMIN');
INSERT INTO role (name) VALUES ('ROLE_SUPERVISOR');
INSERT INTO role (name) VALUES ('ROLE_GROUPLEADER');



INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, role_id)
VALUES ('Super Admin', 'Super Admin', 'Skakanakou+oliveProjectAdmin@gmail.com',
       '$2a$10$C1ES3Wvt8z16pwl3.vnJ1.0x7PnaZeSYPoM.2qm4B3kcxqlEM1l8u',
       false, true, true, 1
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Alain', 'Patrice', 'Skakanakou+oliveProjectSupervisor1@gmail.com',
       '$2a$10$AN8oSklq4UJeMeAbSrKCcus9EXVlDmHUx3W.iUIF3SXhns7K8/4Jm',
       false, true, true, 1, 2
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Jean', 'Peter', 'Skakanakou+oliveProjectSupervisor2@gmail.com',
       '$2a$10$gIcF5FSoYaJE0/Qvtjhe6egn1SsPG6EvFpEHuGhuH9QV80xSCdAIm',
       false, true, true, 1, 2
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Jean', 'Peter', 'Skakanakou+oliveProjectSupervisor3@gmail.com',
       '$2a$10$BPLRIiuPd4fWqLG00KEpAewTV9YyzbTYjOfZNq8lZ5ekoX0QWU582',
       false, true, true, 1, 2
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Jean', 'Peter', 'Skakanakou+oliveProjectSupervisor4@gmail.com',
       '$2a$10$LJtaMRVFZ0fO7V8RfPc2dOY./eC57hloOuSxuVYsqMns7OMnZrJHC',
       false, true, true, 1, 2
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Miguel', 'Stephane', 'Skakanakou+oliveProjectGroupLeader1@gmail.com',
       '$2a$10$fifhquQDWOHEDyCed3ex7estscwVdqaSlThFKStCApYzupllnqUa6',
       false, true, true, 1,
       (SELECT id FROM role WHERE name='ROLE_GROUPLEADER')
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Miguel', 'Stephane', 'Skakanakou+oliveProjectGroupLeader2@gmail.com',
       '$2a$10$aJRwhRFndDyW4Ek0XC1V.OD015eeLe8TnMhnXwXNwqwf4o1g5BPPq6',
       false, true, true, 1,
       (SELECT id FROM role WHERE name='ROLE_GROUPLEADER')
       );

INSERT INTO user (first_name, last_name, email, password, is_default_pwd, is_enabled, is_email_verified, created_by_id, role_id)
VALUES ('Miguel', 'Stephane', 'Skakanakou+oliveProjectGroupLeader3@gmail.com',
        '$2a$10$ElIz9NWgto/VgfdCHnvFwu.GZFWbreFtu1qnl6UL1I5j2.f6ZN1Xu',
        false, true, true, 1,
        (SELECT id FROM role WHERE name = 'ROLE_GROUPLEADER')
       );



INSERT INTO site (code, name, is_enabled) VALUES ('TYUT-TAIYUAN', 'TYUT: Campus of Taiyuan', true);
INSERT INTO site (code, name, is_enabled) VALUES ('TYUT-YUCI', 'TYUT: Campus of Yuci', false);



INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-COMPUTER', 'building of Computer Science', (SELECT id FROM site WHERE site.code='TYUT-TAIYUAN'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-ART', 'building of Art and Culture', (SELECT id FROM site WHERE site.code='TYUT-TAIYUAN'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-MATH', 'building of Mathematics', (SELECT id FROM site WHERE site.code='TYUT-TAIYUAN'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-FGN-LANG', 'building of Foreign Language', (SELECT id FROM site WHERE site.code='TYUT-TAIYUAN'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-COMPUTER', 'building of Computer Science', (SELECT id FROM site WHERE site.code='TYUT-YUCI'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-ECO', 'building of Economy', (SELECT id FROM site WHERE site.code='TYUT-YUCI'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-MATH', 'building of Mathematics', (SELECT id FROM site WHERE site.code='TYUT-YUCI'), true);

INSERT INTO building (code, name, site_id, is_enabled)
VALUES ('BLD-SPORT', 'building of Sport', (SELECT id FROM site WHERE site.code='TYUT-YUCI'), true);



INSERT INTO classroom (code, name, building_id, is_enabled)
VALUES ('CLASS-101', 'classroom 101', 1, true);



INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'MONDAY', '12:00:00', '15:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'MONDAY', '19:00:00', '22:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'TUESDAY', '12:00:00', '15:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'TUESDAY', '19:00:00', '22:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'WEDNESDAY', '12:00:00', '15:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'WEDNESDAY', '19:00:00', '22:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'FRIDAY', '12:00:00', '15:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'FRIDAY', '19:00:00', '22:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'SATURDAY', '8:00:00', '20:00:00', 1, 2, current_date());

INSERT INTO available_time_interval (from_date, to_date, week_day, from_time, to_time, classroom_id, supervisor_id, created_date)
VALUES ('2021-04-1', '2021-06-30', 'SUNDAY', '8:00:00', '15:00:00', 1, 2, current_date());

INSERT INTO classroom_supervisor (supervisor_id, classroom_id, assigned_date, is_valid, who_assigned_id)
VALUES (2, 1, current_date(), true, 1);

INSERT INTO classroom_supervisor (supervisor_id, classroom_id, assigned_date, is_valid, who_assigned_id)
VALUES (3, 1, current_date(), true, 1);
-- admin1  $2a$10$C1ES3Wvt8z16pwl3.vnJ1.0x7PnaZeSYPoM.2qm4B3kcxqlEM1l8u
-- supervisor1  $2a$10$AN8oSklq4UJeMeAbSrKCcus9EXVlDmHUx3W.iUIF3SXhns7K8/4Jm
-- supervisor2  $2a$10$gIcF5FSoYaJE0/Qvtjhe6egn1SsPG6EvFpEHuGhuH9QV80xSCdAIm
-- supervisor3  $2a$10$BPLRIiuPd4fWqLG00KEpAewTV9YyzbTYjOfZNq8lZ5ekoX0QWU582
-- supervisor4  $2a$10$LJtaMRVFZ0fO7V8RfPc2dOY./eC57hloOuSxuVYsqMns7OMnZrJHC
-- groupleader1  $2a$10$fifhquQDWOHEDyCed3ex7estscwVdqaSlThFKStCApYzupllnqUa6
-- groupleader2  $2a$10$aJRwhRFndDyW4Ek0XC1V.OD015eeLe8TnMhnXwXNwqwf4o1g5BPPq
-- groupleader3  $2a$10$ElIz9NWgto/VgfdCHnvFwu.GZFWbreFtu1qnl6UL1I5j2.f6ZN1Xu

INSERT INTO Role (name) VALUES ('Administrator');
INSERT INTO Role (name) VALUES ('Supervisor');
INSERT INTO Role (name) VALUES ('GroupLeader');



INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, roleId)
VALUES ('Super Admin', 'Super Admin', 'Skakanakou+oliveProjectAdmin@gmail.com',
       '$2a$10$C1ES3Wvt8z16pwl3.vnJ1.0x7PnaZeSYPoM.2qm4B3kcxqlEM1l8u',
       false, true, true, 1
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Alain', 'Patrice', 'Skakanakou+oliveProjectSupervisor1@gmail.com',
       '$2a$10$AN8oSklq4UJeMeAbSrKCcus9EXVlDmHUx3W.iUIF3SXhns7K8/4Jm',
       false, true, true, 1, 2
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Jean', 'Peter', 'Skakanakou+oliveProjectSupervisor2@gmail.com',
       '$2a$10$gIcF5FSoYaJE0/Qvtjhe6egn1SsPG6EvFpEHuGhuH9QV80xSCdAIm',
       false, true, true, 1, 2
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Jean', 'Peter', 'Skakanakou+oliveProjectSupervisor3@gmail.com',
       '$2a$10$BPLRIiuPd4fWqLG00KEpAewTV9YyzbTYjOfZNq8lZ5ekoX0QWU582',
       false, true, true, 1, 2
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Jean', 'Peter', 'Skakanakou+oliveProjectSupervisor4@gmail.com',
       '$2a$10$LJtaMRVFZ0fO7V8RfPc2dOY./eC57hloOuSxuVYsqMns7OMnZrJHC',
       false, true, true, 1, 2
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Miguel', 'Stephane', 'Skakanakou+oliveProjectGroupLeader1@gmail.com',
       '$2a$10$fifhquQDWOHEDyCed3ex7estscwVdqaSlThFKStCApYzupllnqUa6',
       false, true, true, 1,
       (SELECT id FROM Role WHERE name='GroupLeader')
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Miguel', 'Stephane', 'Skakanakou+oliveProjectGroupLeader2@gmail.com',
       '$2a$10$aJRwhRFndDyW4Ek0XC1V.OD015eeLe8TnMhnXwXNwqwf4o1g5BPPq6',
       false, true, true, 1,
       (SELECT id FROM Role WHERE name='GroupLeader')
       );

INSERT INTO User (firstName, lastName, email, password, isDefaultPwd, isEnabled, isEmailVerified, createdBy, roleId)
VALUES ('Miguel', 'Stephane', 'Skakanakou+oliveProjectGroupLeader3@gmail.com',
        '$2a$10$ElIz9NWgto/VgfdCHnvFwu.GZFWbreFtu1qnl6UL1I5j2.f6ZN1Xu',
        false, true, true, 1,
        (SELECT id FROM Role WHERE name = 'GroupLeader')
       );



INSERT INTO Site (code, name) VALUES ('TYUT-TAIYUAN', 'TYUT: Campus of Taiyuan');
INSERT INTO Site (code, name) VALUES ('TYUT-YUCI', 'TYUT: Campus of Yuci');



INSERT INTO Building (code, name, sideId)
VALUES ('BLD-COMPUTER', 'Building of Computer Science', (SELECT id FROM Site WHERE Site.code='TYUT-TAIYUAN'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-ART', 'Building of Art and Culture', (SELECT id FROM Site WHERE Site.code='TYUT-TAIYUAN'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-MATH', 'Building of Mathematics', (SELECT id FROM Site WHERE Site.code='TYUT-TAIYUAN'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-FGN-LANG', 'Building of Foreign Language', (SELECT id FROM Site WHERE Site.code='TYUT-TAIYUAN'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-COMPUTER', 'Building of Computer Science', (SELECT id FROM Site WHERE Site.code='TYUT-YUCI'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-ECO', 'Building of Economy', (SELECT id FROM Site WHERE Site.code='TYUT-YUCI'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-MATH', 'Building of Mathematics', (SELECT id FROM Site WHERE Site.code='TYUT-YUCI'));

INSERT INTO Building (code, name, sideId)
VALUES ('BLD-SPORT', 'Building of Sport', (SELECT id FROM Site WHERE Site.code='TYUT-YUCI'));



INSERT INTO Availability (supervisorId)
VALUES ((SELECT id FROM User WHERE email='Skakanakou+oliveProjectSupervisor1@gmail.com'));

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'MONDAY', '12:00:00', '15:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'MONDAY', '19:00:00', '22:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'TUESDAY', '12:00:00', '15:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'TUESDAY', '19:00:00', '22:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'WEDNESDAY', '12:00:00', '15:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'WEDNESDAY', '19:00:00', '22:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'FRIDAY', '12:00:00', '15:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'FRIDAY', '19:00:00', '22:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'SATURDAY', '8:00:00', '20:00:00', 1);

INSERT INTO TimeInterval (fromDate, toDate, weekDay, fromTime, toTime, availabilityId)
VALUES ('2021-04-1', '2021-06-30', 'SUNDAY', '8:00:00', '15:00:00', 1);

INSERT INTO Classroom (code, name, buildingId, availabilityId)
VALUES ('CLASS-101', 'Classroom 101', 1, 1);

INSERT INTO ClassroomSupervisor (supervisorId, classroomId, assignedDate, isValid, whoAssigned)
VALUES (2, 1, current_date(), true, 1);

INSERT INTO ClassroomSupervisor (supervisorId, classroomId, assignedDate, isValid, whoAssigned)
VALUES (3, 1, current_date(), true, 1);
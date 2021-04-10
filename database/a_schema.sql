use classmanagementDb;

CREATE TABLE Role (
    id int not null AUTO_INCREMENT,
    name varchar(20) not null,
    PRIMARY KEY (id)
);
ALTER TABLE Role ADD CONSTRAINT UC_Role_Name UNIQUE (name);

CREATE TABLE User (
    id bigint not null AUTO_INCREMENT,
    firstName varchar(100),
    lastName varchar(100),
    email varchar(100),
    password varchar(100),
    isDefaultPwd boolean default false,
    isEnabled boolean,
    isEmailVerified boolean,
    createdBy bigint,
    roleId int not null,
    PRIMARY KEY (id)
);
ALTER TABLE User ADD CONSTRAINT UC_User_Email UNIQUE (email);
ALTER TABLE User ADD CONSTRAINT FK_User_CreatedBY FOREIGN KEY (createdBy) REFERENCES User(id);
ALTER TABLE User ADD CONSTRAINT FK_User_roleId FOREIGN KEY (roleId) REFERENCES Role(id);

CREATE TABLE Site (
    id int not null AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE Building (
    id int not null AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    sideId int not null,
    PRIMARY KEY (id),
    UNIQUE (sideId, code)
);
ALTER TABLE Building ADD CONSTRAINT FK_Building_siteId FOREIGN KEY (sideId) REFERENCES Site(id);

CREATE TABLE Classroom (
    id int not null AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    buildingId int not null,
    availabilityId int,
    PRIMARY KEY (id),
    UNIQUE (buildingId, code)
);
ALTER TABLE Classroom ADD CONSTRAINT FK_Classroom_siteId FOREIGN KEY (buildingId) REFERENCES Building(id);

CREATE TABLE Availability (
    id int not null AUTO_INCREMENT,
    supervisorId bigint not null,
    PRIMARY KEY (id)
);
ALTER TABLE Availability ADD CONSTRAINT FK_Availability_supervisorId FOREIGN KEY (supervisorId) REFERENCES User(id);
ALTER TABLE Classroom ADD CONSTRAINT FK_Building_availabilityId FOREIGN KEY (availabilityId) REFERENCES Availability(id);

CREATE TABLE TimeInterval (
    id bigint not null AUTO_INCREMENT,
    fromDate date,
    toDate date,
    weekDay varchar(10),
    fromTime time,
    toTime time,
    availabilityId int,
    PRIMARY KEY (id)
);
ALTER TABLE TimeInterval ADD CONSTRAINT FK_Interval_availabilityId FOREIGN KEY (availabilityId) REFERENCES Availability(id);

CREATE TABLE BookingRequest (
    id bigint not null AUTO_INCREMENT,
    status varchar(20),
    createdDate date,
    lastUpdatedDate date,
    rejectionReason varchar(255),
    bookingDate date,
    fromTime time,
    toTime time,
    classroomId int not null,
    assignedTo bigint not null,
    createdBy bigint not null,
    PRIMARY KEY (id)
);
ALTER TABLE BookingRequest ADD CONSTRAINT FK_BookingRequest_classroomId FOREIGN KEY (classroomId) REFERENCES Classroom(id);
ALTER TABLE BookingRequest ADD CONSTRAINT FK_BookingRequest_assignedTo FOREIGN KEY (assignedTo) REFERENCES User(id);
ALTER TABLE BookingRequest ADD CONSTRAINT FK_BookingRequest_createdBy FOREIGN KEY (createdBy) REFERENCES User(id);

CREATE TABLE ClassroomSupervisor (
    id int not null AUTO_INCREMENT,
    supervisorId bigint not null,
    classroomId int not null,
    assignedDate date,
    isValid boolean,
    whoAssigned bigint,
    whoRevoke bigint,
    PRIMARY KEY (id)
);
ALTER TABLE ClassroomSupervisor ADD CONSTRAINT FK_ClassroomSupervisor_supervisorId FOREIGN KEY (supervisorId) REFERENCES User(id);
ALTER TABLE ClassroomSupervisor ADD CONSTRAINT FK_ClassroomSupervisor_classroomId FOREIGN KEY (classroomId) REFERENCES Classroom(id);
ALTER TABLE ClassroomSupervisor ADD CONSTRAINT FK_ClassroomSupervisor_whoAssigned FOREIGN KEY (whoAssigned) REFERENCES User(id);
ALTER TABLE ClassroomSupervisor ADD CONSTRAINT FK_ClassroomSupervisor_whoRevoke FOREIGN KEY (whoRevoke) REFERENCES User(id);
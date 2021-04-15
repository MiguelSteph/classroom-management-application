use classmanagementDb;

CREATE TABLE role (
    id int not null AUTO_INCREMENT,
    name varchar(20) not null,
    PRIMARY KEY (id)
);
ALTER TABLE role ADD CONSTRAINT UC_role_Name UNIQUE (name);

CREATE TABLE user (
    id bigint not null AUTO_INCREMENT,
    first_name varchar(100),
    last_name varchar(100),
    email varchar(100),
    password varchar(100),
    is_default_pwd boolean default false,
    is_enabled boolean,
    is_email_verified boolean,
    created_by_id bigint,
    role_id int not null,
    PRIMARY KEY (id)
);
ALTER TABLE user ADD CONSTRAINT UC_user_Email UNIQUE (email);
ALTER TABLE user ADD CONSTRAINT FK_user_CreatedBY FOREIGN KEY (created_by_id) REFERENCES user(id);
ALTER TABLE user ADD CONSTRAINT FK_user_role_id FOREIGN KEY (role_id) REFERENCES role(id);

CREATE TABLE site (
    id int not null AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    PRIMARY KEY (id),
    UNIQUE (code)
);

CREATE TABLE building (
    id int not null AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    site_id int not null,
    PRIMARY KEY (id),
    UNIQUE (site_id, code)
);
ALTER TABLE building ADD CONSTRAINT FK_building_site_id FOREIGN KEY (site_id) REFERENCES site(id);

CREATE TABLE classroom (
    id int not null AUTO_INCREMENT,
    code varchar(20),
    name varchar(100),
    building_id int not null,
    PRIMARY KEY (id),
    UNIQUE (building_id, code)
);
ALTER TABLE classroom ADD CONSTRAINT FK_classroom_site_id FOREIGN KEY (building_id) REFERENCES building(id);

CREATE TABLE available_time_interval (
    id bigint not null AUTO_INCREMENT,
    from_date date,
    to_date date,
    week_day varchar(10),
    from_time time,
    to_time time,
    classroom_id int,
    supervisor_id bigint,
    created_date date,
    PRIMARY KEY (id)
);
ALTER TABLE available_time_interval ADD CONSTRAINT FK_Interval_classroom_id FOREIGN KEY (classroom_id) REFERENCES classroom(id);
ALTER TABLE available_time_interval ADD CONSTRAINT FK_Interval_supervisor_id FOREIGN KEY (supervisor_id) REFERENCES user(id);

CREATE TABLE booking_request (
    id bigint not null AUTO_INCREMENT,
    status varchar(20),
    created_date date,
    last_updated_date date,
    rejection_reason varchar(255),
    purpose varchar(255),
    booking_date date,
    from_time time,
    to_time time,
    classroom_id int not null,
    assigned_to_id bigint not null,
    created_by_id bigint not null,
    PRIMARY KEY (id)
);
ALTER TABLE booking_request ADD CONSTRAINT FK_booking_request_classroom_id FOREIGN KEY (classroom_id) REFERENCES classroom(id);
ALTER TABLE booking_request ADD CONSTRAINT FK_booking_request_assigned_to_id FOREIGN KEY (assigned_to_id) REFERENCES user(id);
ALTER TABLE booking_request ADD CONSTRAINT FK_booking_request_created_by_id FOREIGN KEY (created_by_id) REFERENCES user(id);

CREATE TABLE classroom_supervisor (
    id int not null AUTO_INCREMENT,
    supervisor_id bigint not null,
    classroom_id int not null,
    assigned_date date,
    is_valid boolean,
    who_assigned_id bigint,
    who_revoke_id bigint,
    PRIMARY KEY (id)
);
ALTER TABLE classroom_supervisor ADD CONSTRAINT FK_classroom_supervisor_supervisor_id FOREIGN KEY (supervisor_id) REFERENCES user(id);
ALTER TABLE classroom_supervisor ADD CONSTRAINT FK_classroom_supervisor_classroom_id FOREIGN KEY (classroom_id) REFERENCES classroom(id);
ALTER TABLE classroom_supervisor ADD CONSTRAINT FK_classroom_supervisor_who_assigned_id FOREIGN KEY (who_assigned_id) REFERENCES user(id);
ALTER TABLE classroom_supervisor ADD CONSTRAINT FK_classroom_supervisor_who_revoke_id FOREIGN KEY (who_revoke_id) REFERENCES user(id);
create table if not exists J
(
    Jnum int          not null
        primary key,
    Jnam varchar(100) not null,
    Ci   varchar(45)  null
);

create table if not exists P
(
    Pnum int          not null
        primary key,
    Pnam varchar(100) not null,
    We   double       not null,
    Co   varchar(45)  null,
    Ci   varchar(45)  null
);

create table if not exists S
(
    Snum int          not null
        primary key,
    Snam varchar(100) not null,
    Ci   varchar(45)  null
);

create table if not exists SPJ
(
    Snum int(11) NOT NULL,
    Pnum int(11) NOT NULL,
    Jnum int(11) NOT NULL,
    Qt   int,
    primary key (Snum, Pnum, Jnum),
    KEY `fk_s` (Snum),
    KEY `fk_p` (Pnum),
    KEY `fk_j` (Jnum),
    CONSTRAINT `fk_s` FOREIGN KEY (`Snum`) REFERENCES `S` (`Snum`),
    CONSTRAINT `fk_p` FOREIGN KEY (`Pnum`) REFERENCES `P` (`Pnum`),
    CONSTRAINT `fk_j` FOREIGN KEY (`Jnum`) REFERENCES `J` (`Jnum`)
);


create table if not exists users
(
    id        int auto_increment primary key,
    user_name varchar(50)          null,
    password  text                 null,
    roles     varchar(50)          null,
    active    tinyint(1) default 1 null
);

create table if not exists assignment
(
    id            int          not null
        primary key,
    description   varchar(700) not null,
    correct_query varchar(150) not null
);



CREATE TABLE if not exists `solution`
(
    `user_id`       int(11) NOT NULL,
    `assignment_id` int(11) NOT NULL,
    answer          text,
    answer_date     datetime,
    PRIMARY KEY (user_id, assignment_id),
    KEY `fk_user` (`user_id`),
    KEY `fk_assignment` (`assignment_id`),
    CONSTRAINT `fk_assignment` FOREIGN KEY (`assignment_id`) REFERENCES `assignment` (`id`),
    CONSTRAINT `fk_user` FOREIGN KEY (`user_id`) REFERENCES `users` (`id`)
);
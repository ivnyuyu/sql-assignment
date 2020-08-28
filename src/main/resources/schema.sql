create table if not exists J
(
    Jnum int         not null
        primary key,
    Jnam varchar(100) not null,
    St varchar(45) null,
    Ci varchar(45) null
);

create table if not exists P
(
    Pnum int         not null
        primary key,
    Pnam varchar(100) not null,
    We int not null,
    Co varchar(45) null,
    Ci varchar(45) null
);

create table if not exists S
(
    Snum int         not null
        primary key,
    Snam varchar(100) not null,
    St varchar(45) null,
    Ci varchar(45) null
);

create table if not exists SPJ
(
    Snum       int NOT NULL,
    Pnum       int NOT NULL,
    Jnum       int NOT NULL,
    Qt int,
    primary key (Snum,Pnum, Jnum)
);


create table if not exists users
(
    id        int auto_increment primary key,
    user_name varchar(50)          null,
    password  text                 null,
    roles     varchar(50)          null,
    active    int default 1 null
);

create table if not exists assignment
(
    id            int          not null
        primary key,
    description   varchar(700) not null,
    correct_query varchar(150) not null
);



CREATE TABLE if not exists solution
(
    user_id       int NOT NULL,
    assignment_id int NOT NULL,
    answer          text,
    answer_date     datetime,
    PRIMARY KEY (user_id, assignment_id)
);
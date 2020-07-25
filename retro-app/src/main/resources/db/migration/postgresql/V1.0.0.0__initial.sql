create sequence hibernate_sequence start 1 increment 1;
create sequence retro_comment_id_seq start 1 increment 1;
create sequence retro_user_id_seq start 1 increment 1;

create table retro_comment (
    id int8 not null DEFAULT nextval('retro_comment_id_seq'),
    comment varchar(255),
    created_by varchar(255),
    created_date timestamp,
    type varchar(255),
    primary key (id)
    );
create table retro_user (
    id int8 not null DEFAULT nextval('retro_user_id_seq'),
    password varchar(255),
    role varchar(255),
    username varchar(255),
    primary key (id)
    );

create table cop_user (id uuid not null, email varchar(255), password varchar(255), primary key (id));
create table user_roles (user_id uuid not null, roles varchar(255));
alter table user_roles add constraint FK26lpbjqgm2c9dkuw6knte2u7n foreign key (user_id) references cop_user;
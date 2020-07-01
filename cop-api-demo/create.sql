create table cop_user (id uuid not null, email varchar(255), password varchar(255), primary key (id))

create table user_roles (user_id uuid not null, roles varchar(255))

alter table user_roles add constraint FK26lpbjqgm2c9dkuw6knte2u7n foreign key (user_id) references cop_user

create table report (id uuid not null, date_time timestamp, description varchar(255), reporter_id uuid, primary key (id))
alter table report add constraint FKp6qna3cq7pqm93e3k3mm3ts14 foreign key (reporter_id) references cop_user
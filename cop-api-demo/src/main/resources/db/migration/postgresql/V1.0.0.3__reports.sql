create table report (id uuid not null, date_time timestamp, description varchar(255), reporter_id uuid, primary key (id));
alter table report add constraint FKp6qna3cq7pqm93e3k3mm3ts14 foreign key (reporter_id) references cop_user;
create table user(id int not null primary key ,
                    firstname varchar ,
                    lastname varchar ,
                    email varchar ,
                    tel int(10));

create table project (id int not null primary key,
                        title varchar (50),
                        description varchar (255),
                        mode varchar (50),
                        budget bigint,
                        startDate bigint,
                        workDays int);
create table tache (idTache int not null primary key,
                    num varchar (50),
                    hourCost int,
                    duration bigint,
                    project int)

create table project (id int not null primary key AUTO_INCREMENT,
                        title varchar (50),
                        description varchar (255),
                        manager int,
                        budget bigint,
                        startDate bigint,
                        workDays int);
create table tache (idTache int not null primary key AUTO_INCREMENT,
                    num varchar (50),
                    hourCost int,
                    duration bigint,
                    project int)
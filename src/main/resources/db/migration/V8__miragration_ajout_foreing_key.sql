create table project_users (project_id int,
                    users_id int);
create table PROJECT_TACHES (project_id int,
                            taches_idtache int);
ALTER TABLE project_users
    ADD FOREIGN KEY (users_id)
        REFERENCES user(id);
ALTER TABLE project_users
    ADD FOREIGN KEY (project_id)
        REFERENCES PROJECT(id);
ALTER TABLE PROJECT_TACHES
    ADD FOREIGN KEY (project_id)
        REFERENCES PROJECT(id);
ALTER TABLE PROJECT_TACHES
    ADD FOREIGN KEY (taches_idtache)
        REFERENCES TACHE(IDTACHE);
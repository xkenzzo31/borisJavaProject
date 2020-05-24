ALTER TABLE tache
    ADD FOREIGN KEY (project) 
    REFERENCES project(id)
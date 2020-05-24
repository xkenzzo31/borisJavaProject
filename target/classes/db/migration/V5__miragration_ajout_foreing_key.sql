ALTER TABLE tache ADD user_id int;
ALTER TABLE tache
    ADD FOREIGN KEY (user_id)
    REFERENCES user(id);
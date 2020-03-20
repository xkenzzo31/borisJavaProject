package fr.cesi.ril19.javaproject.exception;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException(Integer id) {
        super("le project n'existe pas : " + id);
    }
}

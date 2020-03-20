package fr.cesi.ril19.javaproject.exception;

public class UserNotFoundException extends RuntimeException {
    public UserNotFoundException(Integer id) {
        super("l'utilisateur n'existe pas : " + id);
    }
}

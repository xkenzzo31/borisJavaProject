package fr.cesi.ril19.javaproject.exception;

public class UserAlreadyExistException extends RuntimeException {
    public UserAlreadyExistException(Integer id) {
        super("l'utilisateur existe déjà : " + id);
    }
}

package person;

import exceptions.InvalidEmailException;

import java.io.Serializable;

public class Email implements Serializable {
    private String email;
    public static final String PATTERN =
            "[A-ZÆØÅa-zæøå0-9-_.]+@[A-ZÆØÅa-zæøå0-9-_]+\\.[A-ZÆØÅa-zæøå0-9-_]+(\\.[A-ZÆØÅa-zæøå0-9-_]+)?";

    public Email(String email) throws InvalidEmailException {
        if(!email.matches(PATTERN) || email.isBlank()){
            throw new InvalidEmailException("Ugyldig epost!");
        }
        this.email = email;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) throws InvalidEmailException {
        if(!email.matches(PATTERN) || email.isBlank()){
            throw new InvalidEmailException("Ugyldig epost!");
        }
        this.email = email;
    }

    @Override
    public String toString() {
        return email;
    }
}

package person;

import exceptions.InvalidPhoneException;

import java.io.Serializable;

public class PhoneNumber implements Serializable {
    private String number;
    public final static String PATTERN =
            "(\\+)?(\\(((\\+)?[0-9]+)?\\))?( )?([0-9]+([-]| )?)+";

    public PhoneNumber(String number) throws InvalidPhoneException {
        if(!number.matches(PATTERN) || number.isBlank()){
            throw new InvalidPhoneException("Ugyldig telefonnummer!");
        }
        this.number = number;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) throws InvalidPhoneException {
        if(!number.matches(PATTERN) || number.isBlank()){
            throw new InvalidPhoneException("Ugyldig telefonnummer!");
        }
        this.number = number;
    }

    @Override
    public String toString() {
        return number;
    }
}

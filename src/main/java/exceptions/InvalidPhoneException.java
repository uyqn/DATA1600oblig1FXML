package exceptions;

public class InvalidPhoneException extends IllegalArgumentException{
    public InvalidPhoneException(String msg){
        super(msg);
    }
}
